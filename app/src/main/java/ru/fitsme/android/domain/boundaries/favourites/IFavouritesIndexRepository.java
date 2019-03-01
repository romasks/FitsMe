package ru.fitsme.android.domain.boundaries.favourites;

public interface IFavouritesIndexRepository {

    int getLastFavouritesItemIndex();

    void setLastFavouritesItemIndex(int index);
}
