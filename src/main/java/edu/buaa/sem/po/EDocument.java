package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/26.
 */
@Entity
@Table(name = "e_document", schema = "security_system", catalog = "")
public class EDocument {
    private long id;
    private String number;
    private String name;
    private String path;
    private Timestamp uploadTime;
    private String size;
    private String type;
    private String encryptMethod;
    private String encryptCode;
    private Integer viewTimes;
    private Integer downloadTimes;
    private String abstracts;
    private Long editionId;
    private String encryptionStatus;
    private String auditStatus;
    private String shareStatus;
    private Long uploadUserId;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "upload_time")
    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Basic
    @Column(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "encrypt_method")
    public String getEncryptMethod() {
        return encryptMethod;
    }

    public void setEncryptMethod(String encryptMethod) {
        this.encryptMethod = encryptMethod;
    }

    @Basic
    @Column(name = "encrypt_code")
    public String getEncryptCode() {
        return encryptCode;
    }

    public void setEncryptCode(String encryptCode) {
        this.encryptCode = encryptCode;
    }

    @Basic
    @Column(name = "view_times")
    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }

    @Basic
    @Column(name = "download_times")
    public Integer getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Integer downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    @Basic
    @Column(name = "abstracts")
    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    @Basic
    @Column(name = "edition_id")
    public Long getEditionId() {
        return editionId;
    }

    public void setEditionId(Long editionId) {
        this.editionId = editionId;
    }

    @Basic
    @Column(name = "encryption_status")
    public String getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(String encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    @Basic
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Basic
    @Column(name = "share_status")
    public String getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(String shareStatus) {
        this.shareStatus = shareStatus;
    }

    @Basic
    @Column(name = "upload_user_id")
    public Long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EDocument eDocument = (EDocument) o;

        if (id != eDocument.id) return false;
        if (number != null ? !number.equals(eDocument.number) : eDocument.number != null) return false;
        if (name != null ? !name.equals(eDocument.name) : eDocument.name != null) return false;
        if (path != null ? !path.equals(eDocument.path) : eDocument.path != null) return false;
        if (uploadTime != null ? !uploadTime.equals(eDocument.uploadTime) : eDocument.uploadTime != null) return false;
        if (size != null ? !size.equals(eDocument.size) : eDocument.size != null) return false;
        if (type != null ? !type.equals(eDocument.type) : eDocument.type != null) return false;
        if (encryptMethod != null ? !encryptMethod.equals(eDocument.encryptMethod) : eDocument.encryptMethod != null)
            return false;
        if (encryptCode != null ? !encryptCode.equals(eDocument.encryptCode) : eDocument.encryptCode != null)
            return false;
        if (viewTimes != null ? !viewTimes.equals(eDocument.viewTimes) : eDocument.viewTimes != null) return false;
        if (downloadTimes != null ? !downloadTimes.equals(eDocument.downloadTimes) : eDocument.downloadTimes != null)
            return false;
        if (abstracts != null ? !abstracts.equals(eDocument.abstracts) : eDocument.abstracts != null) return false;
        if (editionId != null ? !editionId.equals(eDocument.editionId) : eDocument.editionId != null) return false;
        if (encryptionStatus != null ? !encryptionStatus.equals(eDocument.encryptionStatus) : eDocument.encryptionStatus != null)
            return false;
        if (auditStatus != null ? !auditStatus.equals(eDocument.auditStatus) : eDocument.auditStatus != null)
            return false;
        if (shareStatus != null ? !shareStatus.equals(eDocument.shareStatus) : eDocument.shareStatus != null)
            return false;
        if (uploadUserId != null ? !uploadUserId.equals(eDocument.uploadUserId) : eDocument.uploadUserId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (encryptMethod != null ? encryptMethod.hashCode() : 0);
        result = 31 * result + (encryptCode != null ? encryptCode.hashCode() : 0);
        result = 31 * result + (viewTimes != null ? viewTimes.hashCode() : 0);
        result = 31 * result + (downloadTimes != null ? downloadTimes.hashCode() : 0);
        result = 31 * result + (abstracts != null ? abstracts.hashCode() : 0);
        result = 31 * result + (editionId != null ? editionId.hashCode() : 0);
        result = 31 * result + (encryptionStatus != null ? encryptionStatus.hashCode() : 0);
        result = 31 * result + (auditStatus != null ? auditStatus.hashCode() : 0);
        result = 31 * result + (shareStatus != null ? shareStatus.hashCode() : 0);
        result = 31 * result + (uploadUserId != null ? uploadUserId.hashCode() : 0);
        return result;
    }
}
