package ru.fitsme.android.domain.entities.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("id")
    private long orderId;

    @SerializedName("address")
    private String destinationAdderss;

    @SerializedName("tel")
    private String phoneNumber;

    @SerializedName("created")
    private String orderCreateDate;

    @SerializedName("updated")
    private String orderUpdatedDate;

    @SerializedName("status")
    private String orderStatus;

    @SerializedName("orderitems")
    private List<OrderItem> orderItemList;

    public long getOrderId() {
        return orderId;
    }

    public String getDestinationAdderss() {
        return destinationAdderss;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public String getOrderUpdatedDate() {
        return orderUpdatedDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }
}
