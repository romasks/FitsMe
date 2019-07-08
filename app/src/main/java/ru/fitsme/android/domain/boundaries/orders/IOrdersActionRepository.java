package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersActionRepository {

    @NonNull
    OrdersPage getOrders(OrderStatus status) throws AppException;

    void makeOrder(long orderId, String phoneNumber, String street, String houseNumber,
                   String apartment, OrderStatus orderStatus) throws AppException;

    void removeItemFromOrder(int clotheItemId) throws UserException;
}
