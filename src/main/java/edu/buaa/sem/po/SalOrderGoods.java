package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "sal_order_goods", schema = "amc", catalog = "")
public class SalOrderGoods {
    private long id;
    private long orderId;
    private long goodsId;
    private Integer needNumber;
    private Integer suppliedNumber;
    private Integer availableNumber;
    private String status;
    private Double unitPrice;
    private String isReturn;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id")
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "goods_id")
    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "need_number")
    public Integer getNeedNumber() {
        return needNumber;
    }

    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
    }

    @Basic
    @Column(name = "supplied_number")
    public Integer getSuppliedNumber() {
        return suppliedNumber;
    }

    public void setSuppliedNumber(Integer suppliedNumber) {
        this.suppliedNumber = suppliedNumber;
    }

    @Basic
    @Column(name = "available_number")
    public Integer getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(Integer availableNumber) {
        this.availableNumber = availableNumber;
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
    @Column(name = "unit_price")
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "is_return")
    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalOrderGoods that = (SalOrderGoods) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (goodsId != that.goodsId) return false;
        if (needNumber != null ? !needNumber.equals(that.needNumber) : that.needNumber != null) return false;
        if (suppliedNumber != null ? !suppliedNumber.equals(that.suppliedNumber) : that.suppliedNumber != null)
            return false;
        if (availableNumber != null ? !availableNumber.equals(that.availableNumber) : that.availableNumber != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;
        if (isReturn != null ? !isReturn.equals(that.isReturn) : that.isReturn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (int) (goodsId ^ (goodsId >>> 32));
        result = 31 * result + (needNumber != null ? needNumber.hashCode() : 0);
        result = 31 * result + (suppliedNumber != null ? suppliedNumber.hashCode() : 0);
        result = 31 * result + (availableNumber != null ? availableNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (isReturn != null ? isReturn.hashCode() : 0);
        return result;
    }
}
