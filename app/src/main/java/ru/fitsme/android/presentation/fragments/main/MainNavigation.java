package ru.fitsme.android.presentation.fragments.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Singleton
public class MainNavigation {

    public static final String NAV_RATE_ITEMS = "RateItems";
    public static final String NAV_FAVOURITES = "Favourites";
    public static final String NAV_CART = "Cart";
    public static final String NAV_CHECKOUT = "Checkout";
    public static final String NAV_MAIN_PROFILE = "MainProfile";
    public static final String NAV_SIZE_PROFILE = "SizeProfile";
    public static final String NAV_TYPE_PROFILE = "TypeProfile";
    public static final String NAV_ORDER_HISTORY_PROFILE = "OrderProfile";
    public static final String NAV_ORDER_RETURN_PROFILE = "ReturnProfile";

    private Cicerone<Router> cicerone;

    @Inject
    public MainNavigation() {
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

    public void goToRateItems(){
        goNavigate(NAV_RATE_ITEMS);
    }

    public void goToFavourites(){
        goNavigate(NAV_FAVOURITES);
    }

    public void goToCart(){
        goNavigate(NAV_CART);
    }

    public void goToCheckout(){
        goNavigate(NAV_CHECKOUT);
    }

    public void goToMainProfile(){
        goNavigate(NAV_MAIN_PROFILE);
    }

    public void goToSizeProfile(){
        goNavigate(NAV_SIZE_PROFILE);
    }

    public void goToTypeProfile(){
        goNavigate(NAV_TYPE_PROFILE);
    }

    public void goToOrderHistoryProfile(){
        goNavigate(NAV_ORDER_HISTORY_PROFILE);
    }

    public void goToOrderReturnProfile(){
        goNavigate(NAV_ORDER_RETURN_PROFILE);
    }
}
