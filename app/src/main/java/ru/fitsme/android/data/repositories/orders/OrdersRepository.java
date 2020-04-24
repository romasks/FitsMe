package ru.fitsme.android.data.repositories.orders;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.Collections;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.OrdersInteractor;
import timber.log.Timber;

public class OrdersRepository extends PageKeyedDataSource<Integer, Order>
        implements IOrdersRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    OrdersRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Order> callback) {
        OrdersInteractor.setMessage(App.getInstance().getString(R.string.loading));
        webLoader.getOrdersPage(1)
                .subscribe(response -> {
                    OrdersPage ordersPage = response.getResponse();
                    if (ordersPage != null) {
                        callback.onResult(ordersPage.getOrdersList(), null, ordersPage.getNextPage());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null, null);
                    }
                }, Timber::e);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {
        OrdersInteractor.setMessage(App.getInstance().getString(R.string.loading));
        webLoader.getOrdersPage(params.key)
                .subscribe(response -> {
                    OrdersPage ordersPage = response.getResponse();
                    if (ordersPage != null) {
                        callback.onResult(ordersPage.getOrdersList(), ordersPage.getNextPage());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null);
                    }
                }, Timber::e);
    }
}
