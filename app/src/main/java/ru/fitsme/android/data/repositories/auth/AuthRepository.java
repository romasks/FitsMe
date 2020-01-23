package ru.fitsme.android.data.repositories.auth;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.IAuthInfoStorage;
import ru.fitsme.android.domain.boundaries.auth.IAuthRepository;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.CodeSentInfo;

@Singleton
public class AuthRepository implements IAuthRepository {

    private final WebLoaderNetworkChecker webLoader;
    private IAuthInfoStorage authInfoStorage;

    @Inject
    AuthRepository(IAuthInfoStorage authInfoStorage,
                   WebLoaderNetworkChecker webLoader) {
        this.authInfoStorage = authInfoStorage;
        this.webLoader = webLoader;
    }

    @Override
    public AuthInfo getAuthInfo() {
        AuthInfo authInfo = App.getInstance().getAuthInfo();
        if (authInfo == null){
            updateAppAuthInfo();
            authInfo = App.getInstance().getAuthInfo();
        }
        return authInfo;
    }

    @Override
    public void setAuthInfo(AuthInfo authInfo) {
        authInfoStorage.setAuthInfo(authInfo);
        updateAppAuthInfo();
    }

    @Override
    public Single<Boolean> sendPhoneNumber(String phoneNumber) {
        return Single.create(emitter -> webLoader.sendPhoneNumber(phoneNumber)
                .subscribe(codeSentInfoOkResponse -> {
                    CodeSentInfo info = codeSentInfoOkResponse.getResponse();
                    if (info.getStatus().equals("confirmation code sent")){
                        emitter.onSuccess(true);
                    } else {
                        emitter.onSuccess(false);
                    }
                }, emitter::onError));
    }

    private void updateAppAuthInfo(){
        AuthInfo authInfo = authInfoStorage.getAuthInfo();
        App.getInstance().setAuthInfo(authInfo);
    }
}
