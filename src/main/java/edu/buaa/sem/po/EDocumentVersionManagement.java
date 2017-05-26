package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/26.
 */
@Entity
@Table(name = "e_document_version_management", schema = "security_system", catalog = "")
public class EDocumentVersionManagement {
    private long id;
    private String number;
    private String name;
    private Timestamp latestUploadTime;
    private Timestamp earliestUploadTime;
    private String type;
    private Integer viewTimes;
    private Integer downloadTimes;
    private Integer existEditions;

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
    @Column(name = "latest_upload_time")
    public Timestamp getLatestUploadTime() {
        return latestUploadTime;
    }

    public void setLatestUploadTime(Timestamp latestUploadTime) {
        this.latestUploadTime = latestUploadTime;
    }

    @Basic
    @Column(name = "earliest_upload_time")
    public Timestamp getEarliestUploadTime() {
        return earliestUploadTime;
    }

    public void setEarliestUploadTime(Timestamp earliestUploadTime) {
        this.earliestUploadTime = earliestUploadTime;
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
    @Column(name = "exist_editions")
    public Integer getExistEditions() {
        return existEditions;
    }

    public void setExistEditions(Integer existEditions) {
        this.existEditions = existEditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EDocumentVersionManagement that = (EDocumentVersionManagement) o;

        if (id != that.id) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (latestUploadTime != null ? !latestUploadTime.equals(that.latestUploadTime) : that.latestUploadTime != null)
            return false;
        if (earliestUploadTime != null ? !earliestUploadTime.equals(that.earliestUploadTime) : that.earliestUploadTime != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (viewTimes != null ? !viewTimes.equals(that.viewTimes) : that.viewTimes != null) return false;
        if (downloadTimes != null ? !downloadTimes.equals(that.downloadTimes) : that.downloadTimes != null)
            return false;
        if (existEditions != null ? !existEditions.equals(that.existEditions) : that.existEditions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (latestUploadTime != null ? latestUploadTime.hashCode() : 0);
        result = 31 * result + (earliestUploadTime != null ? earliestUploadTime.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (viewTimes != null ? viewTimes.hashCode() : 0);
        result = 31 * result + (downloadTimes != null ? downloadTimes.hashCode() : 0);
        result = 31 * result + (existEditions != null ? existEditions.hashCode() : 0);
        return result;
    }
}
