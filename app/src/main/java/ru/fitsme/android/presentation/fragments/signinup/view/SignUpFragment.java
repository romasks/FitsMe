package ru.fitsme.android.presentation.fragments.signinup.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignUpBinding;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import ru.fitsme.android.presentation.fragments.signinup.events.SignUpBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;

public class SignUpFragment extends BaseFragment<SignUpViewModel> implements SignUpBindingEvents {

    @Inject
    ISignInteractor signInteractor;

    private FragmentSignUpBinding binding;
    private LoadingDialog loadingDialog;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadingDialog = new LoadingDialog();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(signInteractor)).get(SignUpViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.getFieldsStateLiveData()
                .observe(this, this::onStateChanged);
    }

    private void onStateChanged(SignInUpState signInUpState) {
        binding.setSignInUpState(signInUpState);

        /*if (signInUpState.isLoading()) {
            loadingDialog.show(getContext());
        } else {
            loadingDialog.hide();
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        loadingDialog.hide();
    }

    @Override
    public void onClickSignUp() {
        KeyboardUtils.hide(getActivity(), binding.getRoot());
        viewModel.onSignUp(binding.getLogin(), binding.getPassword());
    }
}
