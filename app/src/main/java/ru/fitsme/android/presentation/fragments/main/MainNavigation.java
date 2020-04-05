package ru.fitsme.android.presentation.fragments.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

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
    static final String NAV_ORDER_DETAILS = "OrderDetails";
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

    private void replaceWith(String navigationKey, int data) {
        getRouter().replaceScreen(navigationKey, data);
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

    public void goToReturnsChooseOrderWithReplace() {
        replaceWith(NAV_RETURNS_CHOOSE_ORDER);
    }

    public void goToReturnsHowToWithReplace() {
        replaceWith(NAV_RETURNS_HOW_TO);
    }

    public void goToReturnsChooseItemsWithReplace(int orderId) {
        replaceWith(NAV_RETURNS_CHOOSE_ITEMS, orderId);
    }

    public void goToReturnsIndicateNumberWithReplace(int returnId) {
        replaceWith(NAV_RETURNS_INDICATE_NUMBER, returnId);
    }

    public void goToReturnsBillingInfoWithReplace(int returnId) {
        replaceWith(NAV_RETURNS_BILLING_INFO, returnId);
    }

    public void goToReturnsVerifyDataWithReplace(int returnId) {
        replaceWith(NAV_RETURNS_VERIFY_DATA, returnId);
    }

    public void goToOrdersReturnWithReplace() {
        replaceWith(NAV_ORDER_RETURN_PROFILE);
    }

    public void goToDetailItemInfo(ClotheInfo clotheInfo) {
        goNavigate(NAV_DETAIL_ITEM_INFO, clotheInfo);
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

    public void goToOrderDetails(Order order) {
        goNavigate(NAV_ORDER_DETAILS, order);
    }
}
