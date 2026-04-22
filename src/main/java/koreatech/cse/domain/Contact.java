package koreatech.cse.domain;

import com.neovisionaries.i18n.LanguageCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Contact implements Serializable  {
    private static final long serialVersionUID = 44493606570353849L;

    private int id;
    private int userId;

    private String firstName;
    private String lastName;

    private String cellPhone;
    private String phone;
    private String postCode;
    private String address;
    private String lab;

    private String parentName;
    private String relation;
    private String parentCellPhone;
    private String parentPhone;
    private String parentPostCode;
    private String parentAddress;

    private int admissionYear; //admission year, entering year
    private String admissionDate; //admission date, entering date
    private String highSchool;
    private String hGradYear;


    private String gradYear;
    private String gradSemester;
    private String gradDate;
    private String gradDegree;
    private String degreeNumber;
    private String certNumber;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        String normalizedFirstName = StringUtils.trimToNull(firstName);
        String normalizedLastName = StringUtils.trimToNull(lastName);

        if (normalizedFirstName == null && normalizedLastName == null) {
            return "Unknown";
        }
        if (normalizedFirstName == null) {
            return normalizedLastName;
        }
        if (normalizedLastName == null) {
            return normalizedFirstName;
        }
        return normalizedFirstName + " " + normalizedLastName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getParentCellPhone() {
        return parentCellPhone;
    }

    public void setParentCellPhone(String parentCellPhone) {
        this.parentCellPhone = parentCellPhone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getParentPostCode() {
        return parentPostCode;
    }

    public void setParentPostCode(String parentPostCode) {
        this.parentPostCode = parentPostCode;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String gethGradYear() {
        return hGradYear;
    }

    public void sethGradYear(String hGradYear) {
        this.hGradYear = hGradYear;
    }

    public String getGradYear() {
        return gradYear;
    }

    public void setGradYear(String gradYear) {
        this.gradYear = gradYear;
    }

    public String getGradSemester() {
        return gradSemester;
    }

    public void setGradSemester(String gradSemester) {
        this.gradSemester = gradSemester;
    }

    public String getGradDate() {
        return gradDate;
    }

    public void setGradDate(String gradDate) {
        this.gradDate = gradDate;
    }

    public String getGradDegree() {
        return gradDegree;
    }

    public void setGradDegree(String gradDegree) {
        this.gradDegree = gradDegree;
    }

    public String getDegreeNumber() {
        return degreeNumber;
    }

    public void setDegreeNumber(String degreeNumber) {
        this.degreeNumber = degreeNumber;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contact other = (Contact) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
