package ru.fitsme.android.presentation.common.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public interface NonNullLiveData<T> {
    void observe(@NonNull LifecycleOwner lifecycleOwner,
                 @NonNull NonNullObserver<T> nonNullObserver);
}
