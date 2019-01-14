package ru.fitsme.android.app;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.repositories.SignInUpRepositoryDebug;
import ru.fitsme.android.data.repositories.TextValidatorDebug;
import ru.fitsme.android.data.repositories.UserInfoRepositoryDebug;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.domain.interactors.auth.ISignInUpRepository;
import ru.fitsme.android.domain.interactors.auth.ITextValidator;
import ru.fitsme.android.domain.interactors.auth.IUserInfoRepository;
import ru.fitsme.android.domain.interactors.auth.SignInUpInteractor;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

@Singleton
public class DI {
    private Scope appScope;

    @Inject
    public DI(Application application) {
        appScope = Toothpick.openScope(application);
        appScope.installModules(new Module() {{
            bind(ISignInUpInteractor.class).to(SignInUpInteractor.class);
            bind(ISignInUpRepository.class).to(SignInUpRepositoryDebug.class);
            bind(IUserInfoRepository.class).to(UserInfoRepositoryDebug.class);
            bind(ITextValidator.class).to(TextValidatorDebug.class);
        }});
    }

    public <T> void inject(T object) {
        Toothpick.inject(object, appScope);
    }
}
