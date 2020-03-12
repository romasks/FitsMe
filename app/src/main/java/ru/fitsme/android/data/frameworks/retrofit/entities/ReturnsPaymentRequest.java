package ru.fitsme.android.data.frameworks.retrofit.entities;

public class ReturnsPaymentRequest {
    private long returnId;
    private String delivery_details;
    private String payment_details;
    private String status;

    public ReturnsPaymentRequest(int returnId, String delivery_details, String payment_details, String quantity) {
        this.returnId = returnId;
        this.delivery_details = delivery_details;
        this.payment_details = payment_details;
        this.status = quantity;
    }

    public long getReturnId() {
        return returnId;
    }

    public String getDeliveryDetails() {
        return delivery_details;
    }

    public String getPaymentDetails() {
        return payment_details;
    }

    public String getStatus() {
        return status;
    }
}
