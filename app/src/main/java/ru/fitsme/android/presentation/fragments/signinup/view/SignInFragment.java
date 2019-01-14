package ru.fitsme.android.presentation.fragments.signinup.view;


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
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;

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

        binding.btnSignIn.setOnClickListener(v -> {
            signInViewModel.onSignIn(binding.getLogin(), binding.getPassword());
        });

        signInViewModel.getFieldsStateLiveData()
                .observe(this, binding::setSignInUpState);


        return binding.getRoot();
    }
}
