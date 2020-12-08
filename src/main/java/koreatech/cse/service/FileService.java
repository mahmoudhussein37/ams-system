package koreatech.cse.service;

import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.repository.UploadedFileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.maxSize}")
    private int MAX_SIZE;

    @Value("${file.uploadPath}")
    private String originPath;


    @Inject
    private UploadedFileMapper uploadedFileMapper;


    public boolean processUploadedFile(MultipartFile file, User uploader, Designation designation, int divisionId, int profCourseId, int year) throws IOException {
        if(file.getSize() > MAX_SIZE)
            return false;
        StringBuilder pathBuilder = new StringBuilder(originPath);
        pathBuilder.append(File.separator);
        pathBuilder.append(year);
        pathBuilder.append(File.separator);
        pathBuilder.append(designation);
        pathBuilder.append(File.separator);
        if(designation == Designation.attendance) {
            pathBuilder.append(profCourseId);
            pathBuilder.append(File.separator);
        }
        File originDir = new File(pathBuilder.toString());
        if (!originDir.exists())
            originDir.mkdirs();
        String originalFilename = file.getOriginalFilename();
        if(this.isValidFileType(originalFilename)) {
            String validFilename = changeToValidFilename(originalFilename);
            String rName = UUID.randomUUID().toString().replace("-", "");
            File tempFile = File.createTempFile(designation.name(), rName, originDir);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(tempFile));

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setUploaderId(uploader.getId());
            uploadedFile.setDesignation(designation.name());
            uploadedFile.setDivisionId(divisionId);
            uploadedFile.setProfCourseId(profCourseId);
            uploadedFile.setName(file.getOriginalFilename());
            uploadedFile.setYear(year);
            uploadedFile.setPath(pathBuilder.toString() + tempFile.getName());
            uploadedFileMapper.insert(uploadedFile);
            return true;
        } else
            return false;
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
        String[] preventString = { "!", "@", "#", "$", "%", "^", "&", "*", "`"};
        for (String s : preventString)
            if (fileName.contains(s))
                fileName = fileName.replace(s, "");

        fileName = fileName.replace(" ", "_");
        return fileName;
    }

    public String getTempPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/") + "temp";
    }
}
