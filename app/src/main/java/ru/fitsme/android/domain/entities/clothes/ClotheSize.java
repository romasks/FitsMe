package ru.fitsme.android.domain.entities.clothes;

import com.google.gson.annotations.SerializedName;

public class ClotheSize {
    private int id;
    @SerializedName(value = "int")
    private String international;
    private int ru;
    private int eu;
    private int fr;
    private int it;
    private int us;
    private int uk;
    private String chestLow;
    private String waistLow;
    private String hipsLow;
    private String sleeveLow;
    private String chestHigh;
    private String waistHigh;
    private String hipsHigh;
    private String sleeveHigh;

    public int getId() {
        return id;
    }

    public String getInternational() {
        return international;
    }

    public int getRu() {
        return ru;
    }

    public int getEu() {
        return eu;
    }

    public int getFr() {
        return fr;
    }

    public int getIt() {
        return it;
    }

    public int getUs() {
        return us;
    }

    public int getUk() {
        return uk;
    }

    public Integer getChestLow() {
        Float f = Float.valueOf(chestLow);
        return f.intValue();
    }

    public Integer getWaistLow() {
        Float f = Float.valueOf(waistLow);
        return f.intValue();
    }

    public Integer getHipsLow() {
        Float f = Float.valueOf(hipsLow);
        return f.intValue();
    }

    public Integer getSleeveLow() {
        Float f = Float.valueOf(sleeveLow);
        return f.intValue();
    }

    public Integer getChestHigh() {
        Float f = Float.valueOf(chestHigh);
        return f.intValue();
    }

    public Integer getWaistHigh() {
        Float f = Float.valueOf(waistHigh);
        return f.intValue();
    }

    public Integer getHipsHigh() {
        Float f = Float.valueOf(hipsHigh);
        return f.intValue();
    }

    public Integer getSleeveHigh() {
        Float f = Float.valueOf(sleeveHigh);
        return f.intValue();
    }
}
