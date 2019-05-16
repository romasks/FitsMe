package ru.fitsme.android.data.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import ru.fitsme.android.BR;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrderModel extends BaseObservable {

    private String city;
    private String street;
    private String houseNumber;
    private String apartment;
    private String phoneNumber;

    private int price;
    private int discount;
    private int totalPrice;

    public OrderModel(Order order) {
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

    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);
    }

    @Bindable
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        notifyPropertyChanged(BR.street);
    }

    @Bindable
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        notifyPropertyChanged(BR.houseNumber);
    }

    @Bindable
    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
        notifyPropertyChanged(BR.apartment);
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    @Bindable
    public int getDiscount() {
        return discount;
    }

    @Bindable
    public int getTotalPrice() {
        return totalPrice;
    }

}
