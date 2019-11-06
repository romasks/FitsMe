package ru.fitsme.android.domain.interactors.favourites;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IFavouritesInteractor extends BaseInteractor {

    Single<OrderItem> addFavouritesItemToCart(int position);

    Single<FavouritesItem> removeFavouriteItem(Integer position);

    LiveData<PagedList<FavouritesItem>> getPagedListLiveData();

    Single<FavouritesItem> restoreItemToFavourites(Integer position);

    ObservableField<String> getShowMessage();

    boolean itemIsRemoved(int position);
}
