package ru.fitsme.android.data.repositories.returns;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.Collections;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.returns.entity.ReturnsPage;
import ru.fitsme.android.domain.boundaries.retunrs.IReturnsRepository;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.ReturnsInteractor;
import timber.log.Timber;

public class ReturnsRepository extends PageKeyedDataSource<Integer, ReturnsOrder>
        implements IReturnsRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    ReturnsRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ReturnsOrder> callback) {
        ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.loading));
        webLoader.getReturnsClothesPage(1)
                .subscribe(response -> {
                    ReturnsPage returnsPage = response.getResponse();
                    if (returnsPage != null) {
                        callback.onResult(returnsPage.getItems(), null, returnsPage.getNext());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null, null);
                    }
                    ReturnsInteractor.setShowMessage(null);
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReturnsOrder> callback) {
        webLoader.getReturnsClothesPage(params.key)
                .subscribe(response -> {
                    ReturnsPage returnsPage = response.getResponse();
                    if (returnsPage != null) {
                        callback.onResult(returnsPage.getItems(), returnsPage.getPrevious());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReturnsOrder> callback) {
        webLoader.getReturnsClothesPage(params.key)
                .subscribe(response -> {
                    ReturnsPage returnsPage = response.getResponse();
                    if (returnsPage != null) {
                        callback.onResult(returnsPage.getItems(), returnsPage.getNext());
                    } else {
                        Timber.e(ErrorRepository.makeError(response.getError()));
                        callback.onResult(Collections.emptyList(), null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setShowMessage(App.getInstance().getString(R.string.error));
                });
    }
}
