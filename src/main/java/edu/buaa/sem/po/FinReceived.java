package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "fin_received", schema = "amc", catalog = "")
public class FinReceived {
    private long id;
    private String number;
    private Long orderId;
    private Double amount;
    private String receiveableNumber;

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
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @Column(name = "receiveable_number")
    public String getReceiveableNumber() {
        return receiveableNumber;
    }

    public void setReceiveableNumber(String receiveableNumber) {
        this.receiveableNumber = receiveableNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinReceived that = (FinReceived) o;

        if (id != that.id) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (receiveableNumber != null ? !receiveableNumber.equals(that.receiveableNumber) : that.receiveableNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (receiveableNumber != null ? receiveableNumber.hashCode() : 0);
        return result;
    }
}
