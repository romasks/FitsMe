package ru.fitsme.android.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public enum OrderStatus implements Parcelable {
    FM("FM"),
    ACP("ACP"),
    INP("INP"),
    RDY("RDY"),
    CNC("CNC"),
    ISU("ISU");

    private String status;

    OrderStatus() {
    }

    OrderStatus(String status) {
        this.status = status;
    }

    public boolean equalsStatus(String compareStatus) {
        return this.status.equals(compareStatus);
    }

    @NotNull
    @Override
    public String toString() {
        return this.status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ordinal());
        out.writeString(status);
    }

    public static final Parcelable.Creator<OrderStatus> CREATOR = new Parcelable.Creator<OrderStatus>() {
        public OrderStatus createFromParcel(Parcel in) {
            OrderStatus orderStatus = OrderStatus.values()[in.readInt()];
            orderStatus.status = in.readString();
            return orderStatus;
        }

        public OrderStatus[] newArray(int size) {
            return new OrderStatus[size];
        }
    };
}
