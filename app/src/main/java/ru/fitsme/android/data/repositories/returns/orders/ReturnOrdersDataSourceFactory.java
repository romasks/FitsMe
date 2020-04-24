package ru.fitsme.android.data.repositories.returns.orders;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.entities.order.Order;

public class ReturnOrdersDataSourceFactory extends DataSource.Factory<Integer, Order> {

    private final WebLoaderNetworkChecker webLoader;

    private MutableLiveData<ReturnOrdersRepository> sourceLiveData = new MutableLiveData<>();
    private ReturnOrdersRepository latestSource = null;

    @Inject
    ReturnOrdersDataSourceFactory(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NotNull
    @Override
    public DataSource<Integer, Order> create() {
        latestSource = new ReturnOrdersRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
