package ru.fitsme.android.data.repositories.auth;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.boundaries.auth.ISignRepository;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.SignInfo;

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
}
