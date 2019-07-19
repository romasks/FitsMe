package ru.fitsme.android.data.repositories.favourites;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesRepository extends PageKeyedDataSource<Integer, FavouritesItem>
        implements IFavouritesRepository {

    private final WebLoader webLoader;

    @Inject
    FavouritesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, FavouritesItem> callback) {
        try {
            FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(1);
            callback.onResult(favouritesPage.getItems(), null, favouritesPage.getNext());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {
        try {
            FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(params.key);
            callback.onResult(favouritesPage.getItems(), favouritesPage.getPrevious());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {
        try {
            FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(params.key);
            callback.onResult(favouritesPage.getItems(), favouritesPage.getNext());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
