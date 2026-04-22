package koreatech.cse.filter;

import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.StaticWebApplicationContext;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MultipartCsrfSupportTest {

    @Test
    public void multipartFilterMakesCsrfParameterVisibleToDownstreamFilters() throws Exception {
        String boundary = "----AMSBoundary";
        String csrfToken = "csrf-token-123";
        byte[] body = buildMultipartBody(boundary, csrfToken);

        MockServletContext servletContext = new MockServletContext();
        File tempDir = Files.createTempDirectory("ams-multipart-csrf").toFile();
        servletContext.setAttribute(WebUtils.TEMP_DIR_CONTEXT_ATTRIBUTE, tempDir);

        StaticWebApplicationContext applicationContext = new StaticWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        applicationContext.registerSingleton("multipartResolver", CommonsMultipartResolver.class);
        applicationContext.refresh();
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);

        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        multipartFilter.init(new MockFilterConfig(servletContext));

        try {
            MockHttpServletRequest request = new MockHttpServletRequest(servletContext, "POST",
                    "/admin/studentManagement/studentInformation/studentDetail");
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            request.setContentType("multipart/form-data; boundary=" + boundary);
            request.setContent(body);

            MockHttpServletResponse response = new MockHttpServletResponse();
            AtomicReference<String> downstreamCsrf = new AtomicReference<String>();
            AtomicBoolean multipartWrapped = new AtomicBoolean(false);

            assertNull("Raw multipart request should not expose the CSRF parameter before parsing.",
                    request.getParameter("_csrf"));

            multipartFilter.doFilter(request, response, new FilterChain() {
                @Override
                public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) {
                    multipartWrapped.set(servletRequest instanceof MultipartHttpServletRequest);
                    downstreamCsrf.set(((HttpServletRequest) servletRequest).getParameter("_csrf"));
                }
            });

            assertTrue("MultipartFilter should wrap the request before downstream security checks.",
                    multipartWrapped.get());
            assertEquals("csrf-token-123", downstreamCsrf.get());
        } finally {
            multipartFilter.destroy();
            applicationContext.close();
            FileSystemUtils.deleteRecursively(tempDir);
        }
    }

    private byte[] buildMultipartBody(String boundary, String csrfToken) {
        String lineSeparator = "\r\n";
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(lineSeparator);
        builder.append("Content-Disposition: form-data; name=\"_csrf\"").append(lineSeparator);
        builder.append(lineSeparator);
        builder.append(csrfToken).append(lineSeparator);
        builder.append("--").append(boundary).append(lineSeparator);
        builder.append("Content-Disposition: form-data; name=\"username\"").append(lineSeparator);
        builder.append(lineSeparator);
        builder.append("student.admin").append(lineSeparator);
        builder.append("--").append(boundary).append(lineSeparator);
        builder.append("Content-Disposition: form-data; name=\"file\"; filename=\"profile.jpg\"").append(lineSeparator);
        builder.append("Content-Type: image/jpeg").append(lineSeparator);
        builder.append(lineSeparator);
        builder.append("fake-image").append(lineSeparator);
        builder.append("--").append(boundary).append("--").append(lineSeparator);
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
