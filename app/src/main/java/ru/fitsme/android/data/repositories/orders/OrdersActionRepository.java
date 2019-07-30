package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
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
    public OrdersPage getOrders(OrderStatus status) throws AppException {
        return webLoader.getOrders(status);
    }

    @Override
    public void makeOrder(
            long orderId, String phoneNumber, String street, String houseNumber,
            String apartment, OrderStatus orderStatus
    ) throws AppException {

        webLoader.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus);
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
