package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.boundaries.auth.IAuthRepository;
import ru.fitsme.android.domain.boundaries.auth.ISignRepository;
import ru.fitsme.android.domain.boundaries.auth.ITextValidator;
import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class SignInteractor implements ISignInteractor {

    private IAuthRepository authRepository;
    private ISignRepository signRepository;
    private ITextValidator textValidator;
    private Scheduler mainThread;
    private Scheduler workThread;

    @Inject
    public SignInteractor(IAuthRepository authRepository,
                          ISignRepository signRepository,
                          ITextValidator textValidator,
                          @Named("main") Scheduler mainThread,
                          @Named("work") Scheduler workThread){
        this.authRepository = authRepository;
        this.signRepository = signRepository;
        this.textValidator = textValidator;
        this.mainThread = mainThread;
        this.workThread = workThread;
    }

    @Override
    @NonNull
    public Single<SignInUpResult> signIn(@Nullable String login, @Nullable String password) {
        return Single.create(emitter -> {
            SignInUpResult signInUpResult = SignInUpResult.build();
            checkLoginAndPass(signInUpResult, login, password);
            if (signInUpResult.isSuccess()){
                signRepository
                        .signIn(new SignInfo(login, password))
                        .subscribe(authInfo -> {
                            if (authInfo.isAuth()){
                                authRepository.setAuthInfo(authInfo);
                            } else {
                                UserException error = authInfo.getError();
                                signInUpResult.setCommonError(error.getMessage());
                            }
                            emitter.onSuccess(signInUpResult);
                        }, emitter::onError);
            } else {
                emitter.onSuccess(signInUpResult);
            }
        })
                .subscribeOn(workThread)
                .observeOn(mainThread)
                .cast(SignInUpResult.class);
    }

    @NonNull
    @Override
    public Single<SignInUpResult> signUp(@Nullable String login, @Nullable String password) {
        return Single.create(emitter -> {
            SignInUpResult signInUpResult = SignInUpResult.build();
            checkLoginAndPass(signInUpResult, login, password);
            if (signInUpResult.isSuccess()){
                signRepository
                        .signUp(new SignInfo(login, password))
                        .subscribe(authInfo -> {
                            if (authInfo.isAuth()){
                                authRepository.setAuthInfo(authInfo);
                            } else {
                                UserException error = authInfo.getError();
                                signInUpResult.setCommonError(error.getMessage());
                            }
                            emitter.onSuccess(signInUpResult);
                        }, emitter::onError);
            }
            else {
                emitter.onSuccess(signInUpResult);
            }
        })
                .subscribeOn(workThread)
                .observeOn(mainThread)
                .cast(SignInUpResult.class);
    }

    private void checkLoginAndPass(SignInUpResult signInUpResult, String login, String password){
        if (!textValidator.checkLogin(login)){
            String string = App.getInstance().getResources().getString(R.string.login_incorrect_error);
            signInUpResult.setLoginError(string);
        } else if (!textValidator.checkPassword(password)){
            String string = App.getInstance().getResources().getString(R.string.password_incorrect_error);
            signInUpResult.setPasswordError(string);
        }
    }

}
