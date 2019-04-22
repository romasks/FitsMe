package ru.fitsme.android.data.api;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import ru.fitsme.android.data.entities.response.AuthToken;
import ru.fitsme.android.data.entities.request.LikedItem;
import ru.fitsme.android.data.entities.request.OrderUpdate;
import ru.fitsme.android.data.entities.request.OrderedItem;
import ru.fitsme.android.data.entities.response.Error;
import ru.fitsme.android.data.entities.response.OkResponse;
import ru.fitsme.android.data.entities.response.clothes.ClothesPage;
import ru.fitsme.android.data.entities.response.favourites.FavouritesPage;
import ru.fitsme.android.data.entities.response.orders.OrdersPage;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.data.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.data.entities.exceptions.internal.InternalException;
import ru.fitsme.android.data.entities.exceptions.user.ClotheNotFoundException;
import ru.fitsme.android.data.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.data.entities.exceptions.user.InvalidTokenException;
import ru.fitsme.android.data.entities.exceptions.user.LoginAlreadyExistException;
import ru.fitsme.android.data.entities.exceptions.user.ProductInListOfViewedException;
import ru.fitsme.android.data.entities.exceptions.user.TokenNotSearchUser;
import ru.fitsme.android.data.entities.exceptions.user.TokenOutOfDateException;
import ru.fitsme.android.data.entities.exceptions.user.TokenUserNotActiveException;
import ru.fitsme.android.data.entities.exceptions.user.UserException;
import ru.fitsme.android.data.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.data.entities.exceptions.user.WrongLoginOrPasswordException;
import ru.fitsme.android.data.entities.exceptions.user.WrongPasswordException;
import ru.fitsme.android.data.entities.exceptions.user.WrongTokenException;
import ru.fitsme.android.data.entities.response.signinup.AuthInfo;
import ru.fitsme.android.data.entities.response.signinup.SignInInfo;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class WebLoader {

    private ApiService apiService;
    private IUserInfoRepository userInfoRepository;

    @Inject
    WebLoader(ApiService apiService, IUserInfoRepository userInfoRepository) {
        this.apiService = apiService;
        this.userInfoRepository = userInfoRepository;
    }

    private <T> T getResponse(OkResponse<T> okResponse) throws UserException, InternalException {
        if (okResponse.getResponse() != null) {
            return okResponse.getResponse();
        }
        throw makeException(okResponse.getError());
    }

    @NonNull
    private UserException makeException(Error error) throws InternalException {
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
            case InternalException.CODE:
                throw new InternalException(error.getMessage());
        }
        throw new InternalException("Unknown error (" + error.getCode() + "):" + error.getMessage());
    }

    public AuthInfo signIn(@NonNull SignInInfo signInInfo) throws UserException {
        AuthToken authToken = executeRequest(() -> apiService.signIn(signInInfo));
        return new AuthInfo(signInInfo.getLogin(), authToken.getToken());
    }

    public AuthInfo signUp(@NonNull SignInInfo signInInfo) throws UserException {
        AuthToken authToken = executeRequest(() -> apiService.signUp(signInInfo));
        return new AuthInfo(signInInfo.getLogin(), authToken.getToken());
    }

    public ClothesPage getClothesPage(@NonNull String token, int page) throws UserException {
        String headerToken = "Token " + token;
        return executeRequest(() -> apiService.getClothes(headerToken, page));
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

    public void likeItem(@NonNull String token, int id, boolean liked) throws UserException {
        String headerToken = "Token " + token;
        executeRequest(() -> apiService.likeItem(headerToken, new LikedItem(id, liked)));
    }

    public FavouritesPage getFavouritesClothesPage(@NonNull String token, int page) throws UserException {
        String headerToken = "Token " + token;
        return executeRequest(() -> apiService.getFavouritesClothes(headerToken, page));
    }

    public void deleteFavouriteItem(@NonNull String token, int itemId) throws UserException {
        String headerToken = "Token " + token;
        try {
            apiService.deleteFavouritesItem(headerToken, itemId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemToCart(@NonNull String token, int id, int quantity) throws UserException {
        String headerToken = "Token " + token;
        executeRequest(() -> apiService.addItemToCart(headerToken, new OrderedItem(id, quantity)));
    }

    public void makeOrder(
            int orderId, String phoneNumber, String street, String houseNumber, String apartment, OrderStatus orderStatus
    ) throws DataNotFoundException, UserException {

        String headerToken = "Token " + userInfoRepository.getAuthInfo().getToken();
        executeRequest(() -> apiService.updateOrderById(headerToken, orderId,
                new OrderUpdate(phoneNumber, street, houseNumber, apartment, orderStatus)));
    }

    public OrdersPage getOrders(int page) throws DataNotFoundException, UserException {
        String headerToken = "Token " + userInfoRepository.getAuthInfo().getToken();
        return executeRequest(() -> apiService.getOrders(headerToken, page));
    }

    public interface ExecutableRequest<T> {
        @NonNull
        Call<OkResponse<T>> request();
    }
}
