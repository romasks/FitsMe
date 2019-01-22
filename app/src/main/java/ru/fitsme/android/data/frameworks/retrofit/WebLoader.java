package ru.fitsme.android.data.frameworks.retrofit;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
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
    public WebLoader(ApiService apiService) {
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
        AuthToken authToken = executeRequest(signInInfo, param ->
                apiService.signIn(signInInfo.getLogin(), signInInfo.getPasswordHash()));
        return new AuthInfo(signInInfo.getLogin(), authToken.getToken());
    }

    public AuthInfo signUp(@NonNull SignInInfo signInInfo) throws UserException {
        AuthToken authToken = executeRequest(signInInfo, param ->
                apiService.signUp(signInInfo.getLogin(), signInInfo.getPasswordHash()));
        return new AuthInfo(signInInfo.getLogin(), authToken.getToken());
    }


    @NonNull
    private <T, P> T executeRequest(@NonNull P param, @NonNull ExecutableRequest<T, P> executableRequest)
            throws UserException {
        try {
            Response<OkResponse<T>> response = executableRequest.request(param).execute();

            if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                return getResponse(response.body());
            }
        } catch (IOException | InternalException e) {
            Timber.e(e);
        }
        throw new InternetConnectionException();
    }

    public interface ExecutableRequest<T, P> {
        @NonNull
        Call<OkResponse<T>> request(@NonNull P param);
    }
}
