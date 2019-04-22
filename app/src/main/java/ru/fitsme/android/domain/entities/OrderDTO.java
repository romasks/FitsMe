package ru.fitsme.android.domain.entities;

import ru.fitsme.android.data.entities.response.orders.Order;
import ru.fitsme.android.data.entities.response.orders.OrderItem;

public class OrderDTO {

    private String city;
    private String street;
    private String houseNumber;
    private String apartment;
    private String phoneNumber;

    private int price;
    private int discount;
    private int totalPrice;

    public OrderDTO(Order order) {
        this.city = order.getCity();
        this.street = order.getStreet();
        this.houseNumber = order.getHouseNumber();
        this.apartment = order.getApartment();
        this.phoneNumber = order.getPhoneNumber();

        int price = 0;
        for (OrderItem item : order.getOrderItemList()) {
            price += item.getPrice();
        }

        this.price = price;
        this.discount = 300;
        this.totalPrice = price + discount;
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

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
