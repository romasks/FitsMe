package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.response.orders.OrdersPage;
import ru.fitsme.android.data.entities.exceptions.AppException;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersRepository {

    @NonNull
    OrdersPage getOrders(int page) throws AppException;

    void makeOrder(int orderId, String phoneNumber, String street, String houseNumber,
                   String apartment, OrderStatus orderStatus) throws AppException;
}
