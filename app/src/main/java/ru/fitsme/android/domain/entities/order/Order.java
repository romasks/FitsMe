package ru.fitsme.android.domain.entities.order;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.fitsme.android.utils.OrderStatus;

public class Order implements Parcelable {
    @SerializedName("id")
    private int orderId;

    @SerializedName("city")
    private String city;

    @SerializedName("street")
    private String street;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("apartment")
    private String apartment;

    @SerializedName("tel")
    private String phoneNumber;

    @SerializedName("created")
    private String orderCreateDate;

    @SerializedName("updated")
    private String orderUpdatedDate;

    @SerializedName("status")
    private OrderStatus orderStatus;

    @SerializedName("orderitems")
    private List<OrderItem> orderItemList;

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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getIndicationNumber() {
        return indicationNumber;
    }

    public void setIndicationNumber(String indicationNumber) {
        this.indicationNumber = indicationNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHiddenCardNumber() {
        String lastQuarter = cardNumber.split("-")[3];
        return "**** **** **** " + lastQuarter;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @SuppressLint("SimpleDateFormat")
    public String orderDate() {
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
        try {
            long orderTime = new SimpleDateFormat("yyyy-MM-dd").parse(orderUpdatedDate).getTime();
            long currentTime = Calendar.getInstance().getTime().getTime();
            return String.valueOf((new Date(currentTime - orderTime)).getDay());
        } catch (ParseException e) {
            return "14";
        }
    }

    public String getOrderSum() {
        int sum = 0;
        for (OrderItem item : orderItemList) {
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
        result = prime * result + getOrderStatus().hashCode();
        result = prime * result + getOrderItemList().hashCode();
        result = prime * result + getIndicationNumber().hashCode();
        result = prime * result + getCardNumber().hashCode();
        return result;
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
        orderStatus = in.readParcelable(OrderStatus.class.getClassLoader());
        in.readList(orderItemList, OrderItem.class.getClassLoader());
        indicationNumber = in.readString();
        cardNumber = in.readString();
    }
}
