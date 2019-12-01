package ru.fitsme.android.data.frameworks.retrofit;

import android.annotation.SuppressLint;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderUpdate;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.data.repositories.returns.entity.ReturnsPage;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.entities.order.ReturnsOrder;
import ru.fitsme.android.domain.entities.profile.Profile;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.utils.OrderStatus;

abstract class WebLoader {

    private ApiService apiService;
    private IAuthInteractor authInteractor;
    private Scheduler workThread;
    private Scheduler mainThread;
    private final static String TOKEN = "Token ";

    WebLoader(ApiService apiService,
              IAuthInteractor authInteractor,
              Scheduler workThread,
              Scheduler mainThread) {
        this.apiService = apiService;
        this.authInteractor = authInteractor;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    public Single signIn(@NonNull SignInfo signInfo) {
        return apiService.signIn(signInfo)
                .map(authTokenOkResponse -> extractAuthInfo(signInfo, authTokenOkResponse));
    }

    public Single<AuthInfo> signUp(@NonNull SignInfo signInfo) {
        return apiService.signUp(signInfo)
                .map(authTokenOkResponse -> extractAuthInfo(signInfo, authTokenOkResponse));
    }

    private AuthInfo extractAuthInfo(SignInfo signInfo, OkResponse<AuthToken> authTokenOkResponse) {
        AuthToken authToken = authTokenOkResponse.getResponse();
        if (authToken != null) {
            String token = authToken.getToken();
            return new AuthInfo(signInfo.getLogin(), token);
        } else {
            Error error = authTokenOkResponse.getError();
            UserException userException = ErrorRepository.makeError(error);
            return new AuthInfo(userException);
        }
    }

    @SuppressLint("CheckResult")
    public Single<OkResponse<LikedClothesItem>> likeItem(ClothesItem clothesItem, boolean liked) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> {
                    if (clothesItem != null) {
                        int id = clothesItem.getId();
                        apiService.likeItem(TOKEN + authInfo.getToken(), new LikedItem(id, liked))
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError);
                    } else {
                        emitter.onError(new NullPointerException(
                                WebLoader.class.getSimpleName() + " likeItem() : clotheItem is null"));
                    }
                }, emitter::onError));
    }

    public Single<OkResponse<ClothesPage>> getClothesPage(int page) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.getClothes(TOKEN + authInfo.getToken(), page)
                        .subscribeOn(workThread)
                        .subscribe(emitter::onSuccess, emitter::onError), emitter::onError));
    }

    public Single<OkResponse<FavouritesPage>> getFavouritesClothesPage(int page) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.getFavouritesClothes(TOKEN + authInfo.getToken(), page)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<ReturnsPage>> getReturnsClothesPage(int page) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.getReturnsClothes(TOKEN + authInfo.getToken(), page)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<FavouritesItem>> deleteFavouriteItem(FavouritesItem item) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.removeItemFromFavourites(TOKEN + authInfo.getToken(), item.getId())
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<FavouritesItem>> restoreFavouriteItem(FavouritesItem item) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(authInfo -> apiService.restoreItemToFavourites(TOKEN + authInfo.getToken(), item.getId())
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<OrderItem>> addItemToCart(FavouritesItem favouritesItem, int quantity) {
        int clotheId = favouritesItem.getItem().getId();
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.addItemToCart(TOKEN + authInfo.getToken(), new OrderedItem(clotheId, quantity))
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<OrdersPage>> getOrdersPage(int page) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getOrders(TOKEN + authInfo.getToken(), page)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError)
        );
    }

    public Single<Response<Void>> removeItemFromOrder(OrderItem item) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.removeItemFromCart(TOKEN + authInfo.getToken(), item.getId())
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError)
        );
    }

    public Single<OkResponse<OrderItem>> restoreItemToOrder(OrderItem item) {
        int clotheId = item.getClothe().getId();
        int quantity;
        if (item.getQuantity() == 0) {
            quantity = 1;
        } else {
            quantity = item.getQuantity();
        }
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.addItemToCart(TOKEN + authInfo.getToken(), new OrderedItem(clotheId, quantity))
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<OrdersPage>> getOrders(OrderStatus status) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getOrders(TOKEN + authInfo.getToken(), status)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<OrdersPage>> getReturnsOrders() {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getReturnsOrders(TOKEN + authInfo.getToken())
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
        return Single.create(emitter -> authInteractor.getAuthInfo()
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

    public Single<OkResponse<List<ClotheSize>>> getSizes() {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getClotheSizes(TOKEN + authInfo.getToken())
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError
                ));
    }

    public Single<OkResponse<Profile>> getProfile() {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getProfile(TOKEN + authInfo.getToken())
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError
                ));
    }

    public Single<OkResponse<Profile>> setProfile(Profile profile) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.setProfile(TOKEN + authInfo.getToken(), profile)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError
                ));
    }

    public Single<OkResponse<ReturnsOrder>> addItemToReturn(ReturnsItemRequest request) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.addItemToReturn(TOKEN + authInfo.getToken(), request)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<ReturnsOrder>> changeReturnsPayment(ReturnsPaymentRequest request) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.changeReturnsPayment(TOKEN + authInfo.getToken(), request.getReturnId(), request)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }

    public Single<OkResponse<ReturnsOrder>> getReturnById(int returnId) {
        return Single.create(emitter -> authInteractor.getAuthInfo()
                .subscribe(
                        authInfo -> apiService.getReturnById(TOKEN + authInfo.getToken(), returnId)
                                .subscribeOn(workThread)
                                .subscribe(emitter::onSuccess, emitter::onError),
                        emitter::onError));
    }
}
