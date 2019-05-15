package ru.fitsme.android.data.repositories.orders;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.favourites.FavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrdersDataSourceFactory extends DataSource.Factory<Integer, OrderItem> {

    private final WebLoader webLoader;
    private final IUserInfoRepository userInfoRepository;

    @Inject
    public OrdersDataSourceFactory(WebLoader webLoader, IUserInfoRepository userInfoRepository) {
        this.webLoader = webLoader;
        this.userInfoRepository = userInfoRepository;
    }

    private MutableLiveData<OrdersRepository> sourceLiveData = new MutableLiveData<>();
    private OrdersRepository latestSource = null;

    @Override
    public DataSource<Integer, OrderItem> create() {
        latestSource = new OrdersRepository(webLoader, userInfoRepository);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public MutableLiveData<OrdersRepository> getSourceLiveData() {
        return sourceLiveData;
    }
}
