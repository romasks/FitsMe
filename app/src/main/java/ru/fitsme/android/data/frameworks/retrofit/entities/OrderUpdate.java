package ru.fitsme.android.data.frameworks.retrofit.entities;

import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.utils.OrderStatus;

public class OrderUpdate {
    @SerializedName("tel")
    private String phoneNumber;

    @SerializedName("street")
    private String street;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("apartment")
    private String apartment;

    @SerializedName("status")
    private OrderStatus status;

    public OrderUpdate(String phoneNumber, String street, String houseNumber, String apartment, OrderStatus status) {
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public OrderStatus getStatus() {
        return status;
    }
}
