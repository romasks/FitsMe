package ru.fitsme.android.data.frameworks.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.entities.exceptions.internal.InternalException;
import ru.fitsme.android.domain.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginOrPasswordNotValidException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;
import timber.log.Timber;

public class WebLoader {

    private ApiService apiService;

    @Inject
    WebLoader(ApiService apiService) {
        this.apiService = apiService;
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
            case 100001:
                return new LoginOrPasswordNotValidException();
            case 100002:
                return new LoginAlreadyInUseException();
            case 100003:
                return new LoginIncorrectException();
            case 900001:
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

    @NonNull
    private <T> T executeRequest(@NonNull ExecutableRequest<T> executableRequest)
            throws UserException {
        try {
            Response<OkResponse<T>> response = executableRequest.request().execute();

            if (response.isSuccessful() && response.body() != null) {
                return getResponse(response.body());
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

    public interface ExecutableRequest<T> {
        @NonNull
        Call<OkResponse<T>> request();
    }
}
