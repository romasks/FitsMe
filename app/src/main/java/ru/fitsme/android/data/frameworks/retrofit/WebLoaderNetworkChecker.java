package ru.fitsme.android.data.frameworks.retrofit;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import retrofit2.Response;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.NetworkStatus;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.data.repositories.returns.entity.ReturnsPage;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.CodeSentInfo;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheBrand;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheColor;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheProductName;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.entities.profile.Profile;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.main.viewmodel.MainViewModel;
import ru.fitsme.android.utils.OrderStatus;

public class WebLoaderNetworkChecker extends WebLoader {

    @Inject
    WebLoaderNetworkChecker(ApiService apiService,
                            IAuthInteractor authInteractor,
                            @Named("work") Scheduler workThread,
                            @Named("main") Scheduler mainThread) {
        super(apiService, authInteractor, workThread, mainThread);
    }

    @Override
    public Single<AuthInfo> signIn(@NonNull SignInfo signInfo) {
        return checkNetwork(super.signIn(signInfo));
    }

    @Override
    public Single<AuthInfo> signUp(@NonNull SignInfo signInfo) {
        return checkNetwork(super.signUp(signInfo));
    }

    @Override
    public Single<OkResponse<LikedClothesItem>> likeItem(ClothesItem clothesItem, boolean liked) {
        return checkNetwork(super.likeItem(clothesItem, liked));
    }

    @Override
    public Single<OkResponse<ClothesPage>> getClothesPage(int page) {
        return checkNetwork(super.getClothesPage(page));
    }

    @Override
    public Single<OkResponse<FavouritesPage>> getFavouritesClothesPage(int page) {
        return checkNetwork(super.getFavouritesClothesPage(page));
    }

    @Override
    public Single<OkResponse<ReturnsPage>> getReturnsClothesPage(int page) {
        return checkNetwork(super.getReturnsClothesPage(page));
    }

    @Override
    public Single<OkResponse<FavouritesItem>> deleteFavouriteItem(FavouritesItem item) {
        return checkNetwork(super.deleteFavouriteItem(item));
    }

    @Override
    public Single<OkResponse<FavouritesItem>> restoreFavouriteItem(FavouritesItem item) {
        return checkNetwork(super.restoreFavouriteItem(item));
    }

    @Override
    public Single<OkResponse<OrderItem>> addItemToCart(FavouritesItem favouritesItem, int quantity) {
        return checkNetwork(super.addItemToCart(favouritesItem, quantity));
    }

    @Override
    public Single<OkResponse<OrdersPage>> getOrdersPage(int page) {
        return checkNetwork(super.getOrdersPage(page));
    }

    @Override
    public Single<Response<Void>> removeItemFromOrder(OrderItem item) {
        return checkNetwork(super.removeItemFromOrder(item));
    }

    @Override
    public Single<OkResponse<OrderItem>> restoreItemToOrder(OrderItem item) {
        return checkNetwork(super.restoreItemToOrder(item));
    }

    @Override
    public Single<OkResponse<OrdersPage>> getOrders(OrderStatus status) {
        return checkNetwork(super.getOrders(status));
    }

    @Override
    public Single<OkResponse<OrdersPage>> getReturnsOrders() {
        return checkNetwork(super.getReturnsOrders());
    }

    @Override
    public Single<OkResponse<Order>> makeOrder(long orderId, String phoneNumber, String street, String houseNumber, String apartment, OrderStatus orderStatus) {
        return checkNetwork(super.makeOrder(orderId, phoneNumber, street, houseNumber, apartment, orderStatus));
    }

    @Override
    public Single<OkResponse<Profile>> setProfile(Profile profile) {
        return checkNetwork(super.setProfile(profile));
    }

    @Override
    public Single<OkResponse<ReturnsOrderItem>> addItemToReturn(ReturnsItemRequest request) {
        return checkNetwork(super.addItemToReturn(request));
    }

    @Override
    public Single<OkResponse<ReturnsOrderItem>> changeReturnsPayment(ReturnsPaymentRequest request) {
        return checkNetwork(super.changeReturnsPayment(request));
    }

    @Override
    public Single<OkResponse<ReturnsOrder>> getReturnById(int returnId) {
        return checkNetwork(super.getReturnById(returnId));
    }

    @Override
    public Single<OkResponse<Order>> getOrderById(int orderId) {
        return checkNetwork(super.getOrderById(orderId));
    }

    @Override
    public Single<OkResponse<Boolean>> sendFeedback(FeedbackRequest request) {
        return checkNetwork(super.sendFeedback(request));
    }

    @Override
    public Single<OkResponse<List<RepoClotheBrand>>> getBrandList() {
        return checkNetwork(super.getBrandList());
    }

    @Override
    public Single<OkResponse<List<RepoClotheColor>>> getColorList() {
        return checkNetwork(super.getColorList());
    }

    @Override
    public Single<OkResponse<List<RepoClotheProductName>>> getProductNameList() {
        return checkNetwork(super.getProductNameList());
    }

    private Single checkNetwork(Single single) {
        if (NetworkStatus.isOnline()) {
            MainViewModel.isOnline.set(true);
            return single;
        } else {
            MainViewModel.isOnline.set(false);
            return Single.error(new InternetConnectionException(
                    App.getInstance().getString(R.string.internet_connection_error)));
        }
    }

    public Single<OkResponse<CodeSentInfo>> sendPhoneNumber(String phoneNumber) {
        return checkNetwork(super.sendPhoneNumber(phoneNumber));
    }
}
