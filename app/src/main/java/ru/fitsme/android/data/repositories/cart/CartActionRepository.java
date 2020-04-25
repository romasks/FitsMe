package ru.fitsme.android.data.repositories.cart;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.cart.ICartActionRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class CartActionRepository implements ICartActionRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    CartActionRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public Single<OrdersPage> getOrders(OrderStatus status) {
        return Single.create(emitter -> webLoader.getOrders(status)
                .subscribe(ordersPageOkResponse -> {
                    OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                    if (ordersPage != null) {
                        emitter.onSuccess(ordersPage);
                    } else {
                        UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                        Timber.e(error);
                        emitter.onSuccess(new OrdersPage());
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(new OrdersPage());
                }));
    }

    @NonNull
    @Override
    public Single<OrdersPage> getOrdersWithoutStatus() {
        return Single.create(emitter -> webLoader.getOrders()
                .subscribe(ordersPageOkResponse -> {
                    OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                    if (ordersPage != null) {
                        emitter.onSuccess(ordersPage);
                    } else {
                        UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                        Timber.e(error);
                        emitter.onSuccess(new OrdersPage());
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(new OrdersPage());
                }));
    }

    @NonNull
    @Override
    public Single<OrdersPage> getReturnsOrders() {
        return Single.create(emitter -> webLoader.getReturnsOrders()
                .subscribe(ordersPageOkResponse -> {
                    OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                    if (ordersPage != null) {
                        emitter.onSuccess(ordersPage);
                    } else {
                        UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                        Timber.e(error);
                        emitter.onSuccess(new OrdersPage());
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(new OrdersPage());
                }));
    }

    @Override
    public Single<Order> makeOrder(OrderRequest orderRequest) {
        return Single.create(emitter ->
                webLoader.makeOrder(orderRequest)
                        .subscribe(orderOkResponse -> {
                            Order order = orderOkResponse.getResponse();
                            if (order != null) {
                                emitter.onSuccess(order);
                            } else {
                                UserException error = ErrorRepository.makeError(orderOkResponse.getError());
                                Timber.e(error);
                                emitter.onSuccess(new Order());
                            }
                        }, error -> {
                            Timber.e(error);
                            emitter.onSuccess(new Order());
                        }));
    }

    @Override
    public Single<Integer> removeItemFromOrder(Integer orderItemId) {
        return Single.create(emitter -> webLoader.removeItemFromOrder(orderItemId)
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        emitter.onSuccess(orderItemId);
                    } else {
                        Timber.e(response.toString());
                        emitter.onSuccess(-1);
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(-1);
                }));
    }

    @Override
    public Single<OrderItem> removeItemFromOrder(OrderItem item) {
        return Single.create(emitter -> webLoader.removeItemFromOrder(item)
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        emitter.onSuccess(item);
                    } else {
                        Timber.e(response.toString());
                        emitter.onSuccess(new OrderItem());
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(new OrderItem());
                }));
    }

    @Override
    public Single<OrderItem> restoreItemToOrder(OrderItem item) {
        return Single.create(emitter -> webLoader.restoreItemToOrder(item)
                .subscribe(orderItemOkResponse -> {
                    OrderItem restoredItem = orderItemOkResponse.getResponse();
                    if (restoredItem != null) {
                        emitter.onSuccess(restoredItem);
                    } else {
                        UserException error = ErrorRepository.makeError(orderItemOkResponse.getError());
                        Timber.e(error);
                        emitter.onSuccess(new OrderItem());
                    }
                }, error -> {
                    Timber.e(error);
                    emitter.onSuccess(new OrderItem());
                }));
    }

    @Override
    public Single<Order> getOrderById(int orderId) {
        return Single.create(emitter ->
                webLoader.getOrderById(orderId)
                        .subscribe(response -> emitter.onSuccess(response.getResponse()),
                                emitter::onError));
    }
}
