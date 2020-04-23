package ru.fitsme.android.domain.interactors.orders;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.repositories.orders.OrdersDataSourceFactory;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.entities.order.Order;

@Singleton
public class OrdersInteractor implements IOrdersInteractor {

    private static final int PAGE_SIZE = 10;

    private final IOrdersActionRepository ordersActionRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final OrdersDataSourceFactory ordersDataSourceFactory;

    private LiveData<PagedList<Order>> pagedListLiveData;
    private PagedList.Config config;

    private MutableLiveData<Boolean> ordersListIsEmpty;
    private final static ObservableField<String> message = new ObservableField<>(App.getInstance().getString(R.string.loading));

    @Inject
    OrdersInteractor(
            IOrdersActionRepository ordersActionRepository,
            OrdersDataSourceFactory ordersDataSourceFactory,
            @Named("work") Scheduler workThread,
            @Named("main") Scheduler mainThread
    ) {
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
    public LiveData<PagedList<Order>> getPagedListLiveData() {
        ordersListIsEmpty = new MutableLiveData<>();
        pagedListLiveData =
                new LivePagedListBuilder<>(this.ordersDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<Order>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                ordersListIsEmpty.setValue(true);
                            }
                        })
                        .build();

        return Transformations.map(pagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {

                @Override
                public void onChanged(int position, int count) {
                }

                @Override
                public void onInserted(int position, int count) {
                    ordersListIsEmpty.setValue(false);
                }

                @Override
                public void onRemoved(int position, int count) {
                }
            });
            return pagedList;
        });
    }

    @Override
    public Single<List<Order>> getOrders() {
        return Single.create(emitter ->
                ordersActionRepository.getOrdersWithoutStatus()
                        .observeOn(mainThread)
                        .subscribe(
                                ordersPage -> emitter.onSuccess(ordersPage.getOrdersList()),
                                emitter::onError
                        ));
    }

    @Override
    public Single<List<Order>> getReturnOrders() {
        return Single.create(emitter ->
                ordersActionRepository.getReturnsOrders()
                        .observeOn(mainThread)
                        .subscribe(
                                ordersPage -> emitter.onSuccess(ordersPage.getOrdersList()),
                                emitter::onError
                        ));
    }

    @Override
    public LiveData<Boolean> getOrdersListIsEmpty() {
        return ordersListIsEmpty;
    }


    @Override
    public ObservableField<String> getMessage() {
        return message;
    }

    @Override
    public Single<Order> getOrderById(int orderId) {
        return ordersActionRepository.getOrderById(orderId)
                .observeOn(mainThread);
    }

    @Override
    public void updateList() {
        ordersDataSourceFactory.invalidate();
    }

    public static void setMessage(String string) {
        message.set(string);
    }
}
