package ru.fitsme.android.presentation.fragments.profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentProfileMainBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.profile.events.MainProfileBindingEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.MainProfileViewModel;

public class MainProfileFragment extends Fragment implements MainProfileBindingEvents {

    FragmentProfileMainBinding binding;

    @Inject
    IProfileInteractor interactor;
    private MainProfileViewModel viewModel;

    public MainProfileFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static MainProfileFragment newInstance() {
        return new MainProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_main, container, false);
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
        viewModel.onViewCreated();
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

    }

    @Override
    public void onOrdersReturnClick() {
        viewModel.goToOrdersReturn();
    }

    @Override
    public void onLeaveFeedbackClick() {

    }

    @Override
    public void onLogoutClick() {
        viewModel.logout();
    }
}
