package ru.fitsme.android.data.frameworks.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderUpdate;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderedItem;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.internal.InternalException;
import ru.fitsme.android.domain.entities.exceptions.user.ClotheNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.user.InvalidTokenException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginAlreadyExistException;
import ru.fitsme.android.domain.entities.exceptions.user.ProductInListOfViewedException;
import ru.fitsme.android.domain.entities.exceptions.user.TokenNotSearchUser;
import ru.fitsme.android.domain.entities.exceptions.user.TokenOutOfDateException;
import ru.fitsme.android.domain.entities.exceptions.user.TokenUserNotActiveException;
import ru.fitsme.android.domain.entities.exceptions.user.UnknowError;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginOrPasswordException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongPasswordException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongTokenException;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class WebLoader {

    private ApiService apiService;
    private IAuthInteractor authInteractor;
    private Scheduler workThread;
    private Scheduler mainThread;
    private final static String TOKEN = "Token ";

    @Inject
    WebLoader(ApiService apiService,
              IAuthInteractor authInteractor,
              @Named("work") Scheduler workThread,
              @Named("main") Scheduler mainThread){
        this.apiService = apiService;
        this.authInteractor = authInteractor;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    public Single<AuthInfo> signIn(@NonNull SignInfo signInfo){
        return apiService.signIn(signInfo)
                .map(authTokenOkResponse -> extractAuthInfo(signInfo, authTokenOkResponse));
    }

    public Single<AuthInfo> signUp(@NonNull SignInfo signInfo){
        return apiService.signUp(signInfo)
                .map(authTokenOkResponse -> extractAuthInfo(signInfo, authTokenOkResponse));
    }

    private AuthInfo extractAuthInfo(SignInfo signInfo, OkResponse<AuthToken> authTokenOkResponse) {
        AuthToken authToken = authTokenOkResponse.getResponse();
        if (authToken != null){
            String token = authToken.getToken();
            return new AuthInfo(signInfo.getLogin(), token);
        } else {
            Error error = authTokenOkResponse.getError();
            UserException userException = makeError(error);
            return new AuthInfo(userException);
        }
    }

    public Single<OkResponse<LikedClothesItem>> likeItem(ClothesItem clothesItem, boolean liked) {
        return Single.create(emitter -> {
            authInteractor.getAuthInfo()
                    .subscribe(authInfo -> {
                        int id = clothesItem.getId();
                        apiService.likeItem(TOKEN + authInfo.getToken(), new LikedItem(id, liked))
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError);
                    }, emitter::onError);
        });
    }

    public Single<OkResponse<ClothesPage>> getClothesPage(int page) {
        return Single.create(emitter -> {
            authInteractor.getAuthInfo()
                    .subscribe(authInfo -> {
                        apiService.getClothes(TOKEN + authInfo.getToken(), page)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError);
                    }, emitter::onError);
        });
    }

    public Single<OkResponse<FavouritesPage>> getFavouritesClothesPage(int page){
        return Single.create(emitter -> {
            authInteractor.getAuthInfo()
                    .subscribe(authInfo -> apiService.getFavouritesClothes(TOKEN + authInfo.getToken(), page)
                            .subscribeOn(workThread)
                            .subscribe(emitter::onSuccess, emitter::onError),
                            emitter::onError);
        });
    }

    public Single<OkResponse<FavouritesItem>> deleteFavouriteItem(FavouritesItem item) {
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.removeItemFromFavourites(TOKEN + authInfo.getToken(), item.getId())
                        .subscribeOn(workThread)
                        .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<FavouritesItem>> restoreFavouriteItem(FavouritesItem item) {
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.restoreItemToFavourites(TOKEN + authInfo.getToken(), item.getId())
                        .subscribeOn(workThread)
                        .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<OrderItem>> addItemToCart(FavouritesItem favouritesItem, int quantity) {
        int clotheId = favouritesItem.getItem().getId();
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                        .subscribe(
                                authInfo -> apiService.addItemToCart(TOKEN + authInfo.getToken(), new OrderedItem(clotheId, quantity))
                                        .subscribeOn(workThread)
                                        .subscribe(emitter::onSuccess, emitter::onError),
                                emitter::onError));
    }

    public Single<OkResponse<OrdersPage>> getOrdersPage(int page) {
        return Single.create(
                emitter -> authInteractor.getAuthInfo()
                        .subscribe(
                                authInfo -> apiService.getOrders(TOKEN + authInfo.getToken(), page)
                                        .subscribeOn(workThread)
                                        .subscribe(emitter::onSuccess, emitter::onError),
                                emitter::onError)
        );
    }

    public Single<Response<Void>> removeItemFromOrder(OrderItem item) {
        return Single.create(
                emitter -> authInteractor.getAuthInfo()
                        .subscribe(
                                authInfo -> apiService.removeItemFromCart(TOKEN + authInfo.getToken(), item.getId())
                                        .subscribeOn(workThread)
                                        .subscribe(emitter::onSuccess, emitter::onError),
                                emitter::onError)
        );
    }

    public Single<OkResponse<OrderItem>> restoreItemToOrder(OrderItem item){
        int clotheId = item.getClothe().getId();
        int quantity;
        if (item.getQuantity() == 0){
            quantity = 1;
        } else {
            quantity = item.getQuantity();
        }
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                        .subscribe(
                                authInfo -> apiService.addItemToCart(TOKEN + authInfo.getToken(), new OrderedItem(clotheId, quantity))
                                        .subscribeOn(workThread)
                                        .subscribe(emitter::onSuccess, emitter::onError),
                                emitter::onError));
    }

    public Single<OkResponse<OrdersPage>> getOrders(OrderStatus status) {
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getOrders(TOKEN + authInfo.getToken(), status)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<Order>> makeOrder(long orderId,
                                   String phoneNumber,
                                   String street,
                                   String houseNumber,
                                   String apartment,
                                   OrderStatus orderStatus) {
        return Single.create(emitter ->
                authInteractor.getAuthInfo()
                        .subscribe(
                                authInfo -> apiService.updateOrderById(
                                        TOKEN + authInfo.getToken(),
                                        orderId,
                                        new OrderUpdate(
                                                phoneNumber,
                                                street,
                                                houseNumber,
                                                apartment,
                                                orderStatus))
                                        .subscribeOn(workThread)
                                        .subscribe(emitter::onSuccess, emitter::onError),
                                emitter::onError));
    }

    @NonNull
    private UserException makeError(Error error){
        switch (error.getCode()) {
            case WrongLoginOrPasswordException.CODE:
                return new WrongLoginOrPasswordException(error.getMessage());
            case LoginAlreadyExistException.CODE:
                return new LoginAlreadyExistException(error.getMessage());
            case WrongLoginException.CODE:
                return new WrongLoginException(error.getMessage());
            case WrongPasswordException.CODE:
                return new WrongPasswordException(error.getMessage());
            case WrongTokenException.CODE:
                return new WrongTokenException(error.getMessage());
            case InvalidTokenException.CODE:
                return new InvalidTokenException(error.getMessage());
            case TokenOutOfDateException.CODE:
                return new TokenOutOfDateException(error.getMessage());
            case TokenNotSearchUser.CODE:
                return new TokenNotSearchUser(error.getMessage());
            case TokenUserNotActiveException.CODE:
                return new TokenUserNotActiveException(error.getMessage());
            case ProductInListOfViewedException.CODE:
                return new ProductInListOfViewedException(error.getMessage());
            case ClotheNotFoundException.CODE:
                return new ClotheNotFoundException(error.getMessage());
            default:
                return new UnknowError();
        }
    }
    private <T> T getResponse(OkResponse<T> okResponse) throws UserException, InternalException {
        if (okResponse.getResponse() != null) {
            return okResponse.getResponse();
        }
        throw makeError(okResponse.getError());
    }
    private <T> T executeRequest(@NonNull ExecutableRequest<T> executableRequest)
            throws UserException {
        try {
            Timber.tag("WebLoader request URL").d(executableRequest.request().request().url().toString());
            Response<OkResponse<T>> response = executableRequest.request().execute();

            if (response.isSuccessful() && response.body() != null) {
                return getResponse(response.body());
            }

            if (response.isSuccessful() && response.body() == null) {
                return null;
            }
        } catch (IOException | InternalException | JsonSyntaxException e) {
            Timber.e(e);
        }
        throw new InternetConnectionException();
    }

    public interface ExecutableRequest<T> {


        @NonNull
        Call<OkResponse<T>> request();

    }

    private String getHeaderToken() {
        return "Token " + authInteractor.getAuthInfoNotSingle().getToken();
    }


}
