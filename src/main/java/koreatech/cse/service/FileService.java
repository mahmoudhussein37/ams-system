package koreatech.cse.service;

import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.repository.UploadedFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.io.InputStream;
import java.util.*;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Value("${file.maxSize}")
    private int MAX_SIZE;

    @Value("${file.uploadPath}")
    private String originPath;

    @Inject
    private UploadedFileMapper uploadedFileMapper;
    @Inject
    private AuthorizationGuardService authorizationGuardService;

    public UploadedFile processUploadedFile(MultipartFile file, User uploader, Designation designation, int divisionId,
            int profCourseId, int year) throws IOException {
        if (designation == Designation.attendance) {
            if (uploader == null) {
                throw new AccessDeniedException("Access denied.");
            }
            ProfessorCourse ownedCourse = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, uploader,
                    "attendance-file-upload-service");
            if (ownedCourse.getSemesterId() != year) {
                throw new AccessDeniedException("Access denied.");
            }
        }

        if (file.isEmpty())
            throw new IllegalArgumentException("Empty file upload");
        if (file.getSize() < 1024)
            throw new IllegalArgumentException("File too small");
        if (file.getSize() > MAX_SIZE)
            throw new IOException("File size " + file.getSize() + " exceeds maximum allowed size " + MAX_SIZE);

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty())
            throw new IllegalArgumentException("Filename is required");
        if (originalFilename.length() > 255)
            throw new IllegalArgumentException("Filename too long");
        if (!this.isValidFileType(originalFilename))
            throw new IOException("Unsupported file type: " + sanitizeFilename(originalFilename));

        assertValidFileSignature(file);

        StringBuilder pathBuilder = new StringBuilder(originPath);
        pathBuilder.append(File.separator);
        pathBuilder.append(year);
        pathBuilder.append(File.separator);
        pathBuilder.append(designation);
        pathBuilder.append(File.separator);
        if (designation == Designation.attendance) {
            pathBuilder.append(profCourseId);
            pathBuilder.append(File.separator);
        }
        File originDir = new File(pathBuilder.toString());
        if (!originDir.exists())
            originDir.mkdirs();

        String rName = UUID.randomUUID().toString().replace("-", "");
        File tempFile = File.createTempFile(designation.name(), rName, originDir);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            FileCopyUtils.copy(file.getInputStream(), fos);
        }

        String safeName = sanitizeFilename(originalFilename);

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setUploaderId(uploader.getId());
        uploadedFile.setDesignation(designation.name());
        uploadedFile.setDivisionId(divisionId);
        uploadedFile.setProfCourseId(profCourseId);
        uploadedFile.setName(safeName);
        uploadedFile.setYear(year);
        uploadedFile.setPath(pathBuilder.toString() + tempFile.getName());
        uploadedFileMapper.insert(uploadedFile);
        // @SelectKey on insert() populates uploadedFile.id via LAST_INSERT_ID()
        return uploadedFile;
    }

    public boolean isValidFileType(String fileName) {
        int dot = fileName.lastIndexOf(".");
        String fileType = fileName.substring(dot + 1);
        String[] validTypeArray = { "jpg", "jpeg", "png", "gif", "zip", "hwp", "doc", "docx", "xls", "xlsx", "pdf",
                "csv", "txt" };
        List<String> validTypes = Arrays.asList(validTypeArray);
        return validTypes.contains(fileType.toLowerCase());
    }

    public String getExtension(String fileName) {
        int dotPosition = fileName.lastIndexOf('.');
        if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
            return fileName.substring(dotPosition + 1).toLowerCase();
        } else {
            return "";
        }
    }

    public String changeToValidFilename(String fileName) {
        String[] preventString = { "!", "@", "#", "$", "%", "^", "&", "*", "`" };
        for (String s : preventString)
            if (fileName.contains(s))
                fileName = fileName.replace(s, "");

        fileName = fileName.replace(" ", "_");
        return fileName;
    }

    public String getTempPath(@SuppressWarnings("unused") HttpServletRequest request) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(request); // no-op reference
        return System.getProperty("java.io.tmpdir") + File.separator + "ams_temp";
    }

    private String sanitizeFilename(String originalName) {
        if (originalName == null) {
            return "file";
        }
        // Strip path components: keep only the part after last / or \
        int lastSlash = originalName.lastIndexOf('/');
        int lastBackslash = originalName.lastIndexOf('\\');
        int lastSep = Math.max(lastSlash, lastBackslash);
        if (lastSep >= 0 && lastSep < originalName.length() - 1) {
            originalName = originalName.substring(lastSep + 1);
        } else if (lastSep >= 0) {
            originalName = "";
        }
        // Remove control characters and null bytes
        originalName = originalName.replaceAll("[\\x00-\\x1f\\x7f]", "");
        // Remove any remaining directory separators
        originalName = originalName.replace("/", "").replace("\\", "");
        // Trim whitespace
        originalName = originalName.trim();
        // Limit length
        if (originalName.length() > 255) {
            originalName = originalName.substring(0, 255);
        }
        // Fallback
        if (originalName.isEmpty()) {
            return "file";
        }
        return originalName;
    }

    private static final Set<String> ALLOWED_CONTENT_TYPES = new HashSet<>(Arrays.asList(
            "application/pdf",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));

    private static final byte[] PDF_MAGIC = { 0x25, 0x50, 0x44, 0x46, 0x2D }; // %PDF-
    private static final byte[] OLE_MAGIC = { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0 };
    private static final byte[] PK_MAGIC = { 0x50, 0x4B }; // PK

    private void assertValidFileSignature(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        String ext = getExtension(file.getOriginalFilename() != null ? file.getOriginalFilename() : "");

        boolean needsSignatureCheck = ALLOWED_CONTENT_TYPES.contains(contentType)
                || "pdf".equals(ext) || "xls".equals(ext) || "xlsx".equals(ext)
                || "doc".equals(ext) || "docx".equals(ext);

        if (!needsSignatureCheck) {
            return;
        }

        byte[] header = new byte[8];
        int bytesRead;
        InputStream is = file.getInputStream();
        try {
            bytesRead = is.read(header);
        } finally {
            is.close();
        }

        if (bytesRead < 2) {
            throw new IllegalArgumentException("Invalid file signature: file too small.");
        }

        if ("pdf".equals(ext)) {
            if (!startsWith(header, PDF_MAGIC)) {
                throw new IllegalArgumentException("Invalid file signature: expected PDF.");
            }
            return;
        }
        if ("xls".equals(ext)) {
            if (!startsWith(header, OLE_MAGIC)) {
                throw new IllegalArgumentException("Invalid file signature: expected XLS (OLE2).");
            }
            return;
        }
        if ("xlsx".equals(ext) || "docx".equals(ext)) {
            if (!startsWith(header, PK_MAGIC)) {
                throw new IllegalArgumentException("Invalid file signature: expected OOXML (PK/ZIP).");
            }
            return;
        }
        if ("doc".equals(ext)) {
            if (!startsWith(header, OLE_MAGIC)) {
                throw new IllegalArgumentException("Invalid file signature: expected DOC (OLE2).");
            }
            return;
        }
    }

    private static boolean startsWith(byte[] data, byte[] prefix) {
        if (data.length < prefix.length)
            return false;
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i])
                return false;
        }
        return true;
    }

    /**
     * Saves a MultipartFile to a sanitized path within the application temp
     * directory.
     * Validates that the resolved canonical path does not escape the temp base
     * directory,
     * preventing path traversal attacks from malicious filenames.
     *
     * @param multipartFile the uploaded file
     * @param request       the current HTTP request
     * @return the File written to disk (guaranteed to be within the temp directory)
     * @throws IOException       if the file cannot be written
     * @throws SecurityException if the resolved path escapes the temp directory
     */
    public File saveMultipartToTempFile(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String tempBasePath = getTempPath(request);
        File tempDir = new File(tempBasePath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
            if (!tempDir.setReadable(false, false)) {
                logger.warn("Could not clear readable permission on temp directory");
            }
            if (!tempDir.setReadable(true, true)) {
                logger.warn("Could not set owner-only readable on temp directory");
            }
            if (!tempDir.setWritable(false, false)) {
                logger.warn("Could not clear writable permission on temp directory");
            }
            if (!tempDir.setWritable(true, true)) {
                logger.warn("Could not set owner-only writable on temp directory");
            }
            if (!tempDir.setExecutable(false, false)) {
                logger.warn("Could not clear executable permission on temp directory");
            }
            if (!tempDir.setExecutable(true, true)) {
                logger.warn("Could not set owner-only executable on temp directory");
            }
        }

        // Use server-generated UUID name — no user-supplied filename in the path.
        // The extension .tmp is sufficient; XSSFWorkbook reads by content, not by name.
        String serverGenName = UUID.randomUUID().toString().replace("-", "") + ".tmp";
        File targetFile = new File(tempDir, serverGenName);

        // Canonical path validation — defense-in-depth beyond sanitizeFilename
        String canonicalBase = tempDir.getCanonicalPath();
        String canonicalTarget = targetFile.getCanonicalPath();
        if (!canonicalTarget.startsWith(canonicalBase + File.separator)
                && !canonicalTarget.equals(canonicalBase)) {
            throw new SecurityException(
                    "Path traversal attempt blocked: resolved path escapes temp directory");
        }

        multipartFile.transferTo(targetFile);
        return targetFile;
    }
}
