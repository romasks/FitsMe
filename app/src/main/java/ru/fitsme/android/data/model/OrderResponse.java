package ru.fitsme.android.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public class OrderResponse {
    @SerializedName("id") public long orderId;
    @SerializedName("city") public String city;
    @SerializedName("street") public String street;
    @SerializedName("house_number") public String houseNumber;
    @SerializedName("apartment") public String apartment;
    @SerializedName("tel") public String phoneNumber;
    @SerializedName("created") public String orderCreateDate;
    @SerializedName("updated") public String orderUpdatedDate;
    @SerializedName("status") public OrderStatus orderStatus;
    @SerializedName("orderitems") public List<OrderItem> orderItemList;
}
