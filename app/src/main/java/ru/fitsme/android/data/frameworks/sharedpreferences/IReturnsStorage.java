package ru.fitsme.android.data.frameworks.sharedpreferences;

import ru.fitsme.android.utils.ReturnsOrderStep;

public interface IReturnsStorage {
    ReturnsOrderStep getReturnOrderStep();
    int getReturnId();
    int getReturnOrderId();
    void setReturnOrderStep(ReturnsOrderStep step);
    void setReturnId(int returnId);
    void setReturnOrderId(int orderId);
}
