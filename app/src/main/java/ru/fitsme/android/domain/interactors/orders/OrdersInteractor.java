package ru.fitsme.android.domain.interactors.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.data.entities.exceptions.AppException;
import ru.fitsme.android.data.entities.response.orders.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.OrderDTO;
import ru.fitsme.android.utils.OrderStatus;

@Singleton
public class OrdersInteractor implements IOrdersInteractor {

    private final String TAG = getClass().getName();

    private final IOrdersRepository orderRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    OrdersInteractor(IOrdersRepository orderRepository,
                     @Named("work") Scheduler workThread,
                     @Named("main") Scheduler mainThread) {
        this.orderRepository = orderRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Single<OrderDTO> getSingleOrder(int page) {
        return Single.create((SingleOnSubscribe<OrderDTO>) emitter -> {
            OrderDTO orderDTO = new OrderDTO(getOrders(page).getOrdersList().get(1));
            emitter.onSuccess(orderDTO);
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private OrdersPage getOrders(int page) throws AppException {
        return orderRepository.getOrders(page);
    }

    @NonNull
    @Override
    public Completable removeItemFromOrder(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable restoreItemToOrder(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable makeOrder(
            String phoneNumber, String street, String houseNumber, String apartment, OrderStatus orderStatus
    ) {
        return Completable.create(emitter -> {
            orderRepository.makeOrder(1, phoneNumber, street, houseNumber, apartment, orderStatus);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
