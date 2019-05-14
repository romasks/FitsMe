package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import com.hendraanggrian.widget.PaginatedRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.presentation.common.adapter.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

import static ru.fitsme.android.utils.Constants.GONE;

public class FavouritesViewModel extends BaseViewModel {

    private final IFavouritesInteractor favouritesInteractor;

    private MutableLiveData<List<FavouritesItem>> pageLiveData;
    private FavouritesAdapter adapter;
    private CompositeDisposable disposable;
    private PostPagination postPagination;
    private Integer nextPage;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;

    public FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        pageLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        postPagination = new PostPagination();
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
    }

    void setAdapter(int layoutId) {
        adapter = new FavouritesAdapter(layoutId, this);
    }

    FavouritesAdapter getAdapter() {
        return adapter;
    }

    PostPagination getPagination() {
        return postPagination;
    }

    private void loadPage(int index) {
        disposable.add(
                favouritesInteractor.getSingleFavouritesPage(index)
                        .subscribe(favouritesPage -> {
                            nextPage = favouritesPage.getNext();
                            pageLiveData.setValue(favouritesPage.getItems());
                            postPagination.pageReceived();
                        })
        );
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

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    public FavouritesItem getFavouriteItemAt(Integer index) {
        return adapter.getFavouriteItemAt(index);
    }

    public boolean inCart(Integer index) {
        return getFavouriteItemAt(index).isInCart();
    }

    public void addItemToCart(int index) {
        disposable.add(
                favouritesInteractor.addFavouritesItemToCart(index, 0)
                        .subscribe(() -> {
                            adapter.changeStatus(index, true);
                        }, throwable -> {
                        })
        );
    }

    void deleteItem(Integer index) {
        disposable.add(
                favouritesInteractor.deleteFavouriteItem(index)
                        .subscribe(() -> {
                            pageLiveData.getValue().remove(getFavouriteItemAt(index));
                            adapter.removeItemAt(index);
                            adapter.notifyItemRemoved(index);
                        })
        );
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
