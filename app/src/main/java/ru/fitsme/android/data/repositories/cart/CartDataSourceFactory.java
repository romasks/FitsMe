package ru.fitsme.android.data.repositories.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class CartDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {

    private final WebLoaderNetworkChecker webLoader;

    private MutableLiveData<CartRepository> sourceLiveData = new MutableLiveData<>();
    private CartRepository latestSource = null;

    @Inject
    CartDataSourceFactory(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NotNull
    @Override
    public DataSource<Integer, OrderItem> create() {
        latestSource = new CartRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public void invalidate() {
        latestSource.invalidate();
    }
}
