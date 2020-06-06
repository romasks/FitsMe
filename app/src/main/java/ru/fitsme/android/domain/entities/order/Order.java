package ru.fitsme.android.domain.entities.order;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.utils.OrderStatus;

public class Order implements Parcelable {
    @SerializedName("id")
    private int orderId;

    @SerializedName("city")
    private String city = "";

    @SerializedName("street")
    private String street = "";

    @SerializedName("house_number")
    private String houseNumber = "";

    @SerializedName("apartment")
    private String apartment = "";

    @SerializedName("tel")
    private String phoneNumber = "";

    @SerializedName("created")
    private String orderCreateDate = "";

    @SerializedName("updated")
    private String orderUpdatedDate = "";

    @SerializedName("issued_date")
    private String issuedDate = "";

    @SerializedName("status")
    private OrderStatus orderStatus;

    @SerializedName("orderitems")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Expose
    private String indicationNumber = "";
    @Expose
    private String cardNumber = "";

    public int getOrderId() {
        return orderId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public String getOrderUpdatedDate() {
        return orderUpdatedDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public LocalDateTime getOrderDate() {
        return issuedDate != null
                ? LocalDateTime.parse(issuedDate.replaceAll("Z", ""))
                : null;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getIndicationNumber() {
        return indicationNumber == null ? "" : indicationNumber;
    }

    public void setIndicationNumber(String indicationNumber) {
        this.indicationNumber = indicationNumber;
    }

    public String getCardNumber() {
        return cardNumber == null ? "" : cardNumber;
    }

    public String getHiddenCardNumber() {
        String lastQuarter = cardNumber.split("-")[3];
        return "**** **** **** " + lastQuarter;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @SuppressLint("SimpleDateFormat")
    public String formattedDate() {
        try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(orderUpdatedDate);
            if (dt == null) return orderUpdatedDate;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.format(dt);
        } catch (ParseException e) {
            return orderUpdatedDate;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public String daysForReturn() {
        if (issuedDate == null) return null;

        LocalDateTime orderTime = LocalDateTime.parse(issuedDate.replaceAll("Z", ""));
        LocalDateTime currentTime = LocalDateTime.now();
        long days = 15 - ChronoUnit.DAYS.between(orderTime, currentTime);

        if (days == 1) return "1 день";
        else if (days >= 2 && days <= 4) return days + " дня";
        else if (days > 14) return "Возврат более невозможен";
        else return days + " дней";
    }

    public String getOrderSum() {
        int sum = 0;
        for (OrderItem item : orderItemList) {
            sum += item.getPrice();
        }
        return String.valueOf(sum);
    }

    public String getStatusName() {
        switch (orderStatus.name()) {
            case "FM":
                return "формируется";
            case "ACP":
                return "оформлен";
            case "INP":
                return "собирается";
            case "RDY":
                return "готов к выдаче";
            case "CNC":
                return "отменён";
            case "ISU":
                return "выдан";
            default:
                return "";
        }
    }

    public int getStatusColor() {
        switch (orderStatus.name()) {
            case "FM":
                return R.color.colorStatusFM;
            case "ACP":
                return R.color.colorStatusACP;
            case "INP":
                return R.color.colorStatusINP;
            case "RDY":
                return R.color.colorStatusRDY;
            case "CNC":
                return R.color.colorStatusCNC;
            case "ISU":
                return R.color.colorStatusISU;
            default:
                return R.color.colorStatusFM;
        }
    }

    public String getCount() {
        return String.valueOf(orderItemList.size());
    }

    public String getTotalPrice() {
        int sum = 0;
        for (OrderItem item: orderItemList) {
            sum += item.getPrice();
        }
        return String.valueOf(sum);
    }

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return getOrderId() == that.getOrderId() &&
                getCity().equals(that.getCity()) &&
                getStreet().equals(that.getStreet()) &&
                getHouseNumber().equals(that.getHouseNumber()) &&
                getApartment().equals(that.getApartment()) &&
                getPhoneNumber().equals(that.getPhoneNumber()) &&
                getOrderCreateDate().equals(that.getOrderCreateDate()) &&
                getOrderUpdatedDate().equals(that.getOrderUpdatedDate()) &&
                getIssuedDate().equals(that.getIssuedDate()) &&
                getOrderStatus() == that.getOrderStatus() &&
                getOrderItemList() == that.getOrderItemList() &&
                getIndicationNumber().equals(that.getIndicationNumber()) &&
                getCardNumber().equals(that.getCardNumber());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + getOrderId();
        result = prime * result + getCity().hashCode();
        result = prime * result + getStreet().hashCode();
        result = prime * result + getHouseNumber().hashCode();
        result = prime * result + getApartment().hashCode();
        result = prime * result + getPhoneNumber().hashCode();
        result = prime * result + getOrderCreateDate().hashCode();
        result = prime * result + getOrderUpdatedDate().hashCode();
        result = prime * result + getIssuedDate().hashCode();
        result = prime * result + getOrderStatus().hashCode();
        result = prime * result + getOrderItemList().hashCode();
        result = prime * result + getIndicationNumber().hashCode();
        result = prime * result + getCardNumber().hashCode();
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Order("
                + "id=" + getOrderId()
                + ", city=" + getCity()
                + ", street=" + getStreet()
                + ", houseNumber=" + getHouseNumber()
                + ", apartment=" + getApartment()
                + ", phoneNumber=" + getPhoneNumber()
                + ", orderCreateDate=" + getOrderCreateDate()
                + ", orderUpdatedDate=" + getOrderUpdatedDate()
                + ", issuedDate=" + getIssuedDate()
                + ", orderStatus=" + getOrderStatus()
                + ", orderItemList=" + getOrderItemList()
                + ", indicationNumber=" + getIndicationNumber()
                + ", cardNumber=" + getCardNumber() + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(orderId);
        out.writeString(city);
        out.writeString(street);
        out.writeString(houseNumber);
        out.writeString(apartment);
        out.writeString(phoneNumber);
        out.writeString(orderCreateDate);
        out.writeString(orderUpdatedDate);
        out.writeString(issuedDate);
        out.writeParcelable(orderStatus, flags);
        out.writeList(orderItemList);
        out.writeString(indicationNumber);
        out.writeString(cardNumber);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    private Order(Parcel in) {
        orderId = in.readInt();
        city = in.readString();
        street = in.readString();
        houseNumber = in.readString();
        apartment = in.readString();
        phoneNumber = in.readString();
        orderCreateDate = in.readString();
        orderUpdatedDate = in.readString();
        issuedDate = in.readString();
        orderStatus = in.readParcelable(OrderStatus.class.getClassLoader());
        in.readList(orderItemList, OrderItem.class.getClassLoader());
        indicationNumber = in.readString();
        cardNumber = in.readString();
    }

    public static DiffUtil.ItemCallback<Order> DIFF_CALLBACK = new DiffUtil.ItemCallback<Order>() {

        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getOrderId() == newItem.getOrderId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.equals(newItem);
        }
    };
}
