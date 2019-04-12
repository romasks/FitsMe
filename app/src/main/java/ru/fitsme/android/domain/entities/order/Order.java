package ru.fitsme.android.domain.entities.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.fitsme.android.utils.OrderStatus;

public class Order {
    @SerializedName("id")
    private long orderId;

    @SerializedName("city")
    private String city;

    @SerializedName("street")
    private String street;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("apartment")
    private String apartment;

    @SerializedName("tel")
    private String phoneNumber;

    @SerializedName("created")
    private String orderCreateDate;

    @SerializedName("updated")
    private String orderUpdatedDate;

    @SerializedName("status")
    private OrderStatus orderStatus;

    @SerializedName("orderitems")
    private List<OrderItem> orderItemList;

    public long getOrderId() {
        return orderId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartment() {
        return apartment;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }
}
