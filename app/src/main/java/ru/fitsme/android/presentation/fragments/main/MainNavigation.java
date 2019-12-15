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
    static final String NAV_RETURN_DETAILS = "ReturnDetails";
    static final String NAV_RETURNS_HOW_TO = "ReturnsHowTo";
    static final String NAV_RETURNS_CHOOSE_ORDER = "ReturnsChooseOrder";
    static final String NAV_RETURNS_CHOOSE_ITEMS = "ReturnsChooseItems";
    static final String NAV_RETURNS_INDICATE_NUMBER = "ReturnsIndicateNumber";
    static final String NAV_RETURNS_BILLING_INFO = "ReturnsBillingInfo";
    static final String NAV_RETURNS_VERIFY_DATA = "ReturnsVerifyData";
    static final String NAV_DETAIL_ITEM_INFO = "DetailItemInfo";

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

    private void goNavigate(String navigationKey, Order returnsOrder) {
        getRouter().navigateTo(navigationKey, returnsOrder);
    }

    private void goNavigate(String navigationKey, ReturnsItem returnsItem) {
        getRouter().navigateTo(navigationKey, returnsItem);
    }

    private void goNavigate(String navigationKey, int returnId) {
        getRouter().navigateTo(navigationKey, returnId);
    }

    private void goNavigate(String navigationKey, Object data) {
        getRouter().navigateTo(navigationKey, data);
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

    public void goToReturnsChooseItems(int orderId) {
        goNavigate(NAV_RETURNS_CHOOSE_ITEMS, orderId);
    }

    public void goToReturnsIndicateNumber(int returnId) {
        goNavigate(NAV_RETURNS_INDICATE_NUMBER, returnId);
    }

    public void goToReturnsBillingInfo(int returnId) {
        goNavigate(NAV_RETURNS_BILLING_INFO, returnId);
    }

    public void goToReturnsVerifyData(int returnId) {
        goNavigate(NAV_RETURNS_VERIFY_DATA, returnId);
    }

    public void backToOrdersReturn() {
        backNavigate(NAV_ORDER_RETURN_PROFILE);
    }

    public void backToReturnsHowTo() {
        backNavigate(NAV_RETURNS_HOW_TO);
    }

    public void backToReturnsChooseOrder() {
        backNavigate(NAV_RETURNS_CHOOSE_ORDER);
    }

    public void backToReturnsChooseItems() {
        backNavigate(NAV_RETURNS_CHOOSE_ITEMS);
    }

    public void backToReturnsIndicateNumber() {
        backNavigate(NAV_RETURNS_INDICATE_NUMBER);
    }

    public void backToReturnsBillingInfo() {
        backNavigate(NAV_RETURNS_BILLING_INFO);
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
}
