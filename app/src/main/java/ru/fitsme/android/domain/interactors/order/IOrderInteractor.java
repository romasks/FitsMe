package ru.fitsme.android.domain.interactors.order;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;

public interface IOrderInteractor {

    @NonNull
    Single<OrderItem> getSingleOrderItem(int index);

    @NonNull
    Completable makeOrder(OrderItem order);
}
