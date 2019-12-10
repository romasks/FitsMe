package ru.fitsme.android.domain.entities.returns;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReturnsOrder {

    @SerializedName("id")
    private int id;

    @SerializedName("order")
    private int order;

    @SerializedName("payment_details")
    private String paymentDetails;

    @SerializedName("delivery_details")
    private String deliveryDetails;

    @SerializedName("delivery_details_return")
    private String deliveryDetailsReturn;

    @SerializedName("created")
    private String createdDate;

    @SerializedName("updated")
    private String updatedDate;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("returnitems")
    private List<ReturnsOrderItem> returnItemsList;

    @SerializedName("summ")
    private int summ;

    @SerializedName("count")
    private int count;

    @SerializedName("days_to_return")
    private String daysToReturn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public String getDeliveryDetailsReturn() {
        return deliveryDetailsReturn;
    }

    public void setDeliveryDetailsReturn(String deliveryDetailsReturn) {
        this.deliveryDetailsReturn = deliveryDetailsReturn;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReturnsOrderItem> getReturnItemsList() {
        return returnItemsList;
    }

    public void setReturnItemsList(List<ReturnsOrderItem> returnItemsList) {
        this.returnItemsList = returnItemsList;
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDaysToReturn() {
        return daysToReturn;
    }

    public void setDaysToReturn(String daysToReturn) {
        this.daysToReturn = daysToReturn;
    }

    public ReturnsOrder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnsOrder)) return false;
        ReturnsOrder that = (ReturnsOrder) o;
        return getId() == that.getId() &&
                getOrder() == that.getOrder() &&
                getPaymentDetails().equals(that.getPaymentDetails()) &&
                getDeliveryDetails().equals(that.getDeliveryDetails()) &&
                getDeliveryDetailsReturn().equals(that.getDeliveryDetailsReturn()) &&
                getCreatedDate().equals(that.getCreatedDate()) &&
                getUpdatedDate().equals(that.getUpdatedDate()) &&
                getDate().equals(that.getDate()) &&
                getReturnItemsList().equals(that.getReturnItemsList()) &&
                getSumm() == that.getSumm() &&
                getCount() == that.getSumm() &&
                getDaysToReturn().equals(that.getDaysToReturn());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + (int) getId();
        result = prime * result + getOrder();
        result = prime * result + getPaymentDetails().hashCode();
        result = prime * result + getDeliveryDetails().hashCode();
        result = prime * result + getDeliveryDetailsReturn().hashCode();
        result = prime * result + getCreatedDate().hashCode();
        result = prime * result + getUpdatedDate().hashCode();
        result = prime * result + getDate().hashCode();
        result = prime * result + getReturnItemsList().hashCode();
        result = prime * result + getSumm();
        result = prime * result + getCount();
        result = prime * result + getDaysToReturn().hashCode();
        return result;
    }
}
