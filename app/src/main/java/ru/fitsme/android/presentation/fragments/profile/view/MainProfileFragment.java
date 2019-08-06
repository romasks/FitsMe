package ru.fitsme.android.presentation.fragments.profile.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentProfileBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;
import ru.fitsme.android.presentation.fragments.profile.events.MainProfileBindingEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.MainProfileViewModel;

public class MainProfileFragment extends Fragment implements MainProfileBindingEvents {

    FragmentProfileBinding binding;

    @Inject
    IProfileInteractor interactor;
    private MainProfileViewModel viewModel;

    public MainProfileFragment() {
    }

    public static MainProfileFragment newInstance() {
        return new MainProfileFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(interactor)).get(MainProfileViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
    }

    @Override
    public void onYourSizeClick() {

    }

    @Override
    public void onYourTypeClick() {

    }

    @Override
    public void onOrdersHistoryClick() {

    }

    @Override
    public void onOrdersReturnClick() {

    }

    @Override
    public void onLeaveFeedbackClick() {

    }

    @Override
    public void onLogoutClick() {
        viewModel.logout();
    }
}
