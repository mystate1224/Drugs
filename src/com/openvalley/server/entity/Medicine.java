package com.openvalley.server.entity;

import java.util.Date;

public class Medicine {
    private Integer id;
    private String medicineNo;
    private String name;
    private String factoryAddress;
    private String description;
    private double price;
    private Date expire;
    private String unit;
    private Integer number;
    private Integer categoryId;
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineNo() {
        return medicineNo;
    }

    public void setMedicineNo(String medicineNo) {
        this.medicineNo = medicineNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactoryAddress() {
        return factoryAddress;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", medicineNo='" + medicineNo + '\'' +
                ", name='" + name + '\'' +
                ", factoryAddress='" + factoryAddress + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", expire=" + expire +
                ", unit='" + unit + '\'' +
                ", number=" + number +
                ", categoryId=" + categoryId +
                ", deleted=" + deleted +
                '}';
    }
}
