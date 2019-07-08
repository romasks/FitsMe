package ru.fitsme.android.data.repositories.orders;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public class OrdersRepository extends PageKeyedDataSource<Integer, OrderItem>
    implements IOrdersRepository {

    private final WebLoader webLoader;

    @Inject
    OrdersRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, OrderItem> callback) {
        try {
            OrdersPage ordersPage = webLoader.getOrdersPage(1);
            List<Order> ordersList = ordersPage.getOrdersList();
            List<OrderItem> orderItemList;
            if (ordersList.size() == 0) {
                orderItemList = new ArrayList<>();
            } else {
                orderItemList = ordersList.get(0).getOrderItemList();
            }
            callback.onResult(orderItemList, ordersPage.getPreviousPage(), ordersPage.getNextPage());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, OrderItem> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, OrderItem> callback) {
        try {
            OrdersPage ordersPage = webLoader.getOrdersPage(params.key);
            List<Order> ordersList = ordersPage.getOrdersList();
            callback.onResult(ordersList.get(0).getOrderItemList(), ordersPage.getNextPage());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
