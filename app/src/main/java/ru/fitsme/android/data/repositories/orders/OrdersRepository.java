package ru.fitsme.android.data.repositories.orders;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.utils.OrderStatus;

public class OrdersRepository implements IOrdersRepository {

    private final WebLoader webLoader;

    @Inject
    public OrdersRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public OrdersPage getOrders(int page) throws AppException {
        return webLoader.getOrders(page);
    }

    @Override
    public void makeOrder(int orderId,
                          String phoneNumber,
                          String destinationAddress,
                          OrderStatus orderStatus) throws AppException {
        webLoader.makeOrder(orderId, phoneNumber, destinationAddress, orderStatus);
    }
}
