package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class OrdersActionRepository implements IOrdersActionRepository {

    private final WebLoader webLoader;

    @Inject
    OrdersActionRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public Single<OrdersPage> getOrders(OrderStatus status){
        return Single.create(emitter -> {
            webLoader.getOrders(status)
                    .subscribe(ordersPageOkResponse -> {
                        OrdersPage ordersPage = ordersPageOkResponse.getResponse();
                        if (ordersPage != null){
                            emitter.onSuccess(ordersPage);
                        } else {
                            UserException error = ErrorRepository.makeError(ordersPageOkResponse.getError());
                            Timber.e(error);
                            emitter.onSuccess(new OrdersPage());
                        }
                    }, error -> {
                        Timber.e(error);
                        emitter.onSuccess(new OrdersPage());
                    });

        });
    }

    @Override
    public Single<Order> makeOrder(
            long orderId, String phoneNumber, String street, String houseNumber,
            String apartment, OrderStatus orderStatus){
        return Single.create(emitter ->
                webLoader.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus)
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
    public Single<OrderItem> removeItemFromOrder(OrderItem item) {
        return Single.create(emitter -> {
            webLoader.removeItemFromOrder(item)
                    .subscribe(response -> {
                                    if (response.isSuccessful()){
                                        emitter.onSuccess(item);
                                    } else {
                                        Timber.e(response.toString());
                                        emitter.onSuccess(new OrderItem());
                                    }
                            }, error -> {
                                Timber.e(error);
                                emitter.onSuccess(new OrderItem());
                            });
        });
    }

    @Override
    public Single<OrderItem> restoreItemToOrder(OrderItem item){
        return Single.create(emitter -> {
            webLoader.restoreItemToOrder(item)
                    .subscribe(orderItemOkResponse -> {
                        OrderItem restoredItem = orderItemOkResponse.getResponse();
                        if (restoredItem != null){
                            emitter.onSuccess(restoredItem);
                        } else {
                            UserException error = ErrorRepository.makeError(orderItemOkResponse.getError());
                            Timber.e(error);
                            emitter.onSuccess(new OrderItem());
                        }
                    }, error -> {
                        Timber.e(error);
                        emitter.onSuccess(new OrderItem());
                    });
        });
    }
}
