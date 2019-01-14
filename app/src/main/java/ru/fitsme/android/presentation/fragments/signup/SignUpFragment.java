package ru.fitsme.android.presentation.fragments.signup;


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


        return binding.getRoot();
    }
}
