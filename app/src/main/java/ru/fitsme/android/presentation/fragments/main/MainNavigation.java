package ru.fitsme.android.presentation.fragments.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SIGN_IN_UP;

@Singleton
public class MainNavigation {

    static final String NAV_RATE_ITEMS = "RateItems";
    static final String NAV_FAVOURITES = "Favourites";
    static final String NAV_CART = "Cart";
    static final String NAV_CHECKOUT = "Checkout";
    static final String NAV_MAIN_PROFILE = "MainProfile";
    static final String NAV_SIZE_PROFILE = "SizeProfile";
    static final String NAV_TYPE_PROFILE = "TypeProfile";
    static final String NAV_ORDER_HISTORY_PROFILE = "OrderProfile";
    static final String NAV_ORDER_RETURN_PROFILE = "ReturnProfile";
    static final String NAV_LEAVE_FEEDBACK = "LeaveFeedback";
    static final String NAV_RETURN_DETAILS = "ReturnDetails";
    static final String NAV_RETURNS_HOW_TO = "ReturnsHowTo";
    static final String NAV_RETURNS_CHOOSE_ORDER = "ReturnsChooseOrder";
    static final String NAV_RETURNS_CHOOSE_ITEMS = "ReturnsChooseItems";
    static final String NAV_RETURNS_INDICATE_NUMBER = "ReturnsIndicateNumber";
    static final String NAV_RETURNS_BILLING_INFO = "ReturnsBillingInfo";
    static final String NAV_RETURNS_VERIFY_DATA = "ReturnsVerifyData";
    static final String NAV_DETAIL_ITEM_INFO = "DetailItemInfo";
    static final String NAV_FILTER = "Filter";
    static final String NAV_CODE = "Code";

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

    private void goNavigate(String navigationKey, int data) {
        getRouter().navigateTo(navigationKey, data);
    }

    private void goNavigate(String navigationKey, Object data) {
        getRouter().navigateTo(navigationKey, data);
    }

    private void replaceWith(String navigationKey) {
        getRouter().replaceScreen(navigationKey);
    }

    private void newChainNavigate(String navigationKey) {
        getRouter().newScreenChain(navigationKey);
    }

    private void backNavigate(String navigationKey) {
        getRouter().backTo(navigationKey);
    }

    public void setNavigator(Navigator navigator) {
        getNavigatorHolder().setNavigator(navigator);
    }

    public void removeNavigator() {
        getNavigatorHolder().removeNavigator();
    }

    public void goSignInUp() {
        getRouter().newRootScreen(NAV_SIGN_IN_UP);
    }

    public void goToRateItems() {
        goNavigate(NAV_RATE_ITEMS);
    }

    public void goToFavourites() {
        newChainNavigate(NAV_FAVOURITES);
    }

    public void goToCart() {
        newChainNavigate(NAV_CART);
    }

    public void goToCode() {
        newChainNavigate(NAV_CODE);
    }

    public void goToCheckout() {
        newChainNavigate(NAV_CHECKOUT);
    }

    public void goToMainProfile() {
        newChainNavigate(NAV_MAIN_PROFILE);
    }

    public void goToSizeProfile() {
        goNavigate(NAV_SIZE_PROFILE);
    }

    public void goToOrdersReturn() {
        goNavigate(NAV_ORDER_RETURN_PROFILE);
    }

    public void goToLeaveFeedback() {
        goNavigate(NAV_LEAVE_FEEDBACK);
    }

    public void goToTypeProfile() {
        goNavigate(NAV_TYPE_PROFILE);
    }

    public void goToOrderHistoryProfile() {
        goNavigate(NAV_ORDER_HISTORY_PROFILE);
    }

    public void goToOrderReturnProfile() {
        goNavigate(NAV_ORDER_RETURN_PROFILE);
    }

    public void goToReturnDetails(int returnId) {
        goNavigate(NAV_RETURN_DETAILS, returnId);
    }

    public void goToReturnsHowTo() {
        goNavigate(NAV_RETURNS_HOW_TO);
    }

    public void goToReturnsChooseOrder() {
        goNavigate(NAV_RETURNS_CHOOSE_ORDER);
    }

    public void goToReturnsChooseItems() {
        goNavigate(NAV_RETURNS_CHOOSE_ITEMS);
    }

    public void goToReturnsIndicateNumber() {
        goNavigate(NAV_RETURNS_INDICATE_NUMBER);
    }

    public void goToReturnsBillingInfo() {
        goNavigate(NAV_RETURNS_BILLING_INFO);
    }

    public void goToReturnsVerifyData() {
        goNavigate(NAV_RETURNS_VERIFY_DATA);
    }

    public void goToReturnsChooseOrderWithReplace() {
        replaceWith(NAV_RETURNS_CHOOSE_ORDER);
    }

    public void goToReturnsChooseItemsWithReplace() {
        replaceWith(NAV_RETURNS_CHOOSE_ITEMS);
    }

    public void goToReturnsIndicateNumberWithReplace() {
        replaceWith(NAV_RETURNS_INDICATE_NUMBER);
    }

    public void goToReturnsBillingInfoWithReplace() {
        replaceWith(NAV_RETURNS_BILLING_INFO);
    }

    public void goToReturnsVerifyDataWithReplace() {
        replaceWith(NAV_RETURNS_VERIFY_DATA);
    }

    public void goToOrdersReturnWithReplace() {
        replaceWith(NAV_ORDER_RETURN_PROFILE);
    }

    public void goToDetailItemInfo(ClothesItem clothesItem) {
        goNavigate(NAV_DETAIL_ITEM_INFO, clothesItem);
    }

    public void goBack() {
        getRouter().exit();
    }

    public void finish() {
        getRouter().finishChain();
    }

    public void goToFilter() {
        goNavigate(NAV_FILTER);
    }
}
