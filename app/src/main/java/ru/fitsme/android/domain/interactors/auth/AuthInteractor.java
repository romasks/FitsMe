package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.boundaries.signinup.IAuthRepository;
import ru.fitsme.android.domain.boundaries.signinup.ITextValidator;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;

@Singleton
public class AuthInteractor implements IAuthInteractor {
    private IAuthRepository authRepository;
    private ITextValidator textValidator;
    private Scheduler mainThread;
    private Scheduler workThread;

    @Inject
    public AuthInteractor(IAuthRepository authRepository,
                          ITextValidator textValidator,
                          @Named("main") Scheduler mainThread,
                          @Named("work") Scheduler workThread) {
        this.authRepository = authRepository;
        this.textValidator = textValidator;
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

    private void checkLoginAndPass(SignInUpResult signInUpResult, String login, String password){
        if (!textValidator.checkLogin(login)){
            String string = App.getInstance().getResources().getString(R.string.login_incorrect_error);
            signInUpResult.setLoginError(string);
        } else if (!textValidator.checkPassword(password)){
            String string = App.getInstance().getResources().getString(R.string.password_incorrect_error);
            signInUpResult.setPasswordError(string);
        }
    }

    @Override
    @NonNull
    public Single<SignInUpResult> signIn(@Nullable String login, @Nullable String password) {
        return Single.create(emitter -> {
            SignInUpResult signInUpResult = SignInUpResult.build();
            checkLoginAndPass(signInUpResult, login, password);
            if (signInUpResult.isSuccess()){
                authRepository
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
                authRepository
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


}

//    @Override
//    @NonNull
//    public Single<SignInUpResult> signUp(@Nullable String login, @Nullable String password) {
////        auto = false;
//        return doOperation(() -> {
//            textValidator.checkLogin(login);
//            textValidator.checkPassword(password);
//            SignInfo signInInfo = new SignInfo(login, password);
//            AuthInfo authInfo = authRepository.signUp(signInInfo);
//            saveUserInfo(signInInfo, authInfo);
//        });
//    }
//

//
//    private void doAuthorize(SignInfo signInInfo) throws UserException {
//        AuthInfo authInfo = authRepository.signIn(signInInfo);
//        saveUserInfo(signInInfo, authInfo);
//    }
//
//    private void saveUserInfo(SignInfo signInInfo, AuthInfo authInfo) {
//        userInfoRepository.setAuthInfo(authInfo);
//        userInfoRepository.setSignInInfo(signInInfo);
//    }
//
//    @NonNull
//    @Override
//    public Single<SignInUpResult> signIn(@NonNull SignInfo signInInfo) {
//        return doOperation(() -> doAuthorize(signInInfo));
//    }
//
//    @NonNull
//    private Single<SignInUpResult> doOperation(@NonNull SignInUpOperation signInUpOperation) {
//        return Single.create((SingleOnSubscribe<SignInUpResult>) emitter -> {
//            SignInUpResult signInUpResult = SignInUpResult.build();
//            try {//TODO: переписать обработчики исключений
//                signInUpOperation.operation();
//            } catch (InternetConnectionException | WrongLoginOrPasswordException e) {
//                String error = resourceRepository.getUserErrorMessage(e);
//                signInUpResult.setCommonError(error);
//            } catch (WrongLoginException |
//                    LoginAlreadyExistException e) {
//                String error = resourceRepository.getUserErrorMessage(e);
//                signInUpResult.setLoginError(error);
//            } catch (WrongPasswordException e) {
//                String error = resourceRepository.getUserErrorMessage(e);
//                signInUpResult.setPasswordError(error);
//            } catch (UserException e) {
//                //unhandled user exception
//            }
//            emitter.onSuccess(signInUpResult);
//        }).subscribeOn(workThread)
//                .observeOn(mainThread);
//    }
//
//    @Override
//    @NonNull
//    public Single<SignInfo> getAuthInfo() {
//        return Single.create((SingleOnSubscribe<SignInfo>) emitter -> {
//            SignInfo signInInfo = userInfoRepository.getSignInInfo();
//            emitter.onSuccess(signInInfo);
//        })
//                .subscribeOn(workThread)
//                .observeOn(mainThread);
//    }
//
//    private interface SignInUpOperation {
//        void operation() throws UserException;
//    }
//}
