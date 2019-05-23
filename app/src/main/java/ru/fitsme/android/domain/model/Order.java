package ru.fitsme.android.domain.model;

import java.util.List;

import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public class Order {
    public long orderId;
    public String city;
    public String street;
    public String houseNumber;
    public String apartment;
    public String phoneNumber;
    public OrderStatus orderStatus;
    public List<OrderItem> orderItemList;

    public Order(long orderId, String city, String street, String houseNumber,
                 String apartment, String phoneNumber, OrderStatus orderStatus,
                 List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
        this.orderItemList = orderItemList;
    }
}
