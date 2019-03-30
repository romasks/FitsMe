package ru.fitsme.android.domain.interactors.orders;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;

@Singleton
public class OrdersInteractor implements IOrdersInteractor {

    private final String TAG = getClass().getName();

    private final IFavouritesRepository favouritesRepository;
    private final IOrdersRepository orderRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    OrdersInteractor(IFavouritesRepository favouritesRepository,
                     IOrdersRepository orderRepository,
                     IUserInfoRepository userInfoRepository,
                     @Named("work") Scheduler workThread,
                     @Named("main") Scheduler mainThread) {
        this.favouritesRepository = favouritesRepository;
        this.orderRepository = orderRepository;
        this.userInfoRepository = userInfoRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

/*    @NonNull
    @Override
    public Single<FavouritesItem> getSingleCartItem(int index) {
        return Single.create((SingleOnSubscribe<FavouritesItem>) emitter ->
                emitter.onSuccess(getFavouritesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private FavouritesItem getFavouritesItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return getFavouritesItem(token, index);
    }

    private FavouritesItem getFavouritesItem(String token, int index) throws AppException {
        return favouritesRepository.getFavouritesItem(token, index);
    }

    @NonNull
    @Override
    public Single<List<FavouritesItem>> getSingleCartItems(int page) {
        return Single.create((SingleOnSubscribe<List<FavouritesItem>>) emitter ->
                emitter.onSuccess(getFavouritesItems(page)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private List<FavouritesItem> getFavouritesItems(int page) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return favouritesRepository.getFavouritesPage(token, page).getItems();
    }

    @NonNull
    @Override
    public Single<OrderItem> getSingleOrderItem(int index) {
        return Single.create((SingleOnSubscribe<OrderItem>) emitter ->
                emitter.onSuccess(getOrderItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private OrderItem getOrderItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return orderRepository.getOrderItem(token, index);
    }*/

    @NonNull
    @Override
    public Single<Order> getSingleOrder(int page) {
        return null;
    }

    @NonNull
    @Override
    public Completable removeItemFromOrder(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable restoreItemToOrder(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable makeOrder(OrderItem order) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            orderRepository.makeOrder(token, order);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
