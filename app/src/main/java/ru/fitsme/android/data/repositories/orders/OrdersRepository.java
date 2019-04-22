package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.api.WebLoader;
import ru.fitsme.android.data.entities.response.orders.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.data.entities.exceptions.AppException;
import ru.fitsme.android.utils.OrderStatus;

public class OrdersRepository implements IOrdersRepository {

    private final WebLoader webLoader;

    @Inject
    OrdersRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public OrdersPage getOrders(int page) throws AppException {
        return webLoader.getOrders(page);
    }

    @Override
    public void makeOrder(
            int orderId, String phoneNumber, String street, String houseNumber,
            String apartment, OrderStatus orderStatus
    ) throws AppException {

        webLoader.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus);
    }
}
