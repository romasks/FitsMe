package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.hendraanggrian.widget.PaginatedRecyclerView;

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
    private PostPagination postPagination;
    private Integer nextPage;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;

    private FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        pageLiveData = new MutableLiveData<>();
        adapter = new FavouritesAdapter(R.layout.item_favourite, this);
        postPagination = new PostPagination();
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
    }

    FavouritesAdapter getAdapter() {
        return adapter;
    }

    PostPagination getPagination() {
        return postPagination;
    }

    private void loadPage(int index) {
        disposable = favouritesInteractor.getSingleFavouritesPage(index)
                .subscribe(favouritesPage -> {
                    nextPage = favouritesPage.getNext();
                    List list = favouritesPage.getItems();
                    pageLiveData.setValue(list);
                    postPagination.pageReceived();
                });
    }

    void setFavouritesInAdapter(List<FavouritesItem> favouritesPage) {
        if (adapter.getItemCount() == 0) {
            adapter.setFavouritesItems(favouritesPage);
        } else {
            adapter.addFavouritesItems(favouritesPage);
        }
        adapter.notifyDataSetChanged();
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
        return pageLiveData.getValue().get(index);
    }

    public boolean inCart(Integer index) {
        return getFavouriteItemAt(index).isInCart();
    }

    public void addItemToCart(int index) {
        Timber.tag(TAG).d("addItemToCart clicked on position: %d", index);
        // TODO: next sprint
        favouritesInteractor.addFavouritesItemToCart(index, 0)
                .subscribe(() -> {
                    adapter.changeStatus(index, true);
                }, throwable -> {
                });
    }

    void deleteItem(Integer index) {
         favouritesInteractor.deleteFavouriteItem(index)
                 .subscribe(() -> {
                     adapter.clearFavouriteList();
                     if (nextPage == null){
                         loadPage(1);
                     } else {
                         loadPage(nextPage - 1);
                     }
                 });
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

    class PostPagination extends PaginatedRecyclerView.Pagination implements PageReceivedListener {

        @Override
        public int getPageStart() {
            return 1;
        }

        @Override
        public void onPaginate(int index) {
            loadPage(index);
        }

        @Override
        public void pageReceived() {
            if (nextPage == null) notifyPaginationFinished();
            else notifyLoadingCompleted();
        }
    }
}
