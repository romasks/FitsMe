package ru.fitsme.android.domain.entities.exceptions.user;

public class ClotheNotFoundException extends UserException {

    public ClotheNotFoundException(){}

    public ClotheNotFoundException(String message) {
        super(message);
    }
}
