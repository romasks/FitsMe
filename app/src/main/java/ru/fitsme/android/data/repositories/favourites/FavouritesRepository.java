package ru.fitsme.android.data.repositories.favourites;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesRepository extends PageKeyedDataSource<Integer, FavouritesItem>
        implements IFavouritesRepository {

    private static final int PAGE_SIZE = 10;

    private final SparseArray<FavouritesPage> favouritesPageSparseArray = new SparseArray<>();
    private final WebLoader webLoader;
    private final IUserInfoRepository userInfoRepository;

    @Inject
    FavouritesRepository(WebLoader webLoader, IUserInfoRepository userInfoRepository) {
        this.webLoader = webLoader;
        this.userInfoRepository = userInfoRepository;
    }

    @NonNull
    @Override
    public FavouritesItem getFavouritesItem(@NonNull String token, int index) throws AppException {
        //ToDo wrong way, need to rework
        int pageIndex = index / PAGE_SIZE;
        int itemIndex = index % PAGE_SIZE;
        FavouritesPage favouritesPage = favouritesPageSparseArray.get(pageIndex);
        if (favouritesPage == null) {
            favouritesPage = getFavouritesPage(token, pageIndex);
            favouritesPageSparseArray.put(pageIndex, favouritesPage);
        }
        return favouritesPage.getItems().get(itemIndex);
    }

    @NonNull
    @Override
    public FavouritesPage getFavouritesPage(@NonNull String token, int page) throws AppException {
        FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(token, page);
        favouritesPageSparseArray.put(page - 1, favouritesPage);
        return favouritesPage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, FavouritesItem> callback) {
        String token = null;
        try {
            token = userInfoRepository.getAuthInfo().getToken();
            FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(token, 1);
            callback.onResult(favouritesPage.getItems(), null, favouritesPage.getNext());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavouritesItem> callback) {
        String token = null;
        try {
            token = userInfoRepository.getAuthInfo().getToken();
            FavouritesPage favouritesPage = webLoader.getFavouritesClothesPage(token, params.key);
            callback.onResult(favouritesPage.getItems(), favouritesPage.getNext());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
