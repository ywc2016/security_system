package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "sys_user_role", schema = "amc", catalog = "")
public class SysUserRole {
    private long id;
    private Long sysUserId;
    private Long sysRoleId;
    private String enabled;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sys_user_id")
    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    @Basic
    @Column(name = "sys_role_id")
    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    @Basic
    @Column(name = "enabled")
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUserRole that = (SysUserRole) o;

        if (id != that.id) return false;
        if (sysUserId != null ? !sysUserId.equals(that.sysUserId) : that.sysUserId != null) return false;
        if (sysRoleId != null ? !sysRoleId.equals(that.sysRoleId) : that.sysRoleId != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sysUserId != null ? sysUserId.hashCode() : 0);
        result = 31 * result + (sysRoleId != null ? sysRoleId.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }
}
