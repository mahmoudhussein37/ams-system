package koreatech.cse.domain;

import koreatech.cse.domain.constant.CurriculumStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Curriculum domain model.
 * Represents a curriculum file for a specific division and academic year.
 * UNIQUE constraint: one row per (division_id, academic_year).
 */
public class Curriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int divisionId;
    private int academicYear;
    private int uploadedFileId;
    private CurriculumStatus status;
    private Date createdAt;
    private Date archivedAt;

    // Transient fields for joined data
    private transient String divisionName;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getUploadedFileId() {
        return uploadedFileId;
    }

    public void setUploadedFileId(int uploadedFileId) {
        this.uploadedFileId = uploadedFileId;
    }

    public CurriculumStatus getStatus() {
        return status;
    }

    public void setStatus(CurriculumStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Date archivedAt) {
        this.archivedAt = archivedAt;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Curriculum other = (Curriculum) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
