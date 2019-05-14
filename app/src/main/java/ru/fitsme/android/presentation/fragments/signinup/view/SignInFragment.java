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
import ru.fitsme.android.databinding.FragmentSignInBinding;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import ru.fitsme.android.presentation.fragments.signinup.events.SignInBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;

public class SignInFragment extends BaseFragment<SignInViewModel> implements SignInBindingEvents {

    @Inject
    ISignInUpInteractor signInUpInteractor;

    private FragmentSignInBinding binding;
    private LoadingDialog loadingDialog;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadingDialog = new LoadingDialog();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory(signInUpInteractor)).get(SignInViewModel.class);
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
    public void onClickSignIn() {
        KeyboardUtils.hide(getActivity(), binding.getRoot());
        viewModel.onSignIn(binding.getLogin(), binding.getPassword());
    }
}
