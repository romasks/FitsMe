package ru.fitsme.android.presentation.fragments.base;

import androidx.fragment.app.Fragment;

import ru.fitsme.android.app.App;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    protected VM viewModel;

    protected BaseFragment() {
        inject(this);
    }

    abstract public void onBackPressed();

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
