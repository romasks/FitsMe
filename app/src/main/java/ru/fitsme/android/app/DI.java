package ru.fitsme.android.app;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.repositories.ResourceRepository;
import ru.fitsme.android.data.repositories.SignInUpRepositoryDebug;
import ru.fitsme.android.data.repositories.TextValidator;
import ru.fitsme.android.data.repositories.UserInfoRepository;
import ru.fitsme.android.domain.boundaries.IResourceRepository;
import ru.fitsme.android.domain.boundaries.ISignInUpRepository;
import ru.fitsme.android.domain.boundaries.ITextValidator;
import ru.fitsme.android.domain.boundaries.IUserInfoRepository;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
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
            bind(IUserInfoRepository.class).to(UserInfoRepository.class);
            bind(ITextValidator.class).to(TextValidator.class);
            bind(IResourceRepository.class).to(ResourceRepository.class);
            bind(Context.class).toInstance(application);
        }});
    }

    public <T> void inject(T object) {
        Toothpick.inject(object, appScope);
    }
}
