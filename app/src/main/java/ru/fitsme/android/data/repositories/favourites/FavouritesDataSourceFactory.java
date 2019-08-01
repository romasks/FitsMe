package ru.fitsme.android.data.repositories.favourites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesDataSourceFactory extends DataSource.Factory<Integer, FavouritesItem> {

    private final WebLoader webLoader;

    private MutableLiveData<FavouritesRepository> sourceLiveData = new MutableLiveData<>();
    private FavouritesRepository latestSource = null;

    @Inject
    public FavouritesDataSourceFactory(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public DataSource<Integer, FavouritesItem> create() {
        latestSource = new FavouritesRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
