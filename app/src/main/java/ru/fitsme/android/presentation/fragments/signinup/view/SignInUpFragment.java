package ru.fitsme.android.presentation.fragments.signinup.view;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentSignInUpBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.signinup.events.SignInUpBindingEvents;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInUpViewModel;

public class SignInUpFragment extends BaseFragment<SignInUpViewModel> implements SignInUpBindingEvents {

    private FragmentSignInUpBinding binding;

    public static SignInUpFragment newInstance() {
        return new SignInUpFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_in_up;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentSignInUpBinding.bind(view);
        binding.setBindingEvents(this);
    }

    @Override
    public void onClickSignUp() {
        viewModel.onSignUp();
    }

    @Override
    public void onClickSignIn() {
        viewModel.onSignIn();
    }
}
