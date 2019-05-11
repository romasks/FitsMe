package ru.fitsme.android.presentation.common.base;

import android.support.v4.app.Fragment;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public abstract class BaseFragment<VM extends BaseViewModel, IT extends BaseInteractor> extends Fragment {

    protected VM viewModel;
    protected @Inject
    IT interactor;

    protected BaseFragment() {
        inject(this);
    }

    protected <T extends Fragment> void inject(T instance) {
        App.getInstance().getDi().inject(instance);
    }

    @Override
    public void onDestroyView() {
        viewModel.clearDisposables();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        viewModel.disposeDisposables();
        super.onDestroy();
    }
}
