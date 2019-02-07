package ru.fitsme.android.presentation.common.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class NonNullMutableLiveData<T> implements NonNullLiveData<T> {
    private MutableLiveData<T> mutableLiveData = new MutableLiveData<>();

    @Override
    public void observe(@NonNull LifecycleOwner lifecycleOwner,
                        @NonNull NonNullObserver<T> nonNullObserver) {
        mutableLiveData.observe(lifecycleOwner, data -> {
            if (data == null) {
                throw new RuntimeException("LiveData can't take null object");
            }
            nonNullObserver.onChanged(data);
        });
    }

    public void setValue(@NonNull T value) {
        mutableLiveData.setValue(value);
    }

    public void postValue(@NonNull T value) {
        mutableLiveData.postValue(value);
    }

}
