package ru.fitsme.android.presentation.fragments.signinup;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;

public class SignInUpFragment extends Fragment {

    public static Fragment newInstance() {
        return new SignInUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in_up, container, false);

        SignInUpViewModel signInUpViewModel = ViewModelProviders.of(this)
                .get(SignInUpViewModel.class);

        return view;
    }
}
