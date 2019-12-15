package ru.fitsme.android.data.frameworks.sharedpreferences;

public interface IReturnsStorage {
    int getReturnOrderStep();
    int getReturnId();
    int getReturnOrderId();
    void setReturnOrderStep(int step);
    void setReturnId(int returnId);
    void setReturnOrderId(int orderId);
}
