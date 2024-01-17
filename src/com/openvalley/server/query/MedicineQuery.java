package com.openvalley.server.query;

import com.openvalley.ui.base.UIConstants;

import java.io.Serializable;
import java.util.Objects;

public class MedicineQuery implements Serializable {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private String expire;
    private String categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if(obj==null||getClass()!=obj.getClass()) return  false;
        MedicineQuery mq=(MedicineQuery) obj;
        return Objects.equals(name,mq.name)&&Objects.equals(minPrice,mq.minPrice)&&Objects.equals(maxPrice,mq.maxPrice)&&Objects.equals(categoryId,mq.categoryId);
    }

    public static MedicineQuery from(String medicineNameStr, String medicineMinPriceStr, String medicineMaxPriceStr, String categoryId, String datePickStr)
    {
        MedicineQuery target = new MedicineQuery();
        target.setName(medicineNameStr.isEmpty() ? null : medicineNameStr);
        target.setMinPrice(medicineMinPriceStr.isEmpty()? null : Double.valueOf(medicineMinPriceStr));
        target.setMaxPrice(medicineMinPriceStr.isEmpty()? null : Double.valueOf(medicineMaxPriceStr));

        if (categoryId.isEmpty() || categoryId.equals(String.valueOf(UIConstants.SELECTED_ID))){
            target.setCategoryId(null);
        }else {
            target.setCategoryId(categoryId);
        }

        target.setExpire(datePickStr.isEmpty() ? null : datePickStr);
        return target;
    }
}
