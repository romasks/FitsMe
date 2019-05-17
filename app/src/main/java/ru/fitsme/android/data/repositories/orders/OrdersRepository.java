package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.utils.OrderStatus;

public class OrdersRepository implements IOrdersRepository {

    private final WebLoader webLoader;

    @Inject
    OrdersRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public OrdersPage getOrders(OrderStatus status) throws AppException {
        return webLoader.getOrders(status);
    }

    @Override
    public void makeOrder(
            int orderId, String phoneNumber, String street, String houseNumber,
            String apartment, OrderStatus orderStatus
    ) throws AppException {

        webLoader.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus);
    }
}
