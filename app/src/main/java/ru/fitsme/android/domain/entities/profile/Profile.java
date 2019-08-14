package ru.fitsme.android.domain.entities.profile;

public class Profile {
    private String tel;
    private String street;
    private String houseNumber;
    private String apartment;
    private int topSize;
    private int bottomSize;

    public String getTel() {
        return tel;
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

    public int getTopSize() {
        return topSize;
    }

    public int getBottomSize() {
        return bottomSize;
    }
}
