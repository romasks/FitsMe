package ru.fitsme.android.presentation.common.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

public interface NonNullLiveData<T> {
    void observe(@NonNull LifecycleOwner lifecycleOwner,
                 @NonNull NonNullObserver<T> nonNullObserver);
}
