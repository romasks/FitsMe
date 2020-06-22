package ru.fitsme.android.presentation.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Singleton
public class AuthNavigation {
    public static final String NAV_MAIN_ITEM = "MainItem";
    public static final String NAV_SPLASH = "Splash";
    public static final String NAV_AUTH = "Auth";
    public static final String NAV_CODE_INPUT = "CodeInput";
    public static final String NAV_AGREEMENT = "Agreement";

    private Cicerone<Router> cicerone;

    @Inject
    public AuthNavigation() {
        cicerone = Cicerone.create();
    }

    private NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    private Router getRouter() {
        return cicerone.getRouter();
    }

    private void goNavigate(String navigationKey) {
        getRouter().navigateTo(navigationKey);
    }

    public void setNavigator(Navigator navigator) {
        getNavigatorHolder().setNavigator(navigator);
    }

    public void removeNavigator() {
        getNavigatorHolder().removeNavigator();
    }

    public void goToMainItem() {
        getRouter().newRootScreen(NAV_MAIN_ITEM);
    }

    public void goToSplash() {
        goNavigate(NAV_SPLASH);
    }

    public void goToAuth() {
        getRouter().newRootScreen(NAV_AUTH);
    }

    public void goToCodeInput() {
        goNavigate(NAV_CODE_INPUT);
    }

    public void goToAgreement() {
        goNavigate(NAV_AGREEMENT);
    }

    public void goBack() {
        getRouter().exit();
    }

    public void finish() {
        getRouter().finishChain();
    }
}
