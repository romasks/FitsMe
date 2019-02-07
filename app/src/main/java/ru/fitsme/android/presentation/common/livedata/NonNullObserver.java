package ru.fitsme.android.presentation.common.livedata;

import android.support.annotation.NonNull;

public interface NonNullObserver<T> {
    void onChanged(@NonNull T data);
}
