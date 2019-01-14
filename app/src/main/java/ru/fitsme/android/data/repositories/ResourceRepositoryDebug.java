package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.IResourceRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;

@Singleton
public class ResourceRepositoryDebug implements IResourceRepository {

    @Inject
    public ResourceRepositoryDebug() {

    }

    @NonNull
    @Override
    public <T extends AppException> String getUserErrorMessage(T appException) {
        return appException.getClass().getSimpleName();
    }
}
