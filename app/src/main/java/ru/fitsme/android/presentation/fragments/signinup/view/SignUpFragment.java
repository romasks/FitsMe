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
import ru.fitsme.android.databinding.FragmentSignUpBinding;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;

public class SignUpFragment extends Fragment {


    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSignUpBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_up, container, false);

        SignUpViewModel signUpViewModel = ViewModelProviders.of(this)
                .get(SignUpViewModel.class);

        binding.btnSignUp.setOnClickListener(v -> {
            signUpViewModel.onSignUp(binding.getLogin(), binding.getPassword());
        });

        signUpViewModel.getFieldsStateLiveData()
                .observe(this, binding::setFieldsState);

        return binding.getRoot();
    }
}
