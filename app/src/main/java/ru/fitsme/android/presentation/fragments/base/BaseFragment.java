package ru.fitsme.android.presentation.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.app.App;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected VM viewModel;

    protected BaseFragment() {
        inject(this);
    }

    abstract public void onBackPressed();

    protected <T extends Fragment> void inject(T instance) {
        App.getInstance().getDi().inject(instance);
    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void afterCreateView(View view);
    protected abstract void setUpRecyclers();
    protected abstract void setUpObservers();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(getLayout(), container, false);

        try {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(getVMTypeClass());
            if (savedInstanceState == null) {
                viewModel.init();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        afterCreateView(view);
        setUpRecyclers();
        setUpObservers();

        return view;
    }

    @SuppressWarnings("unchecked")
    private Class<VM> getVMTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString();
            Class<?> clazz = Class.forName(className.replace("class ", ""));
            return (Class<VM>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
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
