package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.signinup.IResourceRepository;
import ru.fitsme.android.domain.boundaries.signinup.ISignInUpRepository;
import ru.fitsme.android.domain.boundaries.signinup.ITextValidator;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginAlreadyExistException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginOrPasswordException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongPasswordException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.AutoSignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInUpResult;

@Singleton
public class SignInUpInteractor implements ISignInUpInteractor {
    private static boolean auto = true;
    private ISignInUpRepository signInRepository;
    private IUserInfoRepository userInfoRepository;
    private ITextValidator textValidator;
    private IResourceRepository resourceRepository;
    private Scheduler mainThread;
    private Scheduler workThread;

    @Inject
    public SignInUpInteractor(ISignInUpRepository signInRepository,
                              IUserInfoRepository userInfoRepository,
                              ITextValidator textValidator,
                              IResourceRepository resourceRepository,
                              @Named("main") Scheduler mainThread,
                              @Named("work") Scheduler workThread) {
        this.signInRepository = signInRepository;
        this.userInfoRepository = userInfoRepository;
        this.textValidator = textValidator;
        this.resourceRepository = resourceRepository;
        this.mainThread = mainThread;
        this.workThread = workThread;
    }

    @Override
    @NonNull
    public Single<SignInUpResult> register(@Nullable String login, @Nullable String password) {
        auto = false;
        return doOperation(() -> {
            textValidator.checkLogin(login);
            textValidator.checkPassword(password);
            SignInInfo signInInfo = new SignInInfo(login, password);
            AuthInfo authInfo = signInRepository.register(signInInfo);
            saveUserInfo(signInInfo, authInfo);
        });
    }

    @Override
    @NonNull
    public Single<SignInUpResult> authorize(@Nullable String login, @Nullable String password) {
        auto = false;
        return doOperation(() -> {
            textValidator.checkLogin(login);
            textValidator.checkPassword(password);
            doAuthorize(new SignInInfo(login, password));
        });
    }

    private void doAuthorize(SignInInfo signInInfo) throws UserException {
        AuthInfo authInfo = signInRepository.authorize(signInInfo);
        saveUserInfo(signInInfo, authInfo);
    }

    private void saveUserInfo(SignInInfo signInInfo, AuthInfo authInfo) {
        userInfoRepository.setAuthInfo(authInfo);
        userInfoRepository.setSignInInfo(signInInfo);
    }

    @NonNull
    @Override
    public Single<SignInUpResult> authorize(@NonNull SignInInfo signInInfo) {
        return doOperation(() -> doAuthorize(signInInfo));
    }

    @Override
    @NonNull
    public Single<SignInUpResult> checkLogin(@Nullable String login) {
        return doOperation(() -> textValidator.checkLogin(login));
    }

    @Override
    @NonNull
    public Single<SignInUpResult> checkPassword(@Nullable String password) {
        return doOperation(() -> textValidator.checkPassword(password));
    }

    @NonNull
    private Single<SignInUpResult> doOperation(@NonNull SignInUpOperation signInUpOperation) {
        return Single.create((SingleOnSubscribe<SignInUpResult>) emitter -> {
            SignInUpResult signInUpResult = SignInUpResult.build();
            try {//TODO: переписать обработчики исключений
                signInUpOperation.operation();
            } catch (InternetConnectionException | WrongLoginOrPasswordException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setCommonError(error);
            } catch (WrongLoginException |
                    LoginAlreadyExistException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setLoginError(error);
            } catch (WrongPasswordException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setPasswordError(error);
            } catch (UserException e) {
                //unhandled user exception
            }
            emitter.onSuccess(signInUpResult);
        }).subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @Override
    @NonNull
    public Single<AutoSignInInfo> getAutoSignInInfo() {
        return Single.create((SingleOnSubscribe<AutoSignInInfo>) emitter -> {
            SignInInfo signInInfo;
            try {
                signInInfo = userInfoRepository.getSignInInfo();
            } catch (DataNotFoundException e) {
                signInInfo = null;
            }
            emitter.onSuccess(new AutoSignInInfo(signInInfo, auto));
        }).subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private interface SignInUpOperation {
        void operation() throws UserException;
    }
}
