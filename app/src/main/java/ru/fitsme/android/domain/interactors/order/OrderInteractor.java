package ru.fitsme.android.domain.interactors.order;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.order.IOrderRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.OrderItem;

@Singleton
public class OrderInteractor implements IOrderInteractor {

    private final String TAG = getClass().getName();

    private final IOrderRepository orderRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    OrderInteractor(IOrderRepository orderRepository,
                    IUserInfoRepository userInfoRepository,
                    @Named("work") Scheduler workThread,
                    @Named("main") Scheduler mainThread) {
        this.orderRepository = orderRepository;
        this.userInfoRepository = userInfoRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Single<OrderItem> getSingleOrderItem(int index) {
        return Single.create((SingleOnSubscribe<OrderItem>) emitter ->
                emitter.onSuccess(getOrderItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private OrderItem getOrderItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return orderRepository.getOrderItem(token, index);
    }

    @NonNull
    @Override
    public Completable makeOrder(OrderItem order) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            orderRepository.makeOrder(token, order);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
