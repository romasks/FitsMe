package ru.fitsme.android.data.frameworks.retrofit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.utils.OrderStatus;

public class OrderRequest {
    @Expose
    private long id;

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

    public OrderRequest(long id, String phoneNumber, String street, String houseNumber, String apartment) {
        this.id = id;
        this.phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.status = OrderStatus.ISU;
    }

    public OrderRequest(OrderModel orderModel) {
        this.id = orderModel.getOrderId();
        this.phoneNumber = orderModel.getPhoneNumber().replaceAll("[^\\d]", "");
        this.street = orderModel.getStreet();
        this.houseNumber = orderModel.getHouseNumber();
        this.apartment = orderModel.getApartment();
        this.status = OrderStatus.ISU;
    }

    public long getId() {
        return id;
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
