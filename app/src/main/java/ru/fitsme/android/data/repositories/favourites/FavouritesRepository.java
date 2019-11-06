package ru.fitsme.android.data.repositories.favourites;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.FavouritesInteractor;
import timber.log.Timber;

public class FavouritesRepository extends PageKeyedDataSource<Integer, FavouritesItem>
        implements IFavouritesRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    FavouritesRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, FavouritesItem> callback) {
        FavouritesInteractor.setFavouriteMessage(App.getInstance().getString(R.string.loading));
        webLoader.getFavouritesClothesPage(1)
                .subscribe(favouritesPageOkResponse -> {
                    FavouritesPage favouritesPage = favouritesPageOkResponse.getResponse();
                    if (favouritesPage != null) {
                        callback.onResult(favouritesPage.getItems(), null, favouritesPage.getNext());
                    } else {
                        UserException error = ErrorRepository.makeError(favouritesPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<FavouritesItem> list = new ArrayList();
                        callback.onResult(list, null, null);
                    }
                    FavouritesInteractor.setFavouriteMessage(null);
                }, error -> {
                    Timber.e(error);
                    FavouritesInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {
        webLoader.getFavouritesClothesPage(params.key)
                .subscribe(favouritesPageOkResponse -> {
                    FavouritesPage favouritesPage = favouritesPageOkResponse.getResponse();
                    if (favouritesPage != null) {
                        callback.onResult(favouritesPage.getItems(), favouritesPage.getPrevious());
                    } else {
                        UserException error = ErrorRepository.makeError(favouritesPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<FavouritesItem> list = new ArrayList();
                        callback.onResult(list, null);
                    }
                }, error -> {
                    Timber.e(error);
                    FavouritesInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {
        webLoader.getFavouritesClothesPage(params.key)
                .subscribe(favouritesPageOkResponse -> {
                    FavouritesPage favouritesPage = favouritesPageOkResponse.getResponse();
                    if (favouritesPage != null) {
                        callback.onResult(favouritesPage.getItems(), favouritesPage.getNext());
                    } else {
                        UserException error = ErrorRepository.makeError(favouritesPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<FavouritesItem> list = new ArrayList();
                        callback.onResult(list, null);
                    }
                }, error -> {
                    Timber.e(error);
                    FavouritesInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }
}
