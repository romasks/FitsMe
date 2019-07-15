package ru.fitsme.android.domain.boundaries.auth;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.AppException;

public interface IResourceRepository {
    @NonNull
    <T extends AppException> String getUserErrorMessage(T appException);
}
