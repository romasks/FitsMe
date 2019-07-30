package ru.fitsme.android.data.repositories.favourites;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import timber.log.Timber;

public class FavouritesActionRepository implements IFavouritesActionRepository {
    private final WebLoader webLoader;

    @Inject
    FavouritesActionRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public Single<FavouritesItem> removeItem(FavouritesItem item) {
        return Single.create(emitter ->
                webLoader.deleteFavouriteItem(item)
                        .subscribe(favouritesItemOkResponse -> {
                            FavouritesItem dislikedItem = favouritesItemOkResponse.getResponse();
                            if (dislikedItem != null){
                                emitter.onSuccess(dislikedItem);
                            } else {
                                UserException error = ErrorRepository.makeError(favouritesItemOkResponse.getError());
                                Timber.e(error);
                                emitter.onSuccess(item);
                            }
                }, emitter::onError));
    }

    @Override
    public Single<FavouritesItem> restoreItem(FavouritesItem item) {
        return Single.create(emitter ->
                webLoader.restoreFavouriteItem(item)
                        .subscribe(favouritesItemOkResponse -> {
                            FavouritesItem restoredItem = favouritesItemOkResponse.getResponse();
                            if (restoredItem != null){
                                emitter.onSuccess(restoredItem);
                            } else {
                                UserException error = ErrorRepository.makeError(favouritesItemOkResponse.getError());
                                Timber.e(error);
                                emitter.onSuccess(item);
                            }
                        }, emitter::onError));
    }

    @Override
    public Single<OrderItem> addItemToCart(FavouritesItem favouritesItem) {
        return Single.create(emitter ->
                webLoader.addItemToCart(favouritesItem, 1)
                        .subscribe(orderItemOkResponse -> {
                            OrderItem orderItem = orderItemOkResponse.getResponse();
                            if (orderItem != null){
                                emitter.onSuccess(orderItem);
                            } else {
                                UserException error = ErrorRepository.makeError(orderItemOkResponse.getError());
                                Timber.e(error);
                            }
                            }, error -> {
                            if (error instanceof HttpException){
                                HttpException httpException = (HttpException) error;
                                Response response = httpException.response();
                                ResponseBody errorBody = response.errorBody();
                                if (errorBody != null) {
                                    String string = errorBody.string();
                                    JSONObject jsonObject = new JSONObject(string);
                                    JSONObject errorJson = jsonObject.getJSONObject("error");
                                    String code = errorJson.getString("code");
                                    String message = errorJson.getString("message");
                                    UserException userException = ErrorRepository.makeError(new Error(Integer.parseInt(code), message));
                                }
                            }
                            Timber.e(error);
                            }));
    }
}
