package koreatech.cse.domain.dto;

import java.util.Date;

/**
 * View model for each row on the Curriculum Management page.
 * One instance per division, enriched with file and uploader metadata.
 */
public class CurriculumRowDTO {

    private int divisionId;
    private String divisionName;
    private int academicYear;

    // Curriculum data (null if no curriculum exists)
    private Integer curriculumId;
    private String status; // "ACTIVE", "ARCHIVED", or null

    // File data (null if no file)
    private Integer uploadedFileId;
    private String uploadedFileName;
    private Date uploadedAt;
    private String uploaderName;

    // Getters and Setters
    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Integer curriculumId) {
        this.curriculumId = curriculumId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUploadedFileId() {
        return uploadedFileId;
    }

    public void setUploadedFileId(Integer uploadedFileId) {
        this.uploadedFileId = uploadedFileId;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    // Helper methods for JSP conditionals
    public boolean hasCurriculum() {
        return curriculumId != null;
    }

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean hasFile() {
        return uploadedFileId != null;
    }
}
