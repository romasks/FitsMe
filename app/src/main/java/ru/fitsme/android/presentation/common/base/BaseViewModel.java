package ru.fitsme.android.presentation.common.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel implements IViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void clearDisposables() {
        compositeDisposable.clear();
    }

    @Override
    public void disposeDisposables() {
        compositeDisposable.dispose();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
