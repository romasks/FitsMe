package ru.fitsme.android.App;

import android.app.Application;

import ru.fitsme.android.Data.Repositories.SignInRepositoryDebug;
import ru.fitsme.android.Data.Repositories.UserInfoRepositoryDebug;
import ru.fitsme.android.Domain.Interactors.Auth.ISignInRepository;
import ru.fitsme.android.Domain.Interactors.Auth.ISignInUpInteractor;
import ru.fitsme.android.Domain.Interactors.Auth.IUserInfoRepository;
import ru.fitsme.android.Domain.Interactors.Auth.SignInUpInteractor;
import timber.log.Timber;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class App extends Application {
    public static <T> void inject(T object) {
        Scope appScope = Toothpick.openScope(object);
        appScope.installModules(new Module() {{
            bind(ISignInUpInteractor.class).to(SignInUpInteractor.class);
            bind(ISignInRepository.class).to(SignInRepositoryDebug.class);
            bind(IUserInfoRepository.class).to(UserInfoRepositoryDebug.class);
        }});
        Toothpick.inject(object, appScope);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
