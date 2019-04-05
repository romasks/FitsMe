package ru.fitsme.android.utils;

public enum OrderStatus {
    FM("FM"),
    ACP("ACP"),
    INP("INP"),
    RDY("RDY"),
    CNC("CNC"),
    ISU("ISU");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public boolean equalsStatus(String compareStatus) {
        return this.status.equals(compareStatus);
    }


    @Override
    public String toString() {
        return this.status;
    }
}
