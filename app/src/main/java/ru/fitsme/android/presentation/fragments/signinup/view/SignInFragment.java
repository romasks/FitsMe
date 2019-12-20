package ru.fitsme.android.presentation.fragments.signinup.view;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignInBinding;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import ru.fitsme.android.presentation.fragments.signinup.events.SignInBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;

public class SignInFragment extends BaseFragment<SignInViewModel> implements SignInBindingEvents {

    private FragmentSignInBinding binding;
    private LoadingDialog loadingDialog;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void afterCreateView(View view) {
        loadingDialog = new LoadingDialog();
        binding = FragmentSignInBinding.bind(view);
        binding.setBindingEvents(this);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getFieldsStateLiveData().observe(getViewLifecycleOwner(), this::onStateChanged);
    }

    private void onStateChanged(SignInUpState signInUpState) {
        binding.setSignInUpState(signInUpState);
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
