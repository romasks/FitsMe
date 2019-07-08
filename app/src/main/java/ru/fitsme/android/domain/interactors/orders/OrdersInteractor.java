package ru.fitsme.android.domain.interactors.orders;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.data.repositories.orders.OrdersDataSourceFactory;
import ru.fitsme.android.data.repositories.orders.OrdersRepository;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

@Singleton
public class OrdersInteractor implements IOrdersInteractor {

    private static final int PAGE_SIZE = 10;

    private final IOrdersActionRepository ordersActionRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final OrdersDataSourceFactory ordersDataSourceFactory;

    private LiveData<PagedList<OrderItem>> pagedListLiveData;
    private PagedList.Config config;

    @Inject
    OrdersInteractor(IOrdersActionRepository ordersActionRepository,
                     OrdersDataSourceFactory ordersDataSourceFactory,
                     @Named("work") Scheduler workThread,
                     @Named("main") Scheduler mainThread) {
        this.ordersActionRepository = ordersActionRepository;
        this.ordersDataSourceFactory = ordersDataSourceFactory;
        this.workThread = workThread;
        this.mainThread = mainThread;

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        pagedListLiveData =
                new LivePagedListBuilder<>(this.ordersDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build();
    }

    @NonNull
    @Override
    public Single<Order> getSingleOrder(OrderStatus status) {
        return Single.create((SingleOnSubscribe<Order>) emitter -> {
            emitter.onSuccess(getOrders(status).getOrdersList().get(0));
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<Order> getCurrentOrderInCart() {
        return Single.create((SingleOnSubscribe<Order>) emitter ->{
            emitter.onSuccess(new Order());
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private OrdersPage getOrders(OrderStatus status) throws AppException {
        return ordersActionRepository.getOrders(status);
    }

    @NonNull
    @Override
    public Completable removeItemFromOrder(int position) {
        return Completable.create(emitter -> {
            PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position) {
                OrderItem item = pagedList.get(position);
                if (item != null) {
                    int orderItemId = item.getId();
                    ordersActionRepository.removeItemFromOrder(orderItemId);
                    invalidateDataSource();
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Completable restoreItemToOrder(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable makeOrder(OrderModel orderModel) {
        return Completable.create(emitter -> {
            ordersActionRepository.makeOrder(
                    orderModel.getOrderId(),
                    orderModel.getPhoneNumber().replaceAll("[^\\d]", ""),
                    orderModel.getStreet(),
                    orderModel.getHouseNumber(),
                    orderModel.getApartment(),
                    OrderStatus.FM
            );
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @Override
    public LiveData<PagedList<OrderItem>> getPagedListLiveData() {
        invalidateDataSource();
        return pagedListLiveData;
    }

    private void invalidateDataSource(){
        OrdersRepository repository = ordersDataSourceFactory.getSourceLiveData().getValue();
        if (repository != null) {
            Objects.requireNonNull(repository).invalidate();
        }
    }
}
