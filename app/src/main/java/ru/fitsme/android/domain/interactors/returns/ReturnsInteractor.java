package ru.fitsme.android.domain.interactors.returns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.data.repositories.returns.ReturnsActionRepository;
import ru.fitsme.android.data.repositories.returns.ReturnsDataSourceFactory;
import ru.fitsme.android.data.repositories.returns.orders.ReturnOrdersDataSourceFactory;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;

@Singleton
public class ReturnsInteractor implements IReturnsInteractor {

    private static final int PAGE_SIZE = 10;

    private final ReturnsDataSourceFactory returnsDataSourceFactory;
    private final ReturnOrdersDataSourceFactory returnOrdersDataSourceFactory;
    private final ReturnsActionRepository returnsActionRepository;
    private final Scheduler mainThread;

    private LiveData<PagedList<ReturnsOrder>> returnsPagedListLiveData;
    private LiveData<PagedList<Order>> returnOrdersPagedListLiveData;
    private PagedList.Config config;
    private MutableLiveData<Boolean> returnsIsEmpty;
    private MutableLiveData<Boolean> returnOrdersIsEmpty;

    private final static ObservableField<String> showMessage =
            new ObservableField<String>(App.getInstance().getString(R.string.loading));

    @Inject
    ReturnsInteractor(
            ReturnsDataSourceFactory returnsDataSourceFactory,
            ReturnOrdersDataSourceFactory returnOrdersDataSourceFactory,
            ReturnsActionRepository returnsActionRepository,
            @Named("main") Scheduler mainThread
    ) {
        this.returnsDataSourceFactory = returnsDataSourceFactory;
        this.returnOrdersDataSourceFactory = returnOrdersDataSourceFactory;
        this.returnsActionRepository = returnsActionRepository;
        this.mainThread = mainThread;

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    @Override
    public LiveData<PagedList<ReturnsOrder>> getReturnsPagedListLiveData() {
        returnsIsEmpty = new MutableLiveData<>();
        returnsPagedListLiveData =
                new LivePagedListBuilder<>(this.returnsDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<ReturnsOrder>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                returnsIsEmpty.setValue(true);
                            }
                        })
                        .build();

        return Transformations.map(returnsPagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {

                @Override
                public void onChanged(int position, int count) {
                    //updateTotalPrice();
                }

                @Override
                public void onInserted(int position, int count) {
                    returnsIsEmpty.setValue(false);
                    //updateTotalPrice();
                }

                @Override
                public void onRemoved(int position, int count) {
                    //updateTotalPrice();
                }
            });
            return pagedList;
        });
    }

    @Override
    public LiveData<Boolean> getReturnsIsEmpty() {
        return returnsIsEmpty;
    }

    @Override
    public LiveData<PagedList<Order>> getReturnOrdersPagedListLiveData() {
        returnOrdersIsEmpty = new MutableLiveData<>();
        returnOrdersPagedListLiveData =
                new LivePagedListBuilder<>(this.returnOrdersDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<Order>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                returnOrdersIsEmpty.setValue(true);
                            }
                        })
                        .build();

        return Transformations.map(returnOrdersPagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {
                @Override
                public void onChanged(int position, int count) {
                }

                @Override
                public void onInserted(int position, int count) {
                    returnOrdersIsEmpty.setValue(false);
                }

                @Override
                public void onRemoved(int position, int count) {
                }
            });
            return pagedList;
        });
    }

    @Override
    public LiveData<Boolean> getReturnOrdersIsEmpty() {
        return returnOrdersIsEmpty;
    }

    @Override
    public ObservableField<String> getShowMessage() {
        return showMessage;
    }

    @Override
    public boolean itemIsInCart(int position) {
        PagedList<ReturnsOrder> pagedList = returnsPagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            ReturnsOrder item = pagedList.get(position);
            if (item != null) {
                return item.getStatus().equals("FM");
            }
        }
        return false;
    }

    @Override
    public Single<ReturnsOrderItem> addItemsToReturn(List<ReturnsItemRequest> requestsList) {
        List<Single<ReturnsOrderItem>> request = new ArrayList<>();
        for (ReturnsItemRequest item : requestsList) {
            request.add(addItemToReturn(item));
        }
        return Single.merge(request)
                .observeOn(mainThread)
                .firstOrError();
    }

    @Override
    public Single<ReturnsOrderItem> addItemToReturn(ReturnsItemRequest request) {
        return returnsActionRepository.addItemToReturn(request)
                .observeOn(mainThread);
    }

    @Override
    public Single<ReturnsOrderItem> changeReturnsPayment(ReturnsPaymentRequest request) {
        return returnsActionRepository.changeReturnsPayment(request)
                .observeOn(mainThread);
    }

    @Override
    public Single<ReturnsOrder> getReturnById(int returnId) {
        return returnsActionRepository.getReturnById(returnId)
                .observeOn(mainThread);
    }

    public static void setShowMessage(String string) {
        showMessage.set(string);
    }
}
