package ru.fitsme.android.domain.entities.returns;

import java.util.List;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ReturnsItem {
    private int id;
    private long number;
    private String status;
    private String date;
    private int amount;
    private int price;
    private String calculationMethod;
    private int daysForReturn;
    private boolean inCart;
    private List<ClothesItem> items;

    ReturnsItem() {
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

    public String getDate() {
        return date;
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
                getItems().equals(that.getItems());
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
        return result;
    }
}
