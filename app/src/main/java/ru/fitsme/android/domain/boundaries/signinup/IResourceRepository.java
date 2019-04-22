package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.exceptions.AppException;

public interface IResourceRepository {
    @NonNull
    <T extends AppException> String getUserErrorMessage(T appException);
}
