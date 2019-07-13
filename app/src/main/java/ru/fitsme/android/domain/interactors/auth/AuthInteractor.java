package ru.fitsme.android.domain.interactors.auth;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.domain.boundaries.signinup.IAuthRepository;
import ru.fitsme.android.domain.boundaries.signinup.ITextValidator;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

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
}

//    @Override
//    @NonNull
//    public Single<SignInUpResult> register(@Nullable String login, @Nullable String password) {
////        auto = false;
//        return doOperation(() -> {
//            textValidator.checkLogin(login);
//            textValidator.checkPassword(password);
//            SignInInfo signInInfo = new SignInInfo(login, password);
//            AuthInfo authInfo = authRepository.register(signInInfo);
//            saveUserInfo(signInInfo, authInfo);
//        });
//    }
//
//    @Override
//    @NonNull
//    public Single<SignInUpResult> authorize(@Nullable String login, @Nullable String password) {
////        auto = false;
//        return doOperation(() -> {
//            textValidator.checkLogin(login);
//            textValidator.checkPassword(password);
//            doAuthorize(new SignInInfo(login, password));
//        });
//    }
//
//    private void doAuthorize(SignInInfo signInInfo) throws UserException {
//        AuthInfo authInfo = authRepository.authorize(signInInfo);
//        saveUserInfo(signInInfo, authInfo);
//    }
//
//    private void saveUserInfo(SignInInfo signInInfo, AuthInfo authInfo) {
//        userInfoRepository.setAuthInfo(authInfo);
//        userInfoRepository.setSignInInfo(signInInfo);
//    }
//
//    @NonNull
//    @Override
//    public Single<SignInUpResult> authorize(@NonNull SignInInfo signInInfo) {
//        return doOperation(() -> doAuthorize(signInInfo));
//    }
//
//    @Override
//    @NonNull
//    public Single<SignInUpResult> checkLogin(@Nullable String login) {
//        return doOperation(() -> textValidator.checkLogin(login));
//    }
//
//    @Override
//    @NonNull
//    public Single<SignInUpResult> checkPassword(@Nullable String password) {
//        return doOperation(() -> textValidator.checkPassword(password));
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
//    public Single<SignInInfo> getAuthInfo() {
//        return Single.create((SingleOnSubscribe<SignInInfo>) emitter -> {
//            SignInInfo signInInfo = userInfoRepository.getSignInInfo();
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
