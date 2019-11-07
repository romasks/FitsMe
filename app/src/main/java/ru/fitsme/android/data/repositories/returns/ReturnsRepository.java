package ru.fitsme.android.data.repositories.returns;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.returns.entity.ReturnsPage;
import ru.fitsme.android.domain.boundaries.retunrs.IReturnsRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.ReturnsInteractor;
import timber.log.Timber;

public class ReturnsRepository extends PageKeyedDataSource<Integer, ReturnsItem>
        implements IReturnsRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    ReturnsRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ReturnsItem> callback) {
        ReturnsInteractor.setFavouriteMessage(App.getInstance().getString(R.string.loading));
        webLoader.getReturnsClothesPage(1)
                .subscribe(returnsPageOkResponse -> {
                    ReturnsPage returnsPage = returnsPageOkResponse.getResponse();
                    if (returnsPage != null) {
                        List<ReturnsItem> returnsList = returnsPage.getItems();
                        // TODO: temp data:
                        if (returnsList.size() == 0) {
                            List<ClothesItem> clothesList = new ArrayList<>();
                            clothesList.add(new ClothesItem("Adidas", "Кроссовки", 325));
                            clothesList.add(new ClothesItem("Dolce Gabana", "Платье", 2130));
                            clothesList.add(new ClothesItem("Nike", "Кепка", 210));
                            clothesList.add(new ClothesItem("Collins", "Джинсы", 685));

                            returnsList.add(new ReturnsItem(
                                    123L, "черновик", "31.12.2018", 2, 6000,
                                    "Банковская карта", 6, true, clothesList
                            ));
                            returnsList.add(new ReturnsItem(
                                    256L, "в обработке", "11.11.2018", 5, 325,
                                    "Банковская карта", 3, false, clothesList
                            ));
                            returnsList.add(new ReturnsItem(
                                    347L, "выполнено", "23.08.2018", 1, 1234,
                                    "Банковская карта", 7, false, clothesList
                            ));
                            returnsList.add(new ReturnsItem(
                                    852L, "отказ", "12.06.2018", 34, 5,
                                    "Наличные", 3, false, clothesList
                            ));
                        }
                        callback.onResult(returnsList, null, returnsPage.getNext());
//                        callback.onResult(returnsPage.getItems(), null, returnsPage.getNext());
                    } else {
                        UserException error = ErrorRepository.makeError(returnsPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<ReturnsItem> list = new ArrayList();
                        callback.onResult(list, null, null);
                    }
                    ReturnsInteractor.setFavouriteMessage(null);
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReturnsItem> callback) {
        webLoader.getReturnsClothesPage(params.key)
                .subscribe(returnsPageOkResponse -> {
                    ReturnsPage returnsPage = returnsPageOkResponse.getResponse();
                    if (returnsPage != null) {
                        callback.onResult(returnsPage.getItems(), returnsPage.getPrevious());
                    } else {
                        UserException error = ErrorRepository.makeError(returnsPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<ReturnsItem> list = new ArrayList();
                        callback.onResult(list, null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReturnsItem> callback) {
        webLoader.getReturnsClothesPage(params.key)
                .subscribe(returnsPageOkResponse -> {
                    ReturnsPage returnsPage = returnsPageOkResponse.getResponse();
                    if (returnsPage != null) {
                        callback.onResult(returnsPage.getItems(), returnsPage.getNext());
                    } else {
                        UserException error = ErrorRepository.makeError(returnsPageOkResponse.getError());
                        Timber.e(error.getMessage());
                        List<ReturnsItem> list = new ArrayList();
                        callback.onResult(list, null);
                    }
                }, error -> {
                    Timber.e(error);
                    ReturnsInteractor.setFavouriteMessage(App.getInstance().getString(R.string.error));
                });
    }
}
