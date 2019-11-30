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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return getOrderId() == that.getOrderId() &&
                getCity().equals(that.getCity()) &&
                getStreet().equals(that.getStreet()) &&
                getHouseNumber().equals(that.getHouseNumber()) &&
                getApartment().equals(that.getApartment()) &&
                getPhoneNumber().equals(that.getPhoneNumber()) &&
                getOrderCreateDate().equals(that.getOrderCreateDate()) &&
                getOrderUpdatedDate().equals(that.getOrderUpdatedDate()) &&
                getOrderStatus() == that.getOrderStatus() &&
                getOrderItemList() == that.getOrderItemList();
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + (int) getOrderId();
        result = prime * result + getCity().hashCode();
        result = prime * result + getStreet().hashCode();
        result = prime * result + getHouseNumber().hashCode();
        result = prime * result + getApartment().hashCode();
        result = prime * result + getPhoneNumber().hashCode();
        result = prime * result + getOrderCreateDate().hashCode();
        result = prime * result + getOrderUpdatedDate().hashCode();
        result = prime * result + getOrderStatus().hashCode();
        result = prime * result + getOrderItemList().hashCode();
        return result;
    }
}
