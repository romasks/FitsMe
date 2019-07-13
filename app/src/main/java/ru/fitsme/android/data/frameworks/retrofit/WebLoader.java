package ru.fitsme.android.data.frameworks.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;

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
import ru.fitsme.android.domain.entities.auth.SignInInfo;
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
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class WebLoader {

    private ApiService apiService;
//    private IAuthRepository authRepository;

    @Inject
    WebLoader(ApiService apiService){
        this.apiService = apiService;
    }

    public Single<AuthInfo> signIn(@NonNull SignInInfo signInInfo){
        return apiService.signIn(signInInfo)
                .map(authTokenOkResponse -> {
                    AuthToken authToken = authTokenOkResponse.getResponse();
                    if (authToken != null){
                        String token = authToken.getToken();
                        return new AuthInfo(signInInfo.getLogin(), token);
                    } else {
                        Error error = authTokenOkResponse.getError();
                        UserException userException = makeError(error);
                        return new AuthInfo(userException);
                    }
                });
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
        return "Token " + authRepository.getAuthInfo().getToken();
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

    public AuthInfo signUp(@NonNull SignInInfo signInInfo) throws UserException {
        AuthToken authToken = executeRequest(() -> apiService.signUp(signInInfo));
        return new AuthInfo(signInInfo.getLogin(), authToken.getToken());
    }

    public ClothesPage getClothesPage(int page) throws UserException {
        return executeRequest(() -> apiService.getClothes(getHeaderToken(), page));
    }

    public void likeItem(int id, boolean liked) throws UserException {
        executeRequest(() -> apiService.likeItem(getHeaderToken(), new LikedItem(id, liked)));
    }

    public FavouritesPage getFavouritesClothesPage(int page) throws UserException {
        return executeRequest(() -> apiService.getFavouritesClothes(getHeaderToken(), page));
    }

    public OrdersPage getOrdersPage(int page) throws UserException {
        return executeRequest(() -> apiService.getOrders(getHeaderToken(), page));
    }

    public OrdersPage getOrders(OrderStatus status) throws UserException {
        return executeRequest(() -> apiService.getOrders(getHeaderToken(), status));
    }

    public void deleteFavouriteItem(int itemId) throws UserException {
        executeRequest(() -> apiService.deleteFavouritesItem(getHeaderToken(), itemId));
    }

    public void addItemToCart(int id, int quantity) throws UserException {
        executeRequest(() -> apiService.addItemToCart(getHeaderToken(), new OrderedItem(id, quantity)));
    }

    public void makeOrder(
            long orderId, String phoneNumber, String street, String houseNumber, String apartment, OrderStatus orderStatus
    ) throws UserException {

        executeRequest(() -> apiService.updateOrderById(getHeaderToken(), orderId,
                new OrderUpdate(phoneNumber, street, houseNumber, apartment, orderStatus)));
    }
}
