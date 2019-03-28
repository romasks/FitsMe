package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.OrderItem;

public interface IOrdersRepository {

    @NonNull
    OrderItem getOrderItem(@NonNull String token, int index) throws AppException;

    void makeOrder(@NonNull String token, OrderItem order) throws AppException;
}
