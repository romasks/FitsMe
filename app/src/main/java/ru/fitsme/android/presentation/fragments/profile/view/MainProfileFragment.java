package ru.fitsme.android.presentation.fragments.profile.view;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentProfileMainBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.profile.events.MainProfileBindingEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.MainProfileViewModel;

public class MainProfileFragment extends BaseFragment<MainProfileViewModel> implements MainProfileBindingEvents {

    FragmentProfileMainBinding binding;

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile_main;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentProfileMainBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setUp();
    }

    private void setUp() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(true);
        }
        viewModel.onViewCreated();
    }

    public static MainProfileFragment newInstance() {
        return new MainProfileFragment();
    }

    @Override
    public void onYourSizeClick() {
        viewModel.goToSizeProfile();
    }

    @Override
    public void onYourTypeClick() {

    }

    @Override
    public void onOrdersHistoryClick() {
        viewModel.goToOrderHistoryProfile();
    }

    @Override
    public void onOrdersReturnClick() {
        viewModel.goToOrdersReturn();
    }

    @Override
    public void onLeaveFeedbackClick() {
        viewModel.goToLeaveFeedback();
    }

    @Override
    public void onLogoutClick() {
        viewModel.logout(getActivity());
    }
}
