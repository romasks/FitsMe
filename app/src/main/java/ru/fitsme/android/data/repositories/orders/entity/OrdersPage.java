package ru.fitsme.android.data.repositories.orders.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.fitsme.android.domain.entities.order.Order;

public class OrdersPage {

    @SerializedName("count")
    private int ordersCount;

    @SerializedName("current")
    private Integer currentPage;

    @SerializedName("next")
    private Integer nextPage;

    @SerializedName("previous")
    private Integer previousPage;

    @SerializedName("items")
    private List<Order> ordersList;

    public int getOrdersCount() {
        return ordersCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }
}
