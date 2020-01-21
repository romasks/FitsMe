package ru.fitsme.android.domain.boundaries.retunrs;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;

public interface IReturnsActionRepository {
    Single<ReturnsOrderItem> addItemToReturn(ReturnsItemRequest request);
    Single<ReturnsOrderItem> changeReturnsPayment(ReturnsPaymentRequest request);
    Single<ReturnsOrder> getReturnById(int returnId);
    int getReturnOrderStep();
    int getReturnOrderId();
    int getReturnId();
    void setReturnOrderStep(int step);
    void setReturnOrderId(int orderId);
    void setReturnId(int returnId);
}
