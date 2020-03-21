package ru.fitsme.android.data.repositories.orders;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.OrdersInteractor;
import timber.log.Timber;

public class OrdersRepository extends PageKeyedDataSource<Integer, OrderItem>
        implements IOrdersRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    OrdersRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, OrderItem> callback) {
        OrdersInteractor.setCartMessage(App.getInstance().getString(R.string.loading));
        webLoader.getOrdersInCart()
                .subscribe(ordersPageOkResponse -> {
                    OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                    if (ordersPage != null) {
                        List<Order> ordersList = ordersPage.getOrdersList();
                        List<OrderItem> orderItemList;
                        if (ordersList.size() == 0) {
                            orderItemList = new ArrayList<>();
                        } else {
                            orderItemList = ordersList.get(0).getOrderItemList();
                        }
                        callback.onResult(orderItemList, null, ordersPage.getNextPage());
                    } else {
                        UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<OrderItem> list = new ArrayList();
                        callback.onResult(list, null, null);
                    }
                    OrdersInteractor.setCartMessage(null);
                }, error -> {
                    Timber.e(error);
                    OrdersInteractor.setCartMessage(App.getInstance().getString(R.string.error));
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, OrderItem> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, OrderItem> callback) {
        OrdersInteractor.setCartMessage(App.getInstance().getString(R.string.loading));
        webLoader.getOrdersInCart()
                .subscribe(ordersPageOkResponse -> {
                    OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                    if (ordersPage != null) {
                        List<Order> ordersList = ordersPage.getOrdersList();
                        List<OrderItem> orderItemList;
                        if (ordersList.size() == 0) {
                            orderItemList = new ArrayList<>();
                        } else {
                            orderItemList = ordersList.get(0).getOrderItemList();
                        }
                        callback.onResult(orderItemList, ordersPage.getNextPage());
                    } else {
                        UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<OrderItem> list = new ArrayList();
                        callback.onResult(list, null);
                    }
                    OrdersInteractor.setCartMessage(null);
                }, error -> {
                    Timber.e(error);
                    OrdersInteractor.setCartMessage(App.getInstance().getString(R.string.error));
                });
    }
}
