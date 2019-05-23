package ru.fitsme.android.data.mapper;

import ru.fitsme.android.data.model.OrderRequest;
import ru.fitsme.android.data.model.OrderResponse;
import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.utils.OrderStatus;

public class OrderMapper implements Mapper<OrderResponse, OrderRequest, Order> {
    @Override
    public Order mapFromEntity(OrderResponse type) {
        return new Order(type.orderId, type.city, type.street, type.houseNumber,
                type.apartment, type.phoneNumber, type.orderStatus, type.orderItemList);
    }

    @Override
    public OrderRequest mapToEntity(Order type) {
        return new OrderRequest(type.phoneNumber, type.street, type.houseNumber, type.apartment, OrderStatus.FM);
    }
}
