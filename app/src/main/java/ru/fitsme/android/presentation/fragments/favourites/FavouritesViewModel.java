package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import com.hendraanggrian.widget.PaginatedRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;

public class FavouritesViewModel extends BaseViewModel {

    private final IFavouritesInteractor favouritesInteractor;

    private MutableLiveData<List<FavouritesItem>> pageLiveData;
    private FavouritesAdapter adapter;
    private PostPagination postPagination;
    private Integer nextPage;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;

    public FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        pageLiveData = new MutableLiveData<>();
        postPagination = new PostPagination();
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
    }

    void setAdapter(int layoutId) {
        adapter = new FavouritesAdapter(layoutId, this);
    }

    public FavouritesAdapter getAdapter() {
        return adapter;
    }

    PostPagination getPagination() {
        return postPagination;
    }

    void setFavouritesInAdapter(List<FavouritesItem> favouritesItems) {
        if (adapter.getItemCount() == 0) {
            adapter.setFavouritesItems(favouritesItems);
        } else {
            adapter.addFavouritesItems(favouritesItems);
        }
        adapter.notifyDataSetChanged();
    }

    LiveData<List<FavouritesItem>> getPageLiveData() {
        return pageLiveData;
    }

    private FavouritesItem getFavouriteItemAt(Integer index) {
        return adapter.getFavouriteItemAt(index);
    }

    private void loadPage(int index) {
        addDisposable(favouritesInteractor.getSingleFavouritesPage(index)
                .subscribe(this::onFavouritePage, this::onError));
    }

    public void addItemToCart(int index) {
        addDisposable(favouritesInteractor.addFavouritesItemToCart(index, 0)
                .subscribe(() -> adapter.changeStatus(index, true), this::onError));
    }

    void deleteItem(int index) {
        addDisposable(favouritesInteractor.deleteFavouriteItem(index)
                .subscribe(() -> {
                    adapter.removeItemAt(index);
                    if (adapter.getItemCount() == 0) {
                        pageLiveData.setValue(pageLiveData.getValue());
                    }
                }, this::onError));
    }

    private void onFavouritePage(@NotNull FavouritesPage favouritesPage) {
        nextPage = favouritesPage.getNext();
        pageLiveData.setValue(favouritesPage.getItems());
        postPagination.pageReceived();
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
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
