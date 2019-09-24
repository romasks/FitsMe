package ru.fitsme.android.domain.entities.profile;

public class Profile {
    private String tel;
    private String street;
    private String houseNumber;
    private String apartment;
    private Integer topSize;
    private Integer bottomSize;

    public Profile(String tel, String street, String houseNumber, String apartment, Integer topSize, Integer bottomSize){
        this.tel = tel;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
    }

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

    public Integer getTopSize() {
        return topSize;
    }

    public Integer getBottomSize() {
        return bottomSize;
    }
}
