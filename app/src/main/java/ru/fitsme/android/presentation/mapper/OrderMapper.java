package ru.fitsme.android.presentation.mapper;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.model.Order;
import ru.fitsme.android.presentation.model.OrderViewModel;

/**
 * Map a [Order] to and from a [OrderViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
public class OrderMapper implements Mapper<OrderViewModel, Order> {

    @Inject public OrderMapper() {}

    /**
     * Map a [Order] instance to a [OrderViewModel] instance
     */
    @Override
    public OrderViewModel mapToViewModel(Order type) {
        OrderViewModel orderViewModel = new OrderViewModel(type.getOrderId());

        orderViewModel.setCity(type.getCity());
        orderViewModel.setStreet(type.getStreet());
        orderViewModel.setHouseNumber(type.getHouseNumber());
        orderViewModel.setApartment(type.getApartment());
        orderViewModel.setPhoneNumber(type.getPhoneNumber());

        int price = 0;
        for (OrderItem item : type.getOrderItemList()) {
            price += item.getPrice();
        }
        orderViewModel.setPrice(String.valueOf(price));

        int discount = 300;
        orderViewModel.setDiscount(String.valueOf(discount));

        orderViewModel.setTotalPrice(String.valueOf(price + discount));

        return orderViewModel;
    }

}
