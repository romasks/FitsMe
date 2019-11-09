package ru.fitsme.android.domain.interactors.returns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.repositories.returns.ReturnsDataSourceFactory;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;

@Singleton
public class ReturnsInteractor implements IReturnsInteractor {

    private static final int PAGE_SIZE = 10;

    private final ReturnsDataSourceFactory returnsDataSourceFactory;

    private LiveData<PagedList<ReturnsItem>> pagedListLiveData;
    private PagedList.Config config;
    private MutableLiveData<Boolean> returnsIsEmpty;

    private final static ObservableField<String> showMessage =
            new ObservableField<String>(App.getInstance().getString(R.string.loading));

    @Inject
    ReturnsInteractor(ReturnsDataSourceFactory returnsDataSourceFactory) {
        this.returnsDataSourceFactory = returnsDataSourceFactory;

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    @Override
    public LiveData<PagedList<ReturnsItem>> getPagedListLiveData() {
        returnsIsEmpty = new MutableLiveData<>();
        pagedListLiveData =
                new LivePagedListBuilder<>(this.returnsDataSourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<ReturnsItem>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                returnsIsEmpty.setValue(true);
                            }
                        })
                        .build();

        return Transformations.map(pagedListLiveData, pagedList -> {
            pagedList.addWeakCallback(null, new PagedList.Callback() {

                @Override
                public void onChanged(int position, int count) {
                    //updateTotalPrice();
                }

                @Override
                public void onInserted(int position, int count) {
                    returnsIsEmpty.setValue(false);
                    //updateTotalPrice();
                }

                @Override
                public void onRemoved(int position, int count) {
                    //updateTotalPrice();
                }
            });
            return pagedList;
        });
    }

    @Override
    public LiveData<Boolean> getReturnsIsEmpty() {
        return returnsIsEmpty;
    }

    @Override
    public ObservableField<String> getShowMessage() {
        return showMessage;
    }

    @Override
    public boolean itemIsInCart(int position) {
        PagedList<ReturnsItem> pagedList = pagedListLiveData.getValue();
        if (pagedList != null && pagedList.size() > position) {
            ReturnsItem item = pagedList.get(position);
            if (item != null) {
                return item.isInCart();
            }
        }
        return false;
    }

    @Override
    public void sendReturnOrder(ReturnsItem returnsItem) {

    }

    public static void setFavouriteMessage(String string) {
        showMessage.set(string);
    }
}
