package ru.fitsme.android.presentation.fragments.signinup.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignInUpBinding;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.signinup.events.SignInUpBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInUpViewModel;

public class SignInUpFragment extends BaseFragment<SignInUpViewModel> implements SignInUpBindingEvents {

    @Inject
    IAuthInteractor authInteractor;

    private FragmentSignInUpBinding binding;

    public static SignInUpFragment newInstance() {
        return new SignInUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in_up, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(authInteractor)).get(SignInUpViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
    }

    @Override
    public void onClickSignUp() {
        viewModel.onSignUp();
    }

    @Override
    public void onClickSignIn() {
        viewModel.onSignIn();
    }

    @Override
    public void onBackPressed() {

    }
}
