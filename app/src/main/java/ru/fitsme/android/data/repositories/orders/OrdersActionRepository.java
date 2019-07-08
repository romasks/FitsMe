package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.utils.OrderStatus;

public class OrdersActionRepository implements IOrdersActionRepository {

    private final WebLoader webLoader;

    @Inject
    OrdersActionRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public OrdersPage getOrders(OrderStatus status) throws AppException {
        return webLoader.getOrders(status);
    }

    @Override
    public void makeOrder(
            long orderId, String phoneNumber, String street, String houseNumber,
            String apartment, OrderStatus orderStatus
    ) throws AppException {

        webLoader.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus);
    }

    @Override
    public void removeItemFromOrder(int clotheItemId) throws UserException {
        webLoader.deleteOrderItem(clotheItemId);
    }
}
