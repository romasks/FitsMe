package ru.fitsme.android.domain.interactors.orders;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.presentation.model.OrderViewModel;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersInteractor extends BaseInteractor{

    @NonNull
    Single<OrderViewModel> getSingleOrder(OrderStatus status);

    @NonNull
    Completable removeItemFromOrder(int index);

    @NonNull
    Completable restoreItemToOrder(int index);

    LiveData<PagedList<OrderItem>> getPagedListLiveData();

    Completable makeOrder(OrderViewModel order);
}
