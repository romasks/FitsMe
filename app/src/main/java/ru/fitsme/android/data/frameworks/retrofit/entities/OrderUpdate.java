package ru.fitsme.android.data.frameworks.retrofit.entities;

import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.utils.OrderStatus;

public class OrderUpdate {
    @SerializedName("tel")
    private String phoneNumber;

    @SerializedName("address")
    private String destinationAdderss;

    @SerializedName("status")
    private OrderStatus status;

    public OrderUpdate(String phoneNumber,
                       String destinationAdderss,
                       OrderStatus status) {
        this.phoneNumber = phoneNumber;
        this.destinationAdderss = destinationAdderss;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDestinationAdderss() {
        return destinationAdderss;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
