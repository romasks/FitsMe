package ru.fitsme.android.presentation.common.livedata;

import androidx.annotation.NonNull;

public interface NonNullObserver<T> {
    void onChanged(@NonNull T data);
}
