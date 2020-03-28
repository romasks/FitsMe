package ru.fitsme.android.data.repositories.orders;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrdersDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {

    private final WebLoaderNetworkChecker webLoader;

    private MutableLiveData<OrdersRepository> sourceLiveData = new MutableLiveData<>();
    private OrdersRepository latestSource = null;

    @Inject
    OrdersDataSourceFactory(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NotNull
    @Override
    public DataSource<Integer, OrderItem> create() {
        latestSource = new OrdersRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public void invalidate() {
        latestSource.invalidate();
    }
}
