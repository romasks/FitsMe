package ru.fitsme.android.presentation.fragments.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import ru.fitsme.android.app.App;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    protected VM viewModel;

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

    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
