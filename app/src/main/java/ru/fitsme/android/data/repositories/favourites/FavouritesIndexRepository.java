package ru.fitsme.android.data.repositories.favourites;

import ru.fitsme.android.domain.boundaries.favourites.IFavouritesIndexRepository;

public class FavouritesIndexRepository implements IFavouritesIndexRepository {
    @Override
    public int getLastFavouritesItemIndex() {
        return 0;
    }

    @Override
    public void setLastFavouritesItemIndex(int index) {

    }
}
