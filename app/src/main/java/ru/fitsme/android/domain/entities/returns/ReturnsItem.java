package ru.fitsme.android.domain.entities.returns;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ReturnsItem implements Parcelable {
    private int id;
    private long number;
    private String status;
    private String date = "";
    private int amount;
    private int price;
    private String calculationMethod;
    private int daysForReturn;
    private boolean inCart;
    private List<ClothesItem> items;
    private String indicationNumber = "";
    private String cardNumber = "";

    public ReturnsItem() {
    }

    public ReturnsItem(long number, String status, String date, int amount, int price,
                       String calculationMethod, int daysForReturn, boolean inCart, List<ClothesItem> items) {
        this.number = number;
        this.status = status;
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.calculationMethod = calculationMethod;
        this.daysForReturn = daysForReturn;
        this.inCart = inCart;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public long getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate() {
        try {
            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (dt == null) return date;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.format(dt);
        } catch (ParseException e) {
            return date;
        }
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public int getDaysForReturn() {
        return daysForReturn;
    }

    public boolean isInCart() {
        return inCart;
    }

    public List<ClothesItem> getItems() {
        return items;
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

    public String getStatusName() {
        switch (status) {
            case "FM": return "черновик";
            case "ISU": return "в обработке";
            case "CNC": return "отказ";
            case "RDY": return "выполнено";
            default: return "";
        }
    }

    public int getStatusColor() {
        switch (status) {
            case "FM": return R.color.colorStatusFM;
            case "ISU": return R.color.colorStatusISU;
            case "CNC": return R.color.colorStatusCNC;
            case "RDY": return R.color.colorStatusRDY;

            default: return R.color.colorStatusFM;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnsItem)) return false;
        ReturnsItem that = (ReturnsItem) o;
        return getId() == that.getId() &&
                getNumber() == that.getNumber() &&
                getAmount() == that.getAmount() &&
                getPrice() == that.getPrice() &&
                getDaysForReturn() == that.getDaysForReturn() &&
                isInCart() == that.isInCart() &&
                getStatus().equals(that.getStatus()) &&
                getCalculationMethod().equals(that.getCalculationMethod()) &&
                getDate().equals(that.getDate()) &&
                getItems().equals(that.getItems()) &&
                getIndicationNumber().equals(that.getIndicationNumber()) &&
                getCardNumber().equals(that.getCardNumber());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        result = 31 * result + (int) getNumber();
        result = 31 * result + getAmount();
        result = 31 * result + getPrice();
        result = 31 * result + getDaysForReturn();
        result = 31 * result + (isInCart() ? 1231 : 1237);
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getCalculationMethod().hashCode();
        result = 31 * result + getItems().hashCode();
        result = 31 * result + getIndicationNumber().hashCode();
        result = 31 * result + getCardNumber().hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeLong(number);
        out.writeString(status);
        out.writeString(date);
        out.writeInt(amount);
        out.writeInt(price);
        out.writeString(calculationMethod);
        out.writeInt(daysForReturn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            out.writeBoolean(inCart);
        } else {
            out.writeByte((byte) (inCart ? 1 : 0));
        }
        out.writeList(items);
        out.writeString(indicationNumber);
        out.writeString(cardNumber);
    }

    public static final Parcelable.Creator<ReturnsItem> CREATOR = new Parcelable.Creator<ReturnsItem>() {
        public ReturnsItem createFromParcel(Parcel in) {
            return new ReturnsItem(in);
        }

        public ReturnsItem[] newArray(int size) {
            return new ReturnsItem[size];
        }
    };

    private ReturnsItem(Parcel in) {
        id = in.readInt();
        number = in.readLong();
        status = in.readString();
        date = in.readString();
        amount = in.readInt();
        price = in.readInt();
        calculationMethod = in.readString();
        daysForReturn = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            inCart = in.readBoolean();
        } else {
            inCart = in.readByte() != 0;
        }
        in.readList(items, ClothesItem.class.getClassLoader());
        indicationNumber = in.readString();
        cardNumber = in.readString();
    }
}
