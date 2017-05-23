package edu.buaa.sem.po;

import javax.persistence.*;

/**
 * Created by ywcrm on 2017/5/23.
 */
@Entity
@Table(name = "sto_goods", schema = "amc", catalog = "")
public class StoGoods {
    private long id;
    private String number;
    private String name;
    private Integer inventory;
    private String situation;
    private Integer need;
    private Integer suggestNumber;
    private Integer salseNumber;
    private Double salsePrice;
    private String location;
    private String pictureUrl;

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
    @Column(name = "inventory")
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Basic
    @Column(name = "situation")
    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Basic
    @Column(name = "need")
    public Integer getNeed() {
        return need;
    }

    public void setNeed(Integer need) {
        this.need = need;
    }

    @Basic
    @Column(name = "suggest_number")
    public Integer getSuggestNumber() {
        return suggestNumber;
    }

    public void setSuggestNumber(Integer suggestNumber) {
        this.suggestNumber = suggestNumber;
    }

    @Basic
    @Column(name = "salse_number")
    public Integer getSalseNumber() {
        return salseNumber;
    }

    public void setSalseNumber(Integer salseNumber) {
        this.salseNumber = salseNumber;
    }

    @Basic
    @Column(name = "salse_price")
    public Double getSalsePrice() {
        return salsePrice;
    }

    public void setSalsePrice(Double salsePrice) {
        this.salsePrice = salsePrice;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "picture_url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoGoods stoGoods = (StoGoods) o;

        if (id != stoGoods.id) return false;
        if (number != null ? !number.equals(stoGoods.number) : stoGoods.number != null) return false;
        if (name != null ? !name.equals(stoGoods.name) : stoGoods.name != null) return false;
        if (inventory != null ? !inventory.equals(stoGoods.inventory) : stoGoods.inventory != null) return false;
        if (situation != null ? !situation.equals(stoGoods.situation) : stoGoods.situation != null) return false;
        if (need != null ? !need.equals(stoGoods.need) : stoGoods.need != null) return false;
        if (suggestNumber != null ? !suggestNumber.equals(stoGoods.suggestNumber) : stoGoods.suggestNumber != null)
            return false;
        if (salseNumber != null ? !salseNumber.equals(stoGoods.salseNumber) : stoGoods.salseNumber != null)
            return false;
        if (salsePrice != null ? !salsePrice.equals(stoGoods.salsePrice) : stoGoods.salsePrice != null) return false;
        if (location != null ? !location.equals(stoGoods.location) : stoGoods.location != null) return false;
        if (pictureUrl != null ? !pictureUrl.equals(stoGoods.pictureUrl) : stoGoods.pictureUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + (situation != null ? situation.hashCode() : 0);
        result = 31 * result + (need != null ? need.hashCode() : 0);
        result = 31 * result + (suggestNumber != null ? suggestNumber.hashCode() : 0);
        result = 31 * result + (salseNumber != null ? salseNumber.hashCode() : 0);
        result = 31 * result + (salsePrice != null ? salsePrice.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        return result;
    }
}
