package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import timber.log.Timber;

public class FavouritesViewModel extends ViewModel {

    private static final String TAG = "FavouritesViewModel";

    private final IFavouritesInteractor favouritesInteractor;

    private MutableLiveData<List<ClothesItem>> pageLiveData;
    private FavouritesAdapter adapter;
    private Disposable disposable;

    public ObservableInt loading;
    public ObservableInt showEmpty;

    private FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;

        disposable = favouritesInteractor.getSingleFavouritesPage(0)
                .subscribe(favouritesPage -> {
                    pageLiveData.setValue(favouritesPage);
                });
    }

    void init() {
        pageLiveData = new MutableLiveData<>();
        adapter = new FavouritesAdapter(R.layout.item_favourite, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    FavouritesAdapter getAdapter() {
        return adapter;
    }

    void setFavouritesInAdapter(List<ClothesItem> favouritesPage) {
        this.adapter.setFavouritesItems(favouritesPage);
        this.adapter.notifyDataSetChanged();
    }

    MutableLiveData<List<ClothesItem>> getPageLiveData() {
        return pageLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    public ClothesItem getFavouriteItemAt(Integer index) {
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
