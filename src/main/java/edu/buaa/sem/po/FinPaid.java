package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "fin_paid", schema = "amc", catalog = "")
public class FinPaid {
    private long id;
    private String number;
    private long purchaseId;
    private Double amount;
    private String payableNumber;

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
    @Column(name = "purchase_id")
    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
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
    @Column(name = "payable_number")
    public String getPayableNumber() {
        return payableNumber;
    }

    public void setPayableNumber(String payableNumber) {
        this.payableNumber = payableNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinPaid finPaid = (FinPaid) o;

        if (id != finPaid.id) return false;
        if (purchaseId != finPaid.purchaseId) return false;
        if (number != null ? !number.equals(finPaid.number) : finPaid.number != null) return false;
        if (amount != null ? !amount.equals(finPaid.amount) : finPaid.amount != null) return false;
        if (payableNumber != null ? !payableNumber.equals(finPaid.payableNumber) : finPaid.payableNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (int) (purchaseId ^ (purchaseId >>> 32));
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (payableNumber != null ? payableNumber.hashCode() : 0);
        return result;
    }
}
