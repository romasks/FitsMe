package ru.fitsme.android.domain.interactors.cart;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.data.repositories.cart.CartDataSourceFactory;
import ru.fitsme.android.domain.boundaries.cart.ICartActionRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

@Singleton
public class CartInteractor implements ICartInteractor {

    private static final int PAGE_SIZE = 10;

    private final ICartActionRepository cartActionRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final CartDataSourceFactory cartDataSourceFactory;

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
    CartInteractor(
            ICartActionRepository cartActionRepository,
            CartDataSourceFactory cartDataSourceFactory,
            @Named("work") Scheduler workThread,
            @Named("main") Scheduler mainThread
    ) {
        this.cartActionRepository = cartActionRepository;
        this.cartDataSourceFactory = cartDataSourceFactory;
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
                new LivePagedListBuilder<>(this.cartDataSourceFactory, config)
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

    @Override
    public Single<List<Order>> getOrders() {
        return Single.create(emitter ->
                cartActionRepository.getOrdersWithoutStatus()
                        .observeOn(mainThread)
                        .subscribe(
                                ordersPage -> emitter.onSuccess(ordersPage.getOrdersList()),
                                emitter::onError
                        ));
    }

    @NonNull
    @Override
    public Single<Integer> removeItemsFromOrder(List<Integer> orderItemsIds) {
        List<Single<Integer>> request = new ArrayList<>();
        for (Integer id : orderItemsIds) {
            request.add(removeItemFromOrderSingle(id));
        }
        return Single.merge(request)
                .observeOn(mainThread)
                .firstOrError();
    }

    @NonNull
    @Override
    public Single<Integer> removeItemFromOrderSingle(Integer orderItemId) {
        return Single.create(emitter ->
                cartActionRepository.removeItemFromOrder(orderItemId)
                        .observeOn(mainThread)
                        .subscribe(emitter::onSuccess, emitter::onError)
        );
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
                return cartActionRepository.removeItemFromOrder(item)
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
                return cartActionRepository.restoreItemToOrder(item)
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
                cartActionRepository.getOrders(status)
                        .observeOn(mainThread)
                        .subscribe(ordersPage -> {
                            checkOutIsLoading.set(false);
                            if (ordersPage.getOrdersList() != null && !ordersPage.getOrdersList().isEmpty()) {
                                Order order = ordersPage.getOrdersList().get(0);
                                emitter.onSuccess(order);
                            } else {
                                emitter.onSuccess(new Order());
                            }
                        }, emitter::onError));
    }

    @NonNull
    @Override
    public Single<Order> makeOrder(OrderRequest orderRequest) {
        return cartActionRepository.makeOrder(orderRequest)
                .observeOn(mainThread)
                .doAfterSuccess(order -> cartDataSourceFactory.invalidate());
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
                    if (oi.getClothe().getSizeInStock() == ClothesItem.SizeInStock.YES) {
                        tmpPrice += oi.getPrice();
                    }
                }
            }
            totalPrice.set(tmpPrice);
        }
    }

    @Override
    public ObservableBoolean getCheckOutIsLoading() {
        return checkOutIsLoading;
    }

    @Override
    public Single<Order> getOrderById(int orderId) {
        return cartActionRepository.getOrderById(orderId)
                .observeOn(mainThread);
    }

    @Override
    public void updateList() {
        cartDataSourceFactory.invalidate();
    }

    public static void setCartMessage(String string) {
        cartMessage.set(string);
    }
}
