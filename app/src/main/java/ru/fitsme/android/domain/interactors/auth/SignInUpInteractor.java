package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.fitsme.android.domain.boundaries.IResourceRepository;
import ru.fitsme.android.domain.boundaries.ISignInUpRepository;
import ru.fitsme.android.domain.boundaries.ITextValidator;
import ru.fitsme.android.domain.boundaries.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.LoginNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.PasswordIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.PasswordNotValidException;
import ru.fitsme.android.domain.entities.exceptions.ServerInternalException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInUpResult;

@Singleton
public class SignInUpInteractor implements ISignInUpInteractor {
    private ISignInUpRepository signInRepository;
    private IUserInfoRepository userInfoRepository;
    private ITextValidator textValidator;
    private IResourceRepository resourceRepository;

    @Inject
    public SignInUpInteractor(ISignInUpRepository signInRepository,
                              IUserInfoRepository userInfoRepository,
                              ITextValidator textValidator,
                              IResourceRepository resourceRepository) {
        this.signInRepository = signInRepository;
        this.userInfoRepository = userInfoRepository;
        this.textValidator = textValidator;
        this.resourceRepository = resourceRepository;
    }

    @Override
    @NonNull
    public Single<SignInUpResult> register(@Nullable String login, @Nullable String password) {
        return doOperation(() -> {
            textValidator.checkLogin(login);
            textValidator.checkPassword(password);
            SignInInfo signInInfo = new SignInInfo(login, password);
            AuthInfo authInfo = signInRepository.register(signInInfo);
            userInfoRepository.setAuthInfo(authInfo);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    @NonNull
    public Single<SignInUpResult> authorize(@Nullable String login, @Nullable String password) {
        return doOperation(() -> {
            textValidator.checkLogin(login);
            textValidator.checkPassword(password);
            SignInInfo signInInfo = new SignInInfo(login, password);
            AuthInfo authInfo = signInRepository.authorize(signInInfo);
            userInfoRepository.setAuthInfo(authInfo);
        }).subscribeOn(Schedulers.io());
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
        return Single.create(emitter -> {
            SignInUpResult signInUpResult = SignInUpResult.build();
            try {
                signInUpOperation.operation();
            } catch (InternetConnectionException | ServerInternalException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setCommonError(error);
            } catch (LoginIncorrectException | LoginNotFoundException |
                    LoginAlreadyInUseException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setLoginError(error);
            } catch (PasswordIncorrectException | PasswordNotValidException e) {
                String error = resourceRepository.getUserErrorMessage(e);
                signInUpResult.setPasswordError(error);
            }
            emitter.onSuccess(signInUpResult);
        });
    }

    private interface SignInUpOperation {
        void operation() throws InternetConnectionException, ServerInternalException,
                LoginAlreadyInUseException, LoginIncorrectException, LoginNotFoundException,
                PasswordIncorrectException, PasswordNotValidException;
    }
}
