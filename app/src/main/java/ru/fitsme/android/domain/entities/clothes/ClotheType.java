package ru.fitsme.android.domain.entities.clothes;

import android.os.Parcel;
import android.os.Parcelable;

public class ClotheType implements Parcelable {
    private String title;
    private String type;

    public String getTitle() {
        return title;
    }

    public Type getType() {
        switch (type) {
            case "TOP": {
                return Type.TOP;
            }
            case "BOT": {
                return Type.BOTTOM;
            }
            default:
                throw new IllegalStateException("Unknown Type ClotheType state");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClotheType)) return false;
        ClotheType picture = (ClotheType) o;
        return getTitle().equals(picture.getTitle()) &&
                getType().equals(picture.getType());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + title.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(type);
    }

    public static final Parcelable.Creator<ClotheType> CREATOR = new Parcelable.Creator<ClotheType>() {
        public ClotheType createFromParcel(Parcel in) {
            return new ClotheType(in);
        }

        public ClotheType[] newArray(int size) {
            return new ClotheType[size];
        }
    };

    private ClotheType(Parcel in) {
        title = in.readString();
        type = in.readString();
    }

    public enum Type {
        TOP, BOTTOM
    }
}
