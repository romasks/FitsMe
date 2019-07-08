package ru.fitsme.android.data.repositories.orders.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.data.mapper.Mapper;
import ru.fitsme.android.data.model.OrderRequest;
import ru.fitsme.android.data.model.OrderResponse;
import ru.fitsme.android.domain.model.Order;
import timber.log.Timber;

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
    private List<OrderResponse> orderResponsesList;

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

    public List<Order> getOrdersList(Mapper<OrderResponse, OrderRequest, Order> mapper) {
        Timber.d("mapper: %s", mapper);
        List<Order> ordersList = new ArrayList<>();
        for (OrderResponse orderResponse : orderResponsesList) {
            Timber.d("orderResponse: %s", orderResponse);
            if (orderResponse != null)
                ordersList.add(mapper.mapFromEntity(orderResponse));
        }
        return ordersList;
    }
}
