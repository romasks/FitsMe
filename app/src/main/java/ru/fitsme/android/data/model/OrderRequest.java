package ru.fitsme.android.data.model;

import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.utils.OrderStatus;

public class OrderRequest {
    @SerializedName("tel") public String phoneNumber;
    @SerializedName("street") public String street;
    @SerializedName("house_number") public String houseNumber;
    @SerializedName("apartment") public String apartment;
    @SerializedName("status") public OrderStatus status;

    public OrderRequest(Order order) {
        this.phoneNumber = order.phoneNumber;
        this.street = order.street;
        this.houseNumber = order.houseNumber;
        this.apartment = order.apartment;
        this.status = OrderStatus.FM;
    }

    public OrderRequest(String phoneNumber, String street, String houseNumber, String apartment, OrderStatus status) {
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.status = status;
    }
}
