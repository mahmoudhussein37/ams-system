package koreatech.cse.service;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    @Value("${file.maxSize}")
    private int MAX_SIZE;

    @Value("${file.uploadPath}")
    private String originPath;

    public boolean processUploadedFile(MultipartFile file, int programId, int enrollmentId, User uploader, Designation designation) throws IOException {
        if(file.getSize() > MAX_SIZE)
            return false;
        StringBuilder pathBuilder = new StringBuilder(originPath);
        pathBuilder.append(File.separator);
        pathBuilder.append(designation);
        pathBuilder.append(File.separator);
        /*if (designation == Designation.enrollment) {
            pathBuilder.append(programId);
            pathBuilder.append(File.separator);
            pathBuilder.append(enrollmentId);
        } else
            pathBuilder.append(programId);*/

        /*File originDir = new File(pathBuilder.toString());
        if (!originDir.exists())
            originDir.mkdirs();
        String originalFilename = file.getOriginalFilename();
        if(this.isValidFileType(originalFilename)) {
            String validFilename = changeToValidFilename(originalFilename);
            File tempFile = File.createTempFile(getPrefix(enrollmentId), validFilename, originDir);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(tempFile));

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setUploader(uploader);
            uploadedFile.setProgram(programMapper.findOne(programId));
            uploadedFile.setEnrollment(enrollmentMapper.findOne(enrollmentId));
            uploadedFile.setName(getPrefix(enrollmentId) + validFilename);
            uploadedFile.setPath(pathBuilder.toString() + File.separator + tempFile.getName());
            uploadedFileMapper.insert(uploadedFile);
            return true;
        } else
            return false;*/
        return true;
    }

    public boolean isValidFileType(String fileName) {
        int dot = fileName.lastIndexOf(".");
        String fileType = fileName.substring(dot + 1);
        String[] validTypeArray = { "jpg", "jpeg", "png", "gif", "zip", "hwp", "doc", "docx", "xls", "xlsx", "pdf", "csv", "txt" };
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
}
