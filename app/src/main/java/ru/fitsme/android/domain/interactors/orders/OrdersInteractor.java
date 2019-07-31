package ru.fitsme.android.domain.interactors.orders;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import java.util.HashSet;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.repositories.orders.OrdersDataSourceFactory;
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

    private final ObservableBoolean cartIsEmpty = new ObservableBoolean(false);
    private final static ObservableField<String> message =
            new ObservableField<String>(App.getInstance().getString(R.string.loading));

    // Используется для хранения удаленных элементов, чтобы была возможность их восстановить.
    // key - ClotheId, value - OrderItemId. После восстановления удаленного элемента
    // меняется OrderItemId, а в PagedList остается старый, поэтому приходится хранить
    // новое значение тоже
    private SparseIntArray restoredOrderClotheItemsIdList = new SparseIntArray();
    private HashSet<Integer> removedClotheIdList = new HashSet<>();

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
    }

    @Override
    public LiveData<PagedList<OrderItem>> getPagedListLiveData() {
        cartIsEmpty.set(false);
        restoredOrderClotheItemsIdList = new SparseIntArray();
        removedClotheIdList = new HashSet<>();
        return pagedListLiveData =
                new LivePagedListBuilder<>(this.ordersDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<OrderItem>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                cartIsEmpty.set(true);
                            }
                        })
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

    private OrdersPage getOrders(OrderStatus status) throws AppException {
        return ordersActionRepository.getOrders(status);
    }
//    @NonNull
//    @Override
//    public Single<Order> getCurrentOrderInCart() {
//        return Single.create((SingleOnSubscribe<Order>) emitter ->{
//            emitter.onSuccess(new Order());
//        })
//                .subscribeOn(workThread)

//                .observeOn(mainThread);

//    }

    @NonNull
    @Override
    public Single<OrderItem> removeItemFromOrder(int position) {
            PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position) {
                OrderItem item = pagedList.get(position);
                if (item != null) {
                    int clotheId = item.getClothe().getId();
                    if (restoredOrderClotheItemsIdList.get(clotheId) != 0){
                        int restoredOrderItemId = restoredOrderClotheItemsIdList.get(clotheId);
                        item.setId(restoredOrderItemId);
                    }
                    return ordersActionRepository.removeItemFromOrder(item)
                            .map(removedOrderItem -> {
                                if (removedOrderItem.getId() != 0){
                                    removedClotheIdList.add(removedOrderItem.getClothe().getId());
                                }
                                return removedOrderItem;
                            });
                }
            }
            return Single.just(new OrderItem());
    }

    @NonNull
    @Override
    public Single<OrderItem> restoreItemToOrder(int position) {
        PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            OrderItem item = pagedList.get(position);
            if (item != null) {
                return ordersActionRepository.restoreItemToOrder(item)
                        .map(restoredOrderItem -> {
                            restoredOrderClotheItemsIdList.put(
                                    restoredOrderItem.getClothe().getId(), restoredOrderItem.getId());
                            removedClotheIdList.remove(restoredOrderItem.getClothe().getId());
                            return restoredOrderItem;
                        });
            }
        }
        return Single.just(new OrderItem());
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
    public ObservableBoolean getCartIsEmpty() {
        return cartIsEmpty;
    }

    @Override
    public ObservableField<String> getMessage(){
        return message;
    }

    @Override
    public boolean itemIsRemoved(int position) {
        PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            OrderItem item = pagedList.get(position);
            if (item != null) {
                return removedClotheIdList.contains(item.getClothe().getId());
            }
        }
        return false;
    }

    public static void setMessage(String string){
        message.set(string);
    }
}
