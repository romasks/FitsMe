package ru.fitsme.android.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public enum ReturnsOrderStep implements Parcelable {
    HOW_TO(1),
    CHOOSE_ORDER(2),
    CHOOSE_ITEMS(3),
    INDICATE_NUMBER(4),
    BILLING_INFO(5),
    VERIFY_DATA(6);

    private int step;

    ReturnsOrderStep() {
    }

    ReturnsOrderStep(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public static ReturnsOrderStep getByStepNumber(int step) {
        return values()[step];
    }

    public boolean before(ReturnsOrderStep comparedObject) {
        return this.step < comparedObject.step;
    }

    @NotNull
    @Override
    public String toString() {
        return String.valueOf(this.step);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
        dest.writeInt(step);
    }

    public static final Parcelable.Creator<ReturnsOrderStep> CREATOR = new Parcelable.Creator<ReturnsOrderStep>() {
        public ReturnsOrderStep createFromParcel(Parcel in) {
            ReturnsOrderStep returnsOrderStep = ReturnsOrderStep.values()[in.readInt()];
            returnsOrderStep.step = in.readInt();
            return returnsOrderStep;
        }

        public ReturnsOrderStep[] newArray(int size) {
            return new ReturnsOrderStep[size];
        }
    };
}
