package ru.fitsme.android.domain.interactors.auth;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.domain.boundaries.auth.IAuthRepository;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

@Singleton
public class AuthInteractor implements IAuthInteractor {
    private IAuthRepository authRepository;
    private Scheduler mainThread;
    private Scheduler workThread;

    @Inject
    public AuthInteractor(IAuthRepository authRepository,
                          @Named("main") Scheduler mainThread,
                          @Named("work") Scheduler workThread) {
        this.authRepository = authRepository;
        this.mainThread = mainThread;
        this.workThread = workThread;
    }

    @Override
    public Single<AuthInfo> getAuthInfo() {
        return Single.create(emitter -> {
            AuthInfo authInfo = authRepository.getAuthInfo();
            emitter.onSuccess(authInfo);
        })
                .subscribeOn(workThread)
                .observeOn(mainThread)
                .cast(AuthInfo.class);
    }
}
