package ru.fitsme.android.data.repositories.favourites;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class FavouritesActionRepository implements IFavouritesActionRepository {
    private final WebLoader webLoader;

    @Inject
    public FavouritesActionRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public void removeItem(@NonNull String token, int id) {

    }

    @Override
    public void restoreItem(@NonNull String token, int id) {

    }

    @Override
    public void addItemToCart(@NonNull String token, int id, int quantity) throws UserException {
        webLoader.addItemToCart(token, id, quantity);
    }
}
