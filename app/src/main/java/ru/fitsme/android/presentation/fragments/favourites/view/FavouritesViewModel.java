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
        Timber.tag(TAG).d("constructor");
//        loadPage(0);
    }

    void init() {
        Timber.tag(TAG).d("init");
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
        Timber.tag(TAG).d("loadPage");
        Timber.tag(TAG).d("index: %s", index);
        disposable = favouritesInteractor.getSingleFavouritesPage(index)
                .subscribe(favouritesPage -> {
                    pageLiveData.setValue(favouritesPage.getItems());
                    nextPage = favouritesPage.getNext();
                    Timber.tag(TAG).d("next page: %s", nextPage);
                    Timber.tag(TAG).d("previous page: %s", favouritesPage.getPrevious());
                    Timber.tag(TAG).d("count: %s", favouritesPage.getCount() );
                    postPagination.pageReceived();
                });
    }

    void setFavouritesInAdapter(List<FavouritesItem> favouritesPage) {
        Timber.tag(TAG).d("setFavouritesInAdapter");
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

    class PostPagination extends PaginatedRecyclerView.Pagination implements PageReceivedListener {

        @Override
        public int getPageStart() {
            return 0;
        }

        @Override
        public void onPaginate(int index) {
            Timber.tag(TAG).d("onPaginate");
            Timber.tag(TAG).d("index: %s", index);
//            loadPage(index);
            loadPage(nextPage == null ? 0 : nextPage);
        }

        @Override
        public void pageReceived() {
            Timber.tag(TAG).d("pageReceived");
            if (nextPage == null) notifyPaginationFinished();
            else notifyLoadingCompleted();
        }
    }
}
