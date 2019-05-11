package ru.fitsme.android.domain.interactors.orders;

import android.support.annotation.NonNull;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersInteractor extends BaseInteractor {

    @NonNull
    Single<Order> getSingleOrder(int page);

    @NonNull
    Completable removeItemFromOrder(int index);

    @NonNull
    Completable restoreItemToOrder(int index);

    @NonNull
    Completable makeOrder(String phoneNumber, String street, String houseNumber, String apartment, OrderStatus orderStatus);
}
