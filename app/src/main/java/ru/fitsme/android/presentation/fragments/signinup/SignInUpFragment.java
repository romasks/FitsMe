package ru.fitsme.android.presentation.fragments.signinup;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignInUpBinding;

public class SignInUpFragment extends Fragment {

    public static SignInUpFragment newInstance() {
        return new SignInUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignInUpBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_in_up, container, false);

        SignInUpViewModel signInUpViewModel = ViewModelProviders.of(this)
                .get(SignInUpViewModel.class);


        binding.btnSignUp.setOnClickListener(v -> signInUpViewModel.onSignUp());
        binding.btnSignIn.setOnClickListener(v -> signInUpViewModel.onSignIn());

        return binding.getRoot();
    }
}
