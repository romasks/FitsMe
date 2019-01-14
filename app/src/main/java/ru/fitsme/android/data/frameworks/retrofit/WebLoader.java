package ru.fitsme.android.data.frameworks.retrofit;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.domain.entities.exceptions.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.ServerInternalException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public class WebLoader {

    private ApiService apiService;

    @Inject
    public WebLoader(ApiService apiService) {
        this.apiService = apiService;
    }

    public AuthInfo signIn(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            ServerInternalException, InternetConnectionException {
        Call<AuthToken> responseCall = apiService.signIn(signInInfo.getLogin(),
                signInInfo.getPasswordHash());

        try {
            Response<AuthToken> authTokenResponse = responseCall.execute();

            if (authTokenResponse.isSuccessful() && authTokenResponse.body() != null) {
                return new AuthInfo(signInInfo.getLogin(), authTokenResponse.body().getToken());
            }
            switch (authTokenResponse.code()) {
                case 401:
                    throw new LoginIncorrectException();
                default:
                    throw new ServerInternalException();
            }
        } catch (IOException e) {
            throw new InternetConnectionException();
        }
    }

    public AuthInfo signUp(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            LoginAlreadyInUseException, ServerInternalException, InternetConnectionException {
        Call<AuthToken> responseCall = apiService.signUp(signInInfo.getLogin(),
                signInInfo.getPasswordHash());

        try {
            Response<AuthToken> authTokenResponse = responseCall.execute();

            if (authTokenResponse.isSuccessful() && authTokenResponse.body() != null) {
                return new AuthInfo(signInInfo.getLogin(), authTokenResponse.body().getToken());
            }
            switch (authTokenResponse.code()) {
                case 401:
                    throw new LoginIncorrectException();
                case 409:
                    throw new LoginAlreadyInUseException();
                default:
                    throw new ServerInternalException();
            }
        } catch (IOException e) {
            throw new InternetConnectionException();
        }
    }
}
