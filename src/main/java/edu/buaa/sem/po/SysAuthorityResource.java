package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/26.
 */
@Entity
@Table(name = "sys_authority_resource", schema = "security_system", catalog = "")
public class SysAuthorityResource {
    private long id;
    private Long sysAuthorityId;
    private Long sysResourceId;
    private String enabled;
    private String type;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sys_authority_id")
    public Long getSysAuthorityId() {
        return sysAuthorityId;
    }

    public void setSysAuthorityId(Long sysAuthorityId) {
        this.sysAuthorityId = sysAuthorityId;
    }

    @Basic
    @Column(name = "sys_resource_id")
    public Long getSysResourceId() {
        return sysResourceId;
    }

    public void setSysResourceId(Long sysResourceId) {
        this.sysResourceId = sysResourceId;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysAuthorityResource that = (SysAuthorityResource) o;

        if (id != that.id) return false;
        if (sysAuthorityId != null ? !sysAuthorityId.equals(that.sysAuthorityId) : that.sysAuthorityId != null)
            return false;
        if (sysResourceId != null ? !sysResourceId.equals(that.sysResourceId) : that.sysResourceId != null)
            return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sysAuthorityId != null ? sysAuthorityId.hashCode() : 0);
        result = 31 * result + (sysResourceId != null ? sysResourceId.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
