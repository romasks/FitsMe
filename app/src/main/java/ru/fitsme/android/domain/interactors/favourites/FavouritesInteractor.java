package ru.fitsme.android.domain.interactors.favourites;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.HashSet;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.repositories.favourites.FavouritesDataSourceFactory;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

@Singleton
public class FavouritesInteractor implements IFavouritesInteractor {

    private static final int PAGE_SIZE = 10;

    private final IFavouritesActionRepository favouritesActionRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final FavouritesDataSourceFactory favouritesDataSourceFactory;

    private LiveData<PagedList<FavouritesItem>> pagedListLiveData;
    private PagedList.Config config;

    private MutableLiveData<Boolean> favouritesIsEmpty;
    private final static ObservableField<String> showMessage =
            new ObservableField<String>(App.getInstance().getString(R.string.loading));

    private HashSet<Integer> removedFavouriteItemsIdList = new HashSet<>();

    @Inject
    FavouritesInteractor(IFavouritesActionRepository favouritesActionRepository,
                         FavouritesDataSourceFactory favouritesDataSourceFactory,
                         @Named("work") Scheduler workThread,
                         @Named("main") Scheduler mainThread) {
        this.favouritesActionRepository = favouritesActionRepository;
        this.favouritesDataSourceFactory = favouritesDataSourceFactory;
        this.workThread = workThread;
        this.mainThread = mainThread;

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    @Override
    public LiveData<PagedList<FavouritesItem>> getPagedListLiveData() {
        favouritesIsEmpty = new MutableLiveData<>();
        pagedListLiveData =
                new LivePagedListBuilder<>(this.favouritesDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<FavouritesItem>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                favouritesIsEmpty.setValue(true);
                                //showMessage.set(App.getInstance().getString(R.string.no_items_in_favourites));
                            }
                        })
                        .build();

        return Transformations.map(pagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {

                @Override
                public void onChanged(int position, int count) {
                }

                @Override
                public void onInserted(int position, int count) {
                    favouritesIsEmpty.setValue(false);
                }

                @Override
                public void onRemoved(int position, int count) {
                }
            });
            return pagedList;
        });
    }

    @Override
    public Single<OrderItem> addFavouritesItemToCart(int position) {
        PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            FavouritesItem item = pagedList.get(position);
            if (item != null) {
                return favouritesActionRepository.addItemToCart(item)
                        .observeOn(mainThread);
            }
        }
        return Single.just(new OrderItem())
                .observeOn(mainThread);
    }

    @Override
    public Single<FavouritesItem> removeFavouriteItem(Integer position) {
        PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            FavouritesItem item = pagedList.get(position);
            if (item != null) {
                return favouritesActionRepository.removeItem(item)
                        .map(removedItem -> {
                            removedFavouriteItemsIdList.add(removedItem.getId());
                            return removedItem;
                        });
            }
        }
        return Single.just(new FavouritesItem());
    }

    @Override
    public Single<FavouritesItem> restoreItemToFavourites(Integer position) {
        PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            FavouritesItem item = pagedList.get(position);
            if (item != null) {
                return favouritesActionRepository.restoreItem(item)
                        .map(restoredItem -> {
                            removedFavouriteItemsIdList.remove(restoredItem.getId());
                            return restoredItem;
                        });
            }
        }
        return Single.just(new FavouritesItem());
    }

    @Override
    public ObservableField<String> getShowMessage() {
        return showMessage;
    }

    @Override
    public boolean itemIsRemoved(int position) {
        PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            FavouritesItem item = pagedList.get(position);
            if (item != null) {
                return removedFavouriteItemsIdList.contains(item.getId());
            }
        }
        return false;
    }

    public static void setFavouriteMessage(String string) {
        showMessage.set(string);
    }

    @Override
    public LiveData<Boolean> getFavouritesIsEmpty() {
        return favouritesIsEmpty;
    }
}
