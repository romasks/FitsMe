package ru.fitsme.android.data.frameworks.retrofit.entities;

public class OkResponse<T> {
    private T response;
    private Error error;

    public T getResponse() {
        return response;
    }

    public Error getError() {
        return error;
    }
}
