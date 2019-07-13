package ru.fitsme.android.data.repositories.favourites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.boundaries.signinup.IAuthRepository;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesDataSourceFactory extends DataSource.Factory<Integer, FavouritesItem> {

    private final WebLoader webLoader;
    private final IAuthRepository authRepository;
//    private final IUserInfoRepository userInfoRepository;

    @Inject
    public FavouritesDataSourceFactory(WebLoader webLoader, IAuthRepository authRepository) {
        this.webLoader = webLoader;
        this.authRepository = authRepository;
    }

    private MutableLiveData<FavouritesRepository> sourceLiveData = new MutableLiveData<>();
    private FavouritesRepository latestSource = null;

    @Override
    public DataSource<Integer, FavouritesItem> create() {
        latestSource = new FavouritesRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public MutableLiveData<FavouritesRepository> getSourceLiveData() {
        return sourceLiveData;
    }
}
