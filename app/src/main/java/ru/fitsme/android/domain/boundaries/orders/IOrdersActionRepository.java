package ru.fitsme.android.domain.boundaries.orders;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersActionRepository {

    @NonNull
    Single<OrdersPage> getOrders(OrderStatus status);

    @NonNull
    Single<OrdersPage> getOrdersWithoutStatus();

    @NonNull
    Single<OrdersPage> getReturnsOrders();

    Single<Order> makeOrder(long orderId, String phoneNumber, String street, String houseNumber,
                            String apartment, OrderStatus orderStatus);

    Single<OrderItem> removeItemFromOrder(OrderItem item);

    Single<OrderItem> restoreItemToOrder(OrderItem item);

    Single<Order> getOrderById(int orderId);
}
