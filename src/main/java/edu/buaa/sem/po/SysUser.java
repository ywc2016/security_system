package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "sys_user", schema = "amc", catalog = "")
public class SysUser {
    private long id;
    private String name;
    private String password;
    private String description;
    private String enabled;
    private String email;
    private Timestamp creatTime;
    private String headImageUrl;
    private String userType;
    private String selfIntroduction;
    private String ip;
    private String checkStatus;
    private String oauthType;
    private String oauthId;
    private String emailConfirmed;
    private Timestamp lastLoginTime;
    private String emailCode;
    private Timestamp emailTime;
    private String emailHash;
    private Integer maxLogin;
    private String loginIp;
    private Long staffRelate;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "creat_time")
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Basic
    @Column(name = "head_image_url")
    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    @Basic
    @Column(name = "user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "self_introduction")
    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "check_status")
    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Basic
    @Column(name = "oauth_type")
    public String getOauthType() {
        return oauthType;
    }

    public void setOauthType(String oauthType) {
        this.oauthType = oauthType;
    }

    @Basic
    @Column(name = "oauth_id")
    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @Basic
    @Column(name = "email_confirmed")
    public String getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(String emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    @Basic
    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "email_code")
    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    @Basic
    @Column(name = "email_time")
    public Timestamp getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Timestamp emailTime) {
        this.emailTime = emailTime;
    }

    @Basic
    @Column(name = "email_hash")
    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    @Basic
    @Column(name = "max_login")
    public Integer getMaxLogin() {
        return maxLogin;
    }

    public void setMaxLogin(Integer maxLogin) {
        this.maxLogin = maxLogin;
    }

    @Basic
    @Column(name = "login_ip")
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Basic
    @Column(name = "staff_relate")
    public Long getStaffRelate() {
        return staffRelate;
    }

    public void setStaffRelate(Long staffRelate) {
        this.staffRelate = staffRelate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUser sysUser = (SysUser) o;

        if (id != sysUser.id) return false;
        if (name != null ? !name.equals(sysUser.name) : sysUser.name != null) return false;
        if (password != null ? !password.equals(sysUser.password) : sysUser.password != null) return false;
        if (description != null ? !description.equals(sysUser.description) : sysUser.description != null) return false;
        if (enabled != null ? !enabled.equals(sysUser.enabled) : sysUser.enabled != null) return false;
        if (email != null ? !email.equals(sysUser.email) : sysUser.email != null) return false;
        if (creatTime != null ? !creatTime.equals(sysUser.creatTime) : sysUser.creatTime != null) return false;
        if (headImageUrl != null ? !headImageUrl.equals(sysUser.headImageUrl) : sysUser.headImageUrl != null)
            return false;
        if (userType != null ? !userType.equals(sysUser.userType) : sysUser.userType != null) return false;
        if (selfIntroduction != null ? !selfIntroduction.equals(sysUser.selfIntroduction) : sysUser.selfIntroduction != null)
            return false;
        if (ip != null ? !ip.equals(sysUser.ip) : sysUser.ip != null) return false;
        if (checkStatus != null ? !checkStatus.equals(sysUser.checkStatus) : sysUser.checkStatus != null) return false;
        if (oauthType != null ? !oauthType.equals(sysUser.oauthType) : sysUser.oauthType != null) return false;
        if (oauthId != null ? !oauthId.equals(sysUser.oauthId) : sysUser.oauthId != null) return false;
        if (emailConfirmed != null ? !emailConfirmed.equals(sysUser.emailConfirmed) : sysUser.emailConfirmed != null)
            return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(sysUser.lastLoginTime) : sysUser.lastLoginTime != null)
            return false;
        if (emailCode != null ? !emailCode.equals(sysUser.emailCode) : sysUser.emailCode != null) return false;
        if (emailTime != null ? !emailTime.equals(sysUser.emailTime) : sysUser.emailTime != null) return false;
        if (emailHash != null ? !emailHash.equals(sysUser.emailHash) : sysUser.emailHash != null) return false;
        if (maxLogin != null ? !maxLogin.equals(sysUser.maxLogin) : sysUser.maxLogin != null) return false;
        if (loginIp != null ? !loginIp.equals(sysUser.loginIp) : sysUser.loginIp != null) return false;
        if (staffRelate != null ? !staffRelate.equals(sysUser.staffRelate) : sysUser.staffRelate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
        result = 31 * result + (headImageUrl != null ? headImageUrl.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (selfIntroduction != null ? selfIntroduction.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (checkStatus != null ? checkStatus.hashCode() : 0);
        result = 31 * result + (oauthType != null ? oauthType.hashCode() : 0);
        result = 31 * result + (oauthId != null ? oauthId.hashCode() : 0);
        result = 31 * result + (emailConfirmed != null ? emailConfirmed.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (emailCode != null ? emailCode.hashCode() : 0);
        result = 31 * result + (emailTime != null ? emailTime.hashCode() : 0);
        result = 31 * result + (emailHash != null ? emailHash.hashCode() : 0);
        result = 31 * result + (maxLogin != null ? maxLogin.hashCode() : 0);
        result = 31 * result + (loginIp != null ? loginIp.hashCode() : 0);
        result = 31 * result + (staffRelate != null ? staffRelate.hashCode() : 0);
        return result;
    }
}
