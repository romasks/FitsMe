package ru.fitsme.android.domain.boundaries.cart;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public interface ICartActionRepository {

    @NonNull
    Single<OrdersPage> getOrders(OrderStatus status);

    @NonNull
    Single<OrdersPage> getOrdersWithoutStatus();

    @NonNull
    Single<OrdersPage> getReturnsOrders();

    Single<Order> makeOrder(OrderRequest orderRequest);

    Single<Integer> removeItemFromOrder(Integer orderItemId);

    Single<OrderItem> removeItemFromOrder(OrderItem item);

    Single<OrderItem> restoreItemToOrder(OrderItem item);

    Single<Order> getOrderById(int orderId);
}
