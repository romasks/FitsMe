package ru.fitsme.android.domain.boundaries.retunrs;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.order.ReturnsOrder;

public interface IReturnsActionRepository {
    Single<ReturnsOrder> addItemToReturn(ReturnsItemRequest request);
    Single<ReturnsOrder> changeReturnsPayment(ReturnsPaymentRequest request);
    Single<ReturnsOrder> getReturnById(int returnId);
}
