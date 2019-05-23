package ru.fitsme.android.data.model;

import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.utils.OrderStatus;

public class OrderRequest {
    @SerializedName("tel") public String phoneNumber;
    @SerializedName("street") public String street;
    @SerializedName("house_number") public String houseNumber;
    @SerializedName("apartment") public String apartment;
    @SerializedName("status") public OrderStatus status;

    public OrderRequest(String phoneNumber, String street, String houseNumber, String apartment, OrderStatus status) {
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.status = status;
    }
}
