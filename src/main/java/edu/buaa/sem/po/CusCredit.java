package edu.buaa.sem.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "cus_credit", schema = "amc", catalog = "")
public class CusCredit {
    private long id;
    private String number;
    private Long customerId;
    private Double debt;
    private Timestamp arrearsDate;
    private String isReceived;
    private String description;

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
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "debt")
    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    @Basic
    @Column(name = "arrears_date")
    public Timestamp getArrearsDate() {
        return arrearsDate;
    }

    public void setArrearsDate(Timestamp arrearsDate) {
        this.arrearsDate = arrearsDate;
    }

    @Basic
    @Column(name = "is_received")
    public String getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(String isReceived) {
        this.isReceived = isReceived;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CusCredit cusCredit = (CusCredit) o;

        if (id != cusCredit.id) return false;
        if (number != null ? !number.equals(cusCredit.number) : cusCredit.number != null) return false;
        if (customerId != null ? !customerId.equals(cusCredit.customerId) : cusCredit.customerId != null) return false;
        if (debt != null ? !debt.equals(cusCredit.debt) : cusCredit.debt != null) return false;
        if (arrearsDate != null ? !arrearsDate.equals(cusCredit.arrearsDate) : cusCredit.arrearsDate != null)
            return false;
        if (isReceived != null ? !isReceived.equals(cusCredit.isReceived) : cusCredit.isReceived != null) return false;
        if (description != null ? !description.equals(cusCredit.description) : cusCredit.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (debt != null ? debt.hashCode() : 0);
        result = 31 * result + (arrearsDate != null ? arrearsDate.hashCode() : 0);
        result = 31 * result + (isReceived != null ? isReceived.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
