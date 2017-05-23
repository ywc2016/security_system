package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "sal_order", schema = "amc", catalog = "")
public class SalOrder {
    private long id;
    private String number;
    private Long customerId;
    private Long staffId;
    private String status;
    private String description;
    private String isReturn;
    private Double amount;

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
    @Column(name = "staff_id")
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    @Column(name = "is_return")
    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalOrder salOrder = (SalOrder) o;

        if (id != salOrder.id) return false;
        if (number != null ? !number.equals(salOrder.number) : salOrder.number != null) return false;
        if (customerId != null ? !customerId.equals(salOrder.customerId) : salOrder.customerId != null) return false;
        if (staffId != null ? !staffId.equals(salOrder.staffId) : salOrder.staffId != null) return false;
        if (status != null ? !status.equals(salOrder.status) : salOrder.status != null) return false;
        if (description != null ? !description.equals(salOrder.description) : salOrder.description != null)
            return false;
        if (isReturn != null ? !isReturn.equals(salOrder.isReturn) : salOrder.isReturn != null) return false;
        if (amount != null ? !amount.equals(salOrder.amount) : salOrder.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (staffId != null ? staffId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isReturn != null ? isReturn.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
