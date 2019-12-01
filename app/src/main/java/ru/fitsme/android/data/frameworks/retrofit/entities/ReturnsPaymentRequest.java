package ru.fitsme.android.data.frameworks.retrofit.entities;

public class ReturnsPaymentRequest {
    private long returnId;
    private String payment_details;
    private String delivery_details;
    private String status;

    public ReturnsPaymentRequest(int returnId, String orderitems_id, String delivery_details, String quantity) {
        this.returnId = returnId;
        this.payment_details = orderitems_id;
        this.delivery_details = delivery_details;
        this.status = quantity;
    }

    public long getReturnId() {
        return returnId;
    }

    public String getPaymentDetails() {
        return payment_details;
    }

    public String getDeliveryDetails() {
        return delivery_details;
    }

    public String getStatus() {
        return status;
    }
}
