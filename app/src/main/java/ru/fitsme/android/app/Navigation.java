package ru.fitsme.android.app;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_IN_UP;

@Singleton
public class Navigation {
    private Cicerone<Router> cicerone;

    @Inject
    public Navigation() {
        cicerone = Cicerone.create();
        getRouter().newRootScreen(NAV_SIGN_IN_UP);
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }
}
