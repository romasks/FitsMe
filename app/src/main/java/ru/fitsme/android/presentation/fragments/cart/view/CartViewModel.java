package ru.fitsme.android.presentation.fragments.cart.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;

public class CartViewModel extends ViewModel {

    private final IOrdersInteractor ordersInteractor;
    private CompositeDisposable disposable;

    private CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    public void init() {
        disposable = new CompositeDisposable();
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return ordersInteractor.getPagedListLiveData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    static public class Factory implements ViewModelProvider.Factory {
        private final IOrdersInteractor ordersInteractor;

        public Factory(@NotNull IOrdersInteractor ordersInteractor) {
            this.ordersInteractor = ordersInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CartViewModel(ordersInteractor);
        }
    }
}
