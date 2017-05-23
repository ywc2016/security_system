package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "inf_staff", schema = "amc", catalog = "")
public class InfStaff {
    private long id;
    private String number;
    private Long infDepartmentId;
    private String name;
    private String gender;
    private Timestamp birthday;
    private String phone;
    private String email;
    private String title;
    private Timestamp date;
    private String diploma;
    private String graduateSchool;
    private Timestamp graduateTime;
    private String majority;
    private String working;
    private String certificate;
    private String professionTitle;
    private String lastCompany;
    private String insuranceExperience;
    private String insuranceBrokerageExperience;
    private String enabled;
    private Long userRelate;
    private String businessExpertise;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "inf_department_id")
    public Long getInfDepartmentId() {
        return infDepartmentId;
    }

    public void setInfDepartmentId(Long infDepartmentId) {
        this.infDepartmentId = infDepartmentId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "diploma")
    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    @Basic
    @Column(name = "graduate_school")
    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    @Basic
    @Column(name = "graduate_time")
    public Timestamp getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(Timestamp graduateTime) {
        this.graduateTime = graduateTime;
    }

    @Basic
    @Column(name = "majority")
    public String getMajority() {
        return majority;
    }

    public void setMajority(String majority) {
        this.majority = majority;
    }

    @Basic
    @Column(name = "working")
    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    @Basic
    @Column(name = "certificate")
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Basic
    @Column(name = "profession_title")
    public String getProfessionTitle() {
        return professionTitle;
    }

    public void setProfessionTitle(String professionTitle) {
        this.professionTitle = professionTitle;
    }

    @Basic
    @Column(name = "last_company")
    public String getLastCompany() {
        return lastCompany;
    }

    public void setLastCompany(String lastCompany) {
        this.lastCompany = lastCompany;
    }

    @Basic
    @Column(name = "insurance_experience")
    public String getInsuranceExperience() {
        return insuranceExperience;
    }

    public void setInsuranceExperience(String insuranceExperience) {
        this.insuranceExperience = insuranceExperience;
    }

    @Basic
    @Column(name = "insurance_brokerage_experience")
    public String getInsuranceBrokerageExperience() {
        return insuranceBrokerageExperience;
    }

    public void setInsuranceBrokerageExperience(String insuranceBrokerageExperience) {
        this.insuranceBrokerageExperience = insuranceBrokerageExperience;
    }

    @Basic
    @Column(name = "enabled")
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "user_relate")
    public Long getUserRelate() {
        return userRelate;
    }

    public void setUserRelate(Long userRelate) {
        this.userRelate = userRelate;
    }

    @Basic
    @Column(name = "business_expertise")
    public String getBusinessExpertise() {
        return businessExpertise;
    }

    public void setBusinessExpertise(String businessExpertise) {
        this.businessExpertise = businessExpertise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfStaff infStaff = (InfStaff) o;

        if (id != infStaff.id) return false;
        if (number != null ? !number.equals(infStaff.number) : infStaff.number != null) return false;
        if (infDepartmentId != null ? !infDepartmentId.equals(infStaff.infDepartmentId) : infStaff.infDepartmentId != null)
            return false;
        if (name != null ? !name.equals(infStaff.name) : infStaff.name != null) return false;
        if (gender != null ? !gender.equals(infStaff.gender) : infStaff.gender != null) return false;
        if (birthday != null ? !birthday.equals(infStaff.birthday) : infStaff.birthday != null) return false;
        if (phone != null ? !phone.equals(infStaff.phone) : infStaff.phone != null) return false;
        if (email != null ? !email.equals(infStaff.email) : infStaff.email != null) return false;
        if (title != null ? !title.equals(infStaff.title) : infStaff.title != null) return false;
        if (date != null ? !date.equals(infStaff.date) : infStaff.date != null) return false;
        if (diploma != null ? !diploma.equals(infStaff.diploma) : infStaff.diploma != null) return false;
        if (graduateSchool != null ? !graduateSchool.equals(infStaff.graduateSchool) : infStaff.graduateSchool != null)
            return false;
        if (graduateTime != null ? !graduateTime.equals(infStaff.graduateTime) : infStaff.graduateTime != null)
            return false;
        if (majority != null ? !majority.equals(infStaff.majority) : infStaff.majority != null) return false;
        if (working != null ? !working.equals(infStaff.working) : infStaff.working != null) return false;
        if (certificate != null ? !certificate.equals(infStaff.certificate) : infStaff.certificate != null)
            return false;
        if (professionTitle != null ? !professionTitle.equals(infStaff.professionTitle) : infStaff.professionTitle != null)
            return false;
        if (lastCompany != null ? !lastCompany.equals(infStaff.lastCompany) : infStaff.lastCompany != null)
            return false;
        if (insuranceExperience != null ? !insuranceExperience.equals(infStaff.insuranceExperience) : infStaff.insuranceExperience != null)
            return false;
        if (insuranceBrokerageExperience != null ? !insuranceBrokerageExperience.equals(infStaff.insuranceBrokerageExperience) : infStaff.insuranceBrokerageExperience != null)
            return false;
        if (enabled != null ? !enabled.equals(infStaff.enabled) : infStaff.enabled != null) return false;
        if (userRelate != null ? !userRelate.equals(infStaff.userRelate) : infStaff.userRelate != null) return false;
        if (businessExpertise != null ? !businessExpertise.equals(infStaff.businessExpertise) : infStaff.businessExpertise != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (infDepartmentId != null ? infDepartmentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (diploma != null ? diploma.hashCode() : 0);
        result = 31 * result + (graduateSchool != null ? graduateSchool.hashCode() : 0);
        result = 31 * result + (graduateTime != null ? graduateTime.hashCode() : 0);
        result = 31 * result + (majority != null ? majority.hashCode() : 0);
        result = 31 * result + (working != null ? working.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (professionTitle != null ? professionTitle.hashCode() : 0);
        result = 31 * result + (lastCompany != null ? lastCompany.hashCode() : 0);
        result = 31 * result + (insuranceExperience != null ? insuranceExperience.hashCode() : 0);
        result = 31 * result + (insuranceBrokerageExperience != null ? insuranceBrokerageExperience.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (userRelate != null ? userRelate.hashCode() : 0);
        result = 31 * result + (businessExpertise != null ? businessExpertise.hashCode() : 0);
        return result;
    }
}
