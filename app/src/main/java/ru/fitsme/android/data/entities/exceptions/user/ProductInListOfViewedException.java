package ru.fitsme.android.data.entities.exceptions.user;

public class ProductInListOfViewedException extends UserException {

    public static final int CODE = 100012;

    public ProductInListOfViewedException(){}

    public ProductInListOfViewedException(String message) {
        super(message);
    }
}
