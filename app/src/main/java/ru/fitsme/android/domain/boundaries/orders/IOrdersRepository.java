package ru.fitsme.android.domain.boundaries.orders;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersRepository {

    @NonNull
    Order getOrder(@NonNull String token, int page) throws AppException;

    void makeOrder(@NonNull String token, String phoneNumber,
                   String destinationAddress,
                   OrderStatus orderStatus) throws AppException;
}
