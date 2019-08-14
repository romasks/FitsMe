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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.databinding.FragmentChangeSizeProfileBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.profile.events.SizeProfileBindingEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

public class SizeProfileFragment extends Fragment implements SizeProfileBindingEvents {

    FragmentChangeSizeProfileBinding binding;

    @Inject
    IProfileInteractor interactor;
    private SizeProfileViewModel viewModel;

    public SizeProfileFragment(){
        App.getInstance().getDi().inject(this);
    }

    public static SizeProfileFragment newInstance() {

        Bundle args = new Bundle();

        SizeProfileFragment fragment = new SizeProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_size_profile, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(interactor)).get(SizeProfileViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
        setAdapter();
        setOnClickListeners();
    }

    private void setAdapter() {
        String[] array = makeArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentChangeSizeTopSizeLayoutSizeTypeSpinner.setAdapter(adapter);
        binding.fragmentChangeSizeBottomSizeLayoutSizeTypeSpinner.setAdapter(adapter);
    }

    private void setOnClickListeners() {
        binding.fragmentChangeSizeTopSizeLayoutSizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onTopSizeItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.fragmentChangeSizeBottomSizeLayoutSizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onBottomSizeItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void goBack() {
        viewModel.goBack();
    }

    private String[] makeArray(){
        ClotheSizeType[] clotheSizeTypes = ClotheSizeType.values();
        String[] strings = new String[clotheSizeTypes.length];
        for (int i = 0; i < clotheSizeTypes.length; i++) {
            strings[i] = clotheSizeTypes[i].getString();
        }
        return strings;
    }
}
