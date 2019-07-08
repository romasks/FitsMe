package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersRepository {

    @NonNull
    OrdersPage getOrders(OrderStatus status) throws AppException;

    void makeOrder(Order order) throws AppException;
}
