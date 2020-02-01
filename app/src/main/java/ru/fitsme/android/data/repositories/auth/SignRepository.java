package ru.fitsme.android.data.repositories.auth;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.retrofit.entities.CodeRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.Error;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.domain.boundaries.auth.ISignRepository;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.CodeResponse;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.auth.TokenRequest;
import ru.fitsme.android.domain.entities.auth.TokenResponse;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class SignRepository implements ISignRepository {

    private WebLoaderNetworkChecker webLoader;

    @Inject
    SignRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public Single<AuthInfo> signIn(@NonNull SignInfo signInfo) {
        return webLoader.signIn(signInfo);
    }

    @NonNull
    @Override
    public Single<AuthInfo> signUp(@NonNull SignInfo signInfo) {
        return webLoader.signUp(signInfo);
    }


    @Override
    public Single<CodeResponse> sendPhoneNumber(String phoneNumber) {
        return Single.create(emitter -> {
            CodeRequest codeRequest = new CodeRequest(phoneNumber);
            webLoader.sendCodeRequest(codeRequest)
                .subscribe(codeSentInfoOkResponse -> {
                    CodeResponse info = codeSentInfoOkResponse.getResponse();
                    emitter.onSuccess(info);
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
                            emitter.onError(userException);
                        } else {
                            emitter.onError(error);
                        }
                    } else {
                        emitter.onError(error);
                    }
                });
        });
    }

    @Override
    public Single<AuthInfo> verifyCode(String phoneNumber, String code) {
        return Single.create(emitter -> {
            TokenRequest request = new TokenRequest(phoneNumber, code);
            webLoader.sendCode(request)
                    .subscribe(tokenResponseOkResponse -> {
                        TokenResponse response = tokenResponseOkResponse.getResponse();
                        AuthInfo authInfo = new AuthInfo(phoneNumber, response.getToken());
                        emitter.onSuccess(authInfo);
                    }, emitter::onError);
        });
    }
}
