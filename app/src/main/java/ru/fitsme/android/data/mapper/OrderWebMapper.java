package ru.fitsme.android.data.mapper;

import javax.inject.Inject;

import ru.fitsme.android.data.model.OrderRequest;
import ru.fitsme.android.data.model.OrderResponse;
import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.utils.OrderStatus;

/**
 * Map a [Order] to and from a [OrderRequest, OrderResponse] instance when data is moving between
 * this layer and the Domain layer
 */
public class OrderWebMapper implements Mapper<OrderResponse, OrderRequest, Order> {

    @Inject OrderWebMapper() {}

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
