package ru.fitsme.android.presentation.fragments.splash;

import android.os.Bundle;
import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSplashBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class SplashFragment extends BaseFragment<SplashViewModel> {

    FragmentSplashBinding binding;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentSplashBinding.bind(view);
    }
}
