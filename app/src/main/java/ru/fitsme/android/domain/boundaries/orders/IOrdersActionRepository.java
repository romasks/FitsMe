package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersActionRepository {

    @NonNull
    OrdersPage getOrders(OrderStatus status) throws AppException;

    void makeOrder(long orderId, String phoneNumber, String street, String houseNumber,
                   String apartment, OrderStatus orderStatus) throws AppException;

    Single<OrderItem> removeItemFromOrder(OrderItem item);

    Single<OrderItem> restoreItemToOrder(OrderItem item);
}
