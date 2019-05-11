package ru.fitsme.android.presentation.common.base;

import io.reactivex.disposables.Disposable;

public interface IViewModel {
    void addDisposable(Disposable disposable);
    void clearDisposables();
    void disposeDisposables();
}
