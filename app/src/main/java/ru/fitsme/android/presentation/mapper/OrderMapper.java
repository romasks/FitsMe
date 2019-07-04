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
        OrderViewModel orderViewModel = new OrderViewModel(type.orderId);

        orderViewModel.setCity(type.city);
        orderViewModel.setStreet(type.street);
        orderViewModel.setHouseNumber(type.houseNumber);
        orderViewModel.setApartment(type.apartment);
        orderViewModel.setPhoneNumber(type.phoneNumber);

        int price = 0;
        for (OrderItem item : type.orderItemList) {
            price += item.getPrice();
        }
        orderViewModel.setPrice(String.valueOf(price));

        int discount = 300;
        orderViewModel.setDiscount(String.valueOf(discount));

        orderViewModel.setTotalPrice(String.valueOf(price + discount));

        return orderViewModel;
    }

}
