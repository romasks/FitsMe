package ru.fitsme.android.data.repositories.orders;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrdersDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {

    private final WebLoader webLoader;

    private MutableLiveData<OrdersRepository> sourceLiveData = new MutableLiveData<>();
    private OrdersRepository latestSource = null;

    @Inject
    public OrdersDataSourceFactory(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public DataSource<Integer, OrderItem> create() {
        latestSource = new OrdersRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
