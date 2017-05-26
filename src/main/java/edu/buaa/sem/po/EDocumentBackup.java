package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/26.
 */
@Entity
@Table(name = "e_document_backup", schema = "security_system", catalog = "")
public class EDocumentBackup {
    private long id;
    private String number;
    private String name;
    private String path;
    private Timestamp backupTime;
    private String size;
    private String encryptMethod;
    private String encryptCode;
    private Integer downloadTimes;
    private String abstracts;
    private String encryptionStatus;

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
    @Column(name = "backup_time")
    public Timestamp getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Timestamp backupTime) {
        this.backupTime = backupTime;
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
    @Column(name = "encryption_status")
    public String getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(String encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EDocumentBackup that = (EDocumentBackup) o;

        if (id != that.id) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (backupTime != null ? !backupTime.equals(that.backupTime) : that.backupTime != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (encryptMethod != null ? !encryptMethod.equals(that.encryptMethod) : that.encryptMethod != null)
            return false;
        if (encryptCode != null ? !encryptCode.equals(that.encryptCode) : that.encryptCode != null) return false;
        if (downloadTimes != null ? !downloadTimes.equals(that.downloadTimes) : that.downloadTimes != null)
            return false;
        if (abstracts != null ? !abstracts.equals(that.abstracts) : that.abstracts != null) return false;
        if (encryptionStatus != null ? !encryptionStatus.equals(that.encryptionStatus) : that.encryptionStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (backupTime != null ? backupTime.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (encryptMethod != null ? encryptMethod.hashCode() : 0);
        result = 31 * result + (encryptCode != null ? encryptCode.hashCode() : 0);
        result = 31 * result + (downloadTimes != null ? downloadTimes.hashCode() : 0);
        result = 31 * result + (abstracts != null ? abstracts.hashCode() : 0);
        result = 31 * result + (encryptionStatus != null ? encryptionStatus.hashCode() : 0);
        return result;
    }
}
