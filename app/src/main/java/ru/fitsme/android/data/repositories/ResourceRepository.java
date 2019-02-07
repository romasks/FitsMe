package ru.fitsme.android.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.boundaries.IResourceRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.internal.ServerInternalException;
import ru.fitsme.android.domain.entities.exceptions.user.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.user.LoginOrPasswordNotValidException;
import ru.fitsme.android.domain.entities.exceptions.user.PasswordIncorrectException;

@Singleton
public class ResourceRepository implements IResourceRepository {

    private Context context;

    @Inject
    public ResourceRepository(Context appContext) {
        this.context = appContext;
    }

    @NonNull
    @Override
    public <T extends AppException> String getUserErrorMessage(T appException) {
        int id = R.string.app_common_error;
        if (appException instanceof InternetConnectionException) {
            id = R.string.internet_connection_error;
        } else if (appException instanceof LoginAlreadyInUseException) {
            id = R.string.login_already_in_use_error;
        } else if (appException instanceof LoginIncorrectException) {
            id = R.string.login_incorrect_error;
        } else if (appException instanceof LoginOrPasswordNotValidException) {
            id = R.string.login_or_password_error;
        } else if (appException instanceof PasswordIncorrectException) {
            id = R.string.password_incorrect_error;
        } else if (appException instanceof ServerInternalException) {
            id = R.string.server_internal_error;
        }
        return context.getResources().getString(id);
    }
}
