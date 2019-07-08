package ru.fitsme.android.data.repositories.orders;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.mapper.OrderWebMapper;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class OrdersRepository extends PageKeyedDataSource<Integer, OrderItem>
        implements IOrdersRepository {

    private final WebLoader webLoader;

    @Inject
    OrderWebMapper mapper;

    @Inject
    OrdersRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public OrdersPage getOrders(OrderStatus status) throws AppException {
        return webLoader.getOrders(status);
    }

    @Override
    public void makeOrder(Order order) throws AppException {
        webLoader.makeOrder(order.orderId, mapper.mapToEntity(order));
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, OrderItem> callback) {
        try {
            OrdersPage ordersPage = webLoader.getOrdersPage(1);
            Timber.d("mapper: %s", mapper);
            List<Order> ordersList = ordersPage.getOrdersList(mapper);
            List<OrderItem> orderItemList;
            if (ordersList.size() == 0) {
                orderItemList = new ArrayList<>();
            } else {
                orderItemList = ordersList.get(0).orderItemList;
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
            Timber.d("mapper: %s", mapper);
            List<Order> ordersList = ordersPage.getOrdersList(mapper);
            callback.onResult(ordersList.get(0).orderItemList, ordersPage.getNextPage());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
