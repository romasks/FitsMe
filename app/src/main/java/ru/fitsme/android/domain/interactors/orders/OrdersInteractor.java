package ru.fitsme.android.domain.interactors.orders;

import android.util.SparseIntArray;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.HashSet;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.data.repositories.orders.OrdersDataSourceFactory;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
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

    private final ObservableBoolean checkOutIsLoading = new ObservableBoolean(true);
    private MutableLiveData<Boolean> cartIsEmpty;
    private final ObservableInt totalPrice = new ObservableInt(0);
    private final static ObservableField<String> cartMessage =
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
        cartIsEmpty = new MutableLiveData<>();
        totalPrice.set(0);
        restoredOrderClotheItemsIdList = new SparseIntArray();
        removedClotheIdList = new HashSet<>();
        pagedListLiveData =
                new LivePagedListBuilder<>(this.ordersDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<OrderItem>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                cartIsEmpty.setValue(true);
                            }
                        })
                        .build();

        return Transformations.map(pagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {

                @Override
                public void onChanged(int position, int count) {
                    updateTotalPrice();
                }

                @Override
                public void onInserted(int position, int count) {
                    cartIsEmpty.setValue(false);
                    updateTotalPrice();
                }

                @Override
                public void onRemoved(int position, int count) {
                    updateTotalPrice();
                }
            });
            return pagedList;
        });
    }

    @NonNull
    @Override
    public Single<OrderItem> removeItemFromOrder(int position) {
        PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            OrderItem item = pagedList.get(position);
            if (item != null) {
                int clotheId = item.getClothe().getId();
                if (restoredOrderClotheItemsIdList.get(clotheId) != 0) {
                    int restoredOrderItemId = restoredOrderClotheItemsIdList.get(clotheId);
                    item.setId(restoredOrderItemId);
                }
                return ordersActionRepository.removeItemFromOrder(item)
                        .map(removedOrderItem -> {
                            if (removedOrderItem.getId() != 0) {
                                updateTotalPrice();
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
    public Single<Order> getSingleOrder(OrderStatus status) {
        checkOutIsLoading.set(true);
        return Single.create(emitter ->
                ordersActionRepository.getOrders(status)
                        .observeOn(mainThread)
                        .subscribe(ordersPage -> {
                            checkOutIsLoading.set(false);
                            if (ordersPage.getOrdersList() != null) {
                                Order order = ordersPage.getOrdersList().get(0);
                                emitter.onSuccess(order);
                            } else {
                                emitter.onSuccess(new Order());
                            }
                        }, emitter::onError));
    }

    @NonNull
    @Override
    public Single<Order> makeOrder(OrderModel orderModel) {
        return ordersActionRepository.makeOrder(
                orderModel.getOrderId(),
                orderModel.getPhoneNumber().replaceAll("[^\\d]", ""),
                orderModel.getStreet(),
                orderModel.getHouseNumber(),
                orderModel.getApartment(),
                OrderStatus.FM)
                .observeOn(mainThread);
    }

    @Override
    public LiveData<Boolean> getCartIsEmpty() {
        return cartIsEmpty;
    }

    @Override
    public ObservableField<String> getMessage() {
        return cartMessage;
    }

    @Override
    public boolean itemIsRemoved(int position) {
        PagedList<OrderItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            OrderItem item = pagedList.get(position);
            if (item != null) {
                updateTotalPrice();
                return removedClotheIdList.contains(item.getClothe().getId());
            }
        }
        return false;
    }

    @Override
    public ObservableInt getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void updateTotalPrice() {
        int tmpPrice = 0;
        if (pagedListLiveData.getValue() != null) {
            for (OrderItem oi : pagedListLiveData.getValue()) {
                int clotheId = oi.getClothe().getId();
                if (!removedClotheIdList.contains(clotheId)) {
                    tmpPrice += oi.getPrice();
                }
            }
            totalPrice.set(tmpPrice);
        }
    }

    @Override
    public ObservableBoolean getCheckOutIsLoading() {
        return checkOutIsLoading;
    }

    public static void setCartMessage(String string) {
        cartMessage.set(string);
    }
}
