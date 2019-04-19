package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;

//import static ru.fitsme.android.utils.Constants.GONE;

public class FavouritesViewModel extends ViewModel {

    private final String TAG = getClass().getName();

    private final IFavouritesInteractor favouritesInteractor;

//    private MutableLiveData<PagedList<FavouritesItem>> pageLiveData;
    private CompositeDisposable disposable;

//    public ObservableBoolean loading;
//    public ObservableBoolean showEmpty;

    private FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        disposable = new CompositeDisposable();
//        loading = new ObservableBoolean(GONE);
//        showEmpty = new ObservableBoolean(GONE);
    }

//    private void loadPage(int index) {
//        disposable.add(
//                favouritesInteractor.getSingleFavouritesPage(index)
//                        .subscribe(favouritesPage -> {
//                            nextPage = favouritesPage.getNext();
//                            pageLiveData.setValue(favouritesPage.getItems());
//                            postPagination.pageReceived();
//                        })
//        );
//    }


    LiveData<PagedList<FavouritesItem>> getPageLiveData() {
        return favouritesInteractor.getPagedListLiveData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

//    public FavouritesItem getFavouriteItemAt(Integer index) {
//        int i = 1;
//        PagedList<FavouritesItem> list = pageLiveData.getValue();
//        FavouritesItem item = list.get(index);
//        return item;
//    }
//
//    public boolean inCart(Integer index) {
//        return pageLiveData.getValue().get(index).isInCart();
//    }

    public void addItemToCart(int index) {
        disposable.add(
                favouritesInteractor.addFavouritesItemToCart(index, 0)
                        .subscribe(() -> {
//                            adapter.changeStatus(index, true);
                        }, throwable -> {
                        })
        );
    }

    void deleteItem(Integer index) {
        disposable.add(
                favouritesInteractor.deleteFavouriteItem(index)
                        .subscribe(() -> {
//                            pageLiveData.
                        })
        );
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
