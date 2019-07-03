package ru.fitsme.android.app;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Singleton
public class Navigation {
    public static final String NAV_SIGN_IN_UP = "SignInUp";
    public static final String NAV_SIGN_UP = "SignUp";
    public static final String NAV_SIGN_IN = "SignIn";
    public static final String NAV_MAIN_ITEM = "MainItem";
    public static final String NAV_SPLASH = "Splash";

    private Cicerone<Router> cicerone;

    @Inject
    public Navigation() {
        cicerone = Cicerone.create();
//        getRouter().newRootScreen(NAV_SPLASH);
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

    public void goSignInUp() {
        goNavigate(NAV_SIGN_IN_UP);
    }

    public void goSignIn() {
        goNavigate(NAV_SIGN_IN);
    }

    public void goSignUp() {
        goNavigate(NAV_SIGN_UP);
    }

    public void goToMainItem() {
        goNavigate(NAV_MAIN_ITEM);
    }

    public void goToSplash(){
        goNavigate(NAV_SPLASH);
    }
}
