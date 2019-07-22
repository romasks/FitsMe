package ru.fitsme.android.presentation.fragments.base;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;

public abstract class BaseViewModel extends ViewModel implements IViewModel {

    @Inject
    protected Navigation navigation;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected <T extends ViewModel> void inject(T instance) {
        App.getInstance().getDi().inject(instance);
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (App.getInstance().isOnline()) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void clearDisposables() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void disposeDisposables() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
