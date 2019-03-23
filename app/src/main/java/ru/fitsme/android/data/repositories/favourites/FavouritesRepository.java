package ru.fitsme.android.data.repositories.favourites;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesRepository implements IFavouritesRepository {
    private static final int PAGE_SIZE = 10;

    private final SparseArray<FavouritesPage> favouritesPageSparseArray = new SparseArray<>();
    private final WebLoader webLoader;

    @Inject
    public FavouritesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
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
        return webLoader.getFavouritesClothesPage(token, page);
    }
}
