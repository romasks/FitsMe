package ru.fitsme.android.domain.interactors.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import ru.fitsme.android.data.repositories.favourites.FavouritesDataSourceFactory;
import ru.fitsme.android.data.repositories.favourites.FavouritesRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

@Singleton
public class FavouritesInteractor implements IFavouritesInteractor {

    private static final int PAGE_SIZE = 10;

    private final IFavouritesActionRepository favouritesActionRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final FavouritesDataSourceFactory favouritesDataSourceFactory;

    private LiveData<PagedList<FavouritesItem>> pagedListLiveData;
    private PagedList.Config config;

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
        return pagedListLiveData =
                new LivePagedListBuilder<>(this.favouritesDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build();
    }

    private void invalidateDataSource() {
        FavouritesRepository repository = favouritesDataSourceFactory.getSourceLiveData().getValue();
        if (repository != null) {
            Objects.requireNonNull(repository).invalidate();
        }
    }

    @NonNull
    @Override
    public Completable addFavouritesItemToCart(int position) {
        return Completable.create(emitter -> {
            PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position) {
                FavouritesItem item = pagedList.get(position);
                if (item != null) {
                    int clotheItemId = item.getItem().getId();
                    favouritesActionRepository.addItemToCart(clotheItemId);
                    invalidateDataSource();
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NotNull
    @Override
    public Completable deleteFavouriteItem(Integer position) {
        return Completable.create(emitter -> {
            PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position) {
                FavouritesItem item = pagedList.get(position);
                if (item != null) {
                    favouritesActionRepository.removeItem(item.getId());
                    invalidateDataSource();
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
