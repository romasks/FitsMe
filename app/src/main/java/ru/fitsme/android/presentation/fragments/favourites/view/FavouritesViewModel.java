package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;

public class FavouritesViewModel extends ViewModel {

    private final String TAG = getClass().getName();

    private final IFavouritesInteractor favouritesInteractor;

    private MutableLiveData<List<FavouritesItem>> pageLiveData;
    private FavouritesAdapter adapter;
    private Disposable disposable;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;

    private FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
        Timber.tag(TAG).d("constructor");
        loadData();
    }

    void init() {
        Timber.tag(TAG).d("init");
        pageLiveData = new MutableLiveData<>();
        adapter = new FavouritesAdapter(R.layout.item_favourite, this);
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
    }

    FavouritesAdapter getAdapter() {
        return adapter;
    }

    private void loadData() {
        disposable = favouritesInteractor.getSingleFavouritesPage(0)
                .subscribe(favouritesPage -> {
                    pageLiveData.setValue(favouritesPage);
                });
    }

    void setFavouritesInAdapter(List<FavouritesItem> favouritesPage) {
        this.adapter.setFavouritesItems(favouritesPage);
        this.adapter.notifyDataSetChanged();
    }

    LiveData<List<FavouritesItem>> getPageLiveData() {
        return pageLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    public FavouritesItem getFavouriteItemAt(Integer index) {
        if (pageLiveData.getValue() != null &&
                index != null &&
                pageLiveData.getValue().size() > index) {
            return pageLiveData.getValue().get(index);
        }
        return null;
    }

    public void putItemToBasket(Integer index) {
        Timber.tag("FavouritesViewModel").d("putItemToBasket clicked on position: %d", index);
        // TODO: next sprint
    }

    static public class Factory implements ViewModelProvider.Factory {
        private final IFavouritesInteractor favouritesInteractor;

        public Factory(@NotNull IFavouritesInteractor favouritesInteractor) {
            this.favouritesInteractor = favouritesInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavouritesViewModel(favouritesInteractor);
        }
    }
}
