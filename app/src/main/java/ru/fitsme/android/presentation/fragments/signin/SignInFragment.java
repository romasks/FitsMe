package ru.fitsme.android.presentation.fragments.signin;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment {

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignInBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_in, container, false);

        SignInViewModel signInViewModel = ViewModelProviders.of(this)
                .get(SignInViewModel.class);


        return binding.getRoot();
    }
}
