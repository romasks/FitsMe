package ru.fitsme.android.data.repositories.orders;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrdersDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {

    private final WebLoaderNetworkChecker webLoader;

    private MutableLiveData<OrdersRepository> sourceLiveData = new MutableLiveData<>();
    private OrdersRepository latestSource = null;

    @Inject
    public OrdersDataSourceFactory(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public DataSource<Integer, OrderItem> create() {
        latestSource = new OrdersRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
