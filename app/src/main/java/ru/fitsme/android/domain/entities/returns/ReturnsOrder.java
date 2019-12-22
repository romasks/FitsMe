package ru.fitsme.android.domain.entities.returns;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.fitsme.android.R;

public class ReturnsOrder {

    @SerializedName("id")
    private int id;

    @SerializedName("order")
    private int order;

    @SerializedName("payment_details")
    private String paymentDetails;

    @SerializedName("delivery_details")
    private String deliveryDetails;

    @SerializedName("delivery_details_return")
    private String deliveryDetailsReturn;

    @SerializedName("created")
    private String createdDate;

    @SerializedName("updated")
    private String updatedDate;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("returnitems")
    private List<ReturnsOrderItem> returnItemsList;

    @SerializedName("summ")
    private int summ;

    @SerializedName("count")
    private int count;

    @SerializedName("days_to_return")
    private String daysToReturn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public String getHiddenCardNumber() {
        try {
            String lastQuarter = deliveryDetails.split("-")[3];
            return "**** **** **** " + lastQuarter;
        } catch (ArrayIndexOutOfBoundsException e) {
            return deliveryDetails;
        }
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public String getDeliveryDetailsReturn() {
        return deliveryDetailsReturn;
    }

    public void setDeliveryDetailsReturn(String deliveryDetailsReturn) {
        this.deliveryDetailsReturn = deliveryDetailsReturn;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDate() {
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public String getFormattedDate() {
        try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (dt == null) return date;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.format(dt);
        } catch (ParseException | NullPointerException e) {
            return date;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        switch (status) {
            case "FM":
                return "черновик";
            case "ISU":
                return "в обработке";
            case "CNC":
                return "отказ";
            case "RDY":
                return "выполнено";
            default:
                return "";
        }
    }

    public int getStatusColor() {
        switch (status) {
            case "FM":
                return R.color.colorStatusFM;
            case "ISU":
                return R.color.colorStatusISU;
            case "CNC":
                return R.color.colorStatusCNC;
            case "RDY":
                return R.color.colorStatusRDY;

            default:
                return R.color.colorStatusFM;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReturnsOrderItem> getReturnItemsList() {
        return returnItemsList;
    }

    public void setReturnItemsList(List<ReturnsOrderItem> returnItemsList) {
        this.returnItemsList = returnItemsList;
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDaysToReturn() {
        try {
            int days = Integer.parseInt(daysToReturn);

            String daysStr;
            if (days > 10 && days < 20) daysStr = "дней";
            else if (days % 10 == 1) daysStr = "день";
            else if (days % 10 >= 2 && days % 10 <= 4) daysStr = "дня";
            else daysStr = "дней";

            return days + " " + daysStr;
        } catch (NumberFormatException e) {
            return daysToReturn;
        }
    }

    public void setDaysToReturn(String daysToReturn) {
        this.daysToReturn = daysToReturn;
    }

    public ReturnsOrder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnsOrder)) return false;
        ReturnsOrder that = (ReturnsOrder) o;
        return getId() == that.getId() &&
                getOrder() == that.getOrder() &&
                getPaymentDetails().equals(that.getPaymentDetails()) &&
                getDeliveryDetails().equals(that.getDeliveryDetails()) &&
                getDeliveryDetailsReturn().equals(that.getDeliveryDetailsReturn()) &&
                getCreatedDate().equals(that.getCreatedDate()) &&
                getUpdatedDate().equals(that.getUpdatedDate()) &&
                getDate().equals(that.getDate()) &&
                getReturnItemsList().equals(that.getReturnItemsList()) &&
                getSumm() == that.getSumm() &&
                getCount() == that.getSumm() &&
                getDaysToReturn().equals(that.getDaysToReturn());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + (int) getId();
        result = prime * result + getOrder();
        result = prime * result + getPaymentDetails().hashCode();
        result = prime * result + getDeliveryDetails().hashCode();
        result = prime * result + getDeliveryDetailsReturn().hashCode();
        result = prime * result + getCreatedDate().hashCode();
        result = prime * result + getUpdatedDate().hashCode();
        result = prime * result + getDate().hashCode();
        result = prime * result + getReturnItemsList().hashCode();
        result = prime * result + getSumm();
        result = prime * result + getCount();
        result = prime * result + getDaysToReturn().hashCode();
        return result;
    }

    public static DiffUtil.ItemCallback<ReturnsOrder> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReturnsOrder>() {

        @Override
        public boolean areItemsTheSame(@NonNull ReturnsOrder oldItem, @NonNull ReturnsOrder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReturnsOrder oldItem, @NonNull ReturnsOrder newItem) {
            return oldItem.equals(newItem);
        }
    };
}
