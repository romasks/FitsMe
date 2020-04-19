package ru.fitsme.android.data.repositories.returns.orders;

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
import ru.fitsme.android.domain.boundaries.retunrs.IReturnOrdersRepository;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.returns.ReturnsInteractor;
import timber.log.Timber;

public class ReturnOrdersRepository extends PageKeyedDataSource<Integer, Order>
        implements IReturnOrdersRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    ReturnOrdersRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Order> callback) {
        ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.loading));
        webLoader.getReturnOrdersPage(1)
                .subscribe(response -> {
                    OrdersPage ordersPage = response.getResponse();
                    if (ordersPage != null) {
                        callback.onResult(ordersPage.getOrdersList(), null, ordersPage.getNextPage());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null, null);
                    }
                    ReturnsInteractor.setShowMessage(null);
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {
        webLoader.getReturnOrdersPage(params.key)
                .subscribe(response -> {
                    OrdersPage ordersPage = response.getResponse();
                    if (ordersPage != null) {
                        callback.onResult(ordersPage.getOrdersList(), ordersPage.getPreviousPage());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {
        webLoader.getReturnOrdersPage(params.key)
                .subscribe(response -> {
                    OrdersPage ordersPage = response.getResponse();
                    if (ordersPage != null) {
                        callback.onResult(ordersPage.getOrdersList(), ordersPage.getNextPage());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }
}
