package ru.fitsme.android.presentation.fragments.signinup.view;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignUpBinding;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import ru.fitsme.android.presentation.fragments.signinup.events.SignUpBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;

public class SignUpFragment extends BaseFragment<SignUpViewModel> implements SignUpBindingEvents {

    private FragmentSignUpBinding binding;
    private LoadingDialog loadingDialog;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void afterCreateView(View view) {
        loadingDialog = new LoadingDialog();
        binding = FragmentSignUpBinding.bind(view);
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
    public void onClickSignUp() {
        KeyboardUtils.hide(getActivity(), binding.getRoot());
        viewModel.onSignUp(binding.getLogin(), binding.getPassword());
    }
}
