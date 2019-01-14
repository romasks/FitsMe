package ru.fitsme.android.app;

import ru.fitsme.android.data.repositories.SignInRepositoryDebug;
import ru.fitsme.android.data.repositories.UserInfoRepositoryDebug;
import ru.fitsme.android.domain.interactors.auth.ISignInRepository;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.domain.interactors.auth.IUserInfoRepository;
import ru.fitsme.android.domain.interactors.auth.SignInUpInteractor;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class DI {
    public <T> void inject(T object) {
        Scope appScope = Toothpick.openScope(object);
        appScope.installModules(new Module() {{
            bind(ISignInUpInteractor.class).to(SignInUpInteractor.class);
            bind(ISignInRepository.class).to(SignInRepositoryDebug.class);
            bind(IUserInfoRepository.class).to(UserInfoRepositoryDebug.class);
        }});
        Toothpick.inject(object, appScope);
    }
}
