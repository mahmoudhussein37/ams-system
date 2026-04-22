package koreatech.cse.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.InitializingBean;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class TrustedRequestService implements InitializingBean {
    static final String TRUSTED_PROXY_CIDRS_PROPERTY = "security.trustedProxyCidrs";
    static final String TRUSTED_PROXY_CIDRS_ENV = "AMS_TRUSTED_PROXY_CIDRS";
    private static final String DEFAULT_TRUSTED_PROXY_CIDRS =
            "127.0.0.1/32,::1/128,0:0:0:0:0:0:0:1/128";
    private static final Pattern SAFE_FORWARDED_HOST_PATTERN = Pattern.compile(
            "^[A-Za-z0-9.-]+(?::\\d{1,5})?$|^\\[[0-9A-Fa-f:]+\\](?::\\d{1,5})?$");

    private static final Logger logger = LoggerFactory.getLogger(TrustedRequestService.class);
    private volatile List<CidrBlock> trustedProxyCidrs;

    public TrustedRequestService() {
        setTrustedProxyCidrs(resolveConfiguredCidrs());
    }

    @Override
    public void afterPropertiesSet() {
        validateConfiguration();
    }

    void validateConfiguration() {
        if (trustedProxyCidrs.stream().allMatch(CidrBlock::isLoopback)) {
            logger.warn("TrustedRequestService: only loopback proxies configured. " +
                    "If running behind a reverse proxy, set AMS_TRUSTED_PROXY_CIDRS.");
        }
    }

    void setTrustedProxyCidrs(String configuredCidrs) {
        String effectiveCidrs = StringUtils.defaultIfBlank(StringUtils.trimToNull(configuredCidrs),
                DEFAULT_TRUSTED_PROXY_CIDRS);
        trustedProxyCidrs = parseCidrs(effectiveCidrs);
    }

    public boolean isTrustedProxyRequest(HttpServletRequest request) {
        return request != null && isTrustedProxyAddress(request.getRemoteAddr());
    }

    public boolean isSecureRequest(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        if (isTrustedProxyRequest(request)) {
            String rawProto = request.getHeader("X-Forwarded-Proto");
            String forwardedProto = rawProto == null ? null :
                    StringUtils.trimToNull(rawProto.split(",")[0].trim());
            if (forwardedProto != null) {
                return "https".equalsIgnoreCase(forwardedProto);
            }
        }

        return request.isSecure() || "https".equalsIgnoreCase(request.getScheme());
    }

    public String resolveClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return resolveRealClientIp(request);
    }

    String resolveRealClientIp(HttpServletRequest request) {
        if (!isTrustedProxyRequest(request)) {
            return StringUtils.trimToNull(request.getRemoteAddr());
        }
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(xForwardedFor)) {
            return StringUtils.trimToNull(request.getRemoteAddr());
        }
        String[] parts = xForwardedFor.split(",");
        for (int i = parts.length - 1; i >= 0; i--) {
            String candidate = StringUtils.trimToNull(parts[i]);
            if (candidate == null) {
                continue;
            }
            if (!isTrustedProxyAddress(candidate)) {
                return candidate;
            }
        }
        return StringUtils.trimToNull(request.getRemoteAddr());
    }

    public String resolveHostForRedirect(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        if (isTrustedProxyRequest(request)) {
            String rawHost = request.getHeader("X-Forwarded-Host");
            String firstHostValue = rawHost == null ? null :
                    StringUtils.trimToNull(rawHost.split(",")[0].trim());
            String forwardedHost = normalizeForwardedHost(firstHostValue);
            if (forwardedHost != null) {
                return forwardedHost;
            }
        }

        int port = request.getServerPort();
        if (port > 0 && port != 80 && port != 443) {
            return request.getServerName() + ":" + port;
        }
        return request.getServerName();
    }

    public String resolveServerName(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String hostForServerName = null;
        if (isTrustedProxyRequest(request)) {
            String rawHost = request.getHeader("X-Forwarded-Host");
            String firstHostValue = rawHost == null ? null :
                    StringUtils.trimToNull(rawHost.split(",")[0].trim());
            hostForServerName = normalizeForwardedHost(firstHostValue);
        }
        if (hostForServerName == null) {
            hostForServerName = request.getServerName();
        }

        String normalizedHost = extractHostName(hostForServerName);
        return normalizedHost == null ? null : normalizedHost.toLowerCase(Locale.ROOT);
    }

    boolean isTrustedProxyAddress(String remoteAddress) {
        InetAddress address = parseInetAddress(remoteAddress);
        if (address == null) {
            return false;
        }

        for (CidrBlock trustedProxyCidr : trustedProxyCidrs) {
            if (trustedProxyCidr.matches(address)) {
                return true;
            }
        }
        return false;
    }

    private String resolveConfiguredCidrs() {
        String configuredCidrs = StringUtils.trimToNull(System.getProperty(TRUSTED_PROXY_CIDRS_PROPERTY));
        if (configuredCidrs != null) {
            return configuredCidrs;
        }
        return StringUtils.trimToNull(System.getenv(TRUSTED_PROXY_CIDRS_ENV));
    }

    private List<CidrBlock> parseCidrs(String configuredCidrs) {
        if (StringUtils.isBlank(configuredCidrs)) {
            return Collections.emptyList();
        }

        List<CidrBlock> cidrBlocks = new ArrayList<>();
        String[] entries = configuredCidrs.split(",");
        for (String entry : entries) {
            CidrBlock cidrBlock = CidrBlock.parse(entry);
            if (cidrBlock != null) {
                cidrBlocks.add(cidrBlock);
            }
        }
        return cidrBlocks;
    }

    private String normalizeForwardedHost(String forwardedHost) {
        String trimmedHost = StringUtils.trimToNull(forwardedHost);
        if (trimmedHost == null) {
            return null;
        }

        if (trimmedHost.contains("/") || trimmedHost.contains("\\") || containsWhitespace(trimmedHost)) {
            return null;
        }

        if (!SAFE_FORWARDED_HOST_PATTERN.matcher(trimmedHost).matches()) {
            return null;
        }

        return trimmedHost;
    }

    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private String extractHostName(String host) {
        String normalizedHost = StringUtils.trimToNull(host);
        if (normalizedHost == null) {
            return null;
        }

        if (normalizedHost.startsWith("[")) {
            int closingBracketIndex = normalizedHost.indexOf(']');
            if (closingBracketIndex > 0) {
                return normalizedHost.substring(1, closingBracketIndex);
            }
            return normalizedHost;
        }

        int colonIndex = normalizedHost.indexOf(':');
        return colonIndex >= 0 ? normalizedHost.substring(0, colonIndex) : normalizedHost;
    }

    private InetAddress parseInetAddress(String address) {
        String trimmedAddress = StringUtils.trimToNull(address);
        if (trimmedAddress == null) {
            return null;
        }

        try {
            return InetAddress.getByName(trimmedAddress);
        } catch (UnknownHostException ignored) {
            return null;
        }
    }

    static final class CidrBlock {
        private final byte[] addressBytes;
        private final int prefixLength;

        private CidrBlock(byte[] addressBytes, int prefixLength) {
            this.addressBytes = addressBytes;
            this.prefixLength = prefixLength;
        }

        static CidrBlock parse(String cidrExpression) {
            String trimmedExpression = StringUtils.trimToNull(cidrExpression);
            if (trimmedExpression == null) {
                return null;
            }

            String[] split = trimmedExpression.split("/");
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(split[0].trim());
            } catch (UnknownHostException ignored) {
                return null;
            }

            byte[] address = inetAddress.getAddress();
            int maxPrefixLength = address.length * 8;
            int parsedPrefixLength = maxPrefixLength;
            if (split.length == 2) {
                try {
                    parsedPrefixLength = Integer.parseInt(split[1].trim());
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }

            if (parsedPrefixLength < 0 || parsedPrefixLength > maxPrefixLength) {
                return null;
            }

            return new CidrBlock(address, parsedPrefixLength);
        }

        boolean isLoopback() {
            try {
                return InetAddress.getByAddress(addressBytes).isLoopbackAddress();
            } catch (UnknownHostException ignored) {
                return false;
            }
        }

        boolean matches(InetAddress candidateAddress) {
            byte[] candidateBytes = candidateAddress.getAddress();
            if (candidateBytes.length != addressBytes.length) {
                return false;
            }

            int fullBytes = prefixLength / 8;
            int remainingBits = prefixLength % 8;
            for (int i = 0; i < fullBytes; i++) {
                if (addressBytes[i] != candidateBytes[i]) {
                    return false;
                }
            }

            if (remainingBits == 0) {
                return true;
            }

            int mask = 0xFF << (8 - remainingBits);
            return (addressBytes[fullBytes] & mask) == (candidateBytes[fullBytes] & mask);
        }
    }
}
