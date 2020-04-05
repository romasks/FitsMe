package ru.fitsme.android.presentation.fragments.base;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;
import ru.fitsme.android.presentation.main.viewmodel.MainViewModel;

public abstract class BaseViewModel extends ViewModel implements IViewModel {

    @Inject
    protected MainNavigation navigation;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected <T extends ViewModel> void inject(T instance) {
        App.getInstance().getDi().inject(instance);
    }

    protected void init() {}

    @Override
    public void addDisposable(Disposable disposable) {
        if (MainViewModel.isOnline.get()) {
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
        clearDisposables();
    }

    public void onBackPressed() {
        navigation.goBack();
    }
}
