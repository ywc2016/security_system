package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "pur_order", schema = "amc", catalog = "")
public class PurOrder {
    private long id;
    private String number;
    private Long supplierId;
    private Long staffId;
    private Double amount;
    private String purchaseSituation;
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
    @Column(name = "supplier_id")
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "purchase_situation")
    public String getPurchaseSituation() {
        return purchaseSituation;
    }

    public void setPurchaseSituation(String purchaseSituation) {
        this.purchaseSituation = purchaseSituation;
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

        PurOrder purOrder = (PurOrder) o;

        if (id != purOrder.id) return false;
        if (number != null ? !number.equals(purOrder.number) : purOrder.number != null) return false;
        if (supplierId != null ? !supplierId.equals(purOrder.supplierId) : purOrder.supplierId != null) return false;
        if (staffId != null ? !staffId.equals(purOrder.staffId) : purOrder.staffId != null) return false;
        if (amount != null ? !amount.equals(purOrder.amount) : purOrder.amount != null) return false;
        if (purchaseSituation != null ? !purchaseSituation.equals(purOrder.purchaseSituation) : purOrder.purchaseSituation != null)
            return false;
        if (description != null ? !description.equals(purOrder.description) : purOrder.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (staffId != null ? staffId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (purchaseSituation != null ? purchaseSituation.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
