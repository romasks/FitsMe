package ru.fitsme.android.presentation.fragments.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFiltersBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class FiltersFragment  extends BaseFragment<FiltersViewModel>
implements BackClickListener {

    private FragmentFiltersBinding binding;

    String[] groups;

    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<String>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    ExpandableListView elvMain;

    public static Fragment newInstance() {
        return new FiltersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filters, container, false);
        binding.setBackClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory()).get(FiltersViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        createList();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    private void createList(){
        groups = getResources().getStringArray(R.array.filter_names);

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<String>>();
        childData.add(new ArrayList<>(Arrays.asList(phonesHTC)));
        childData.add(new ArrayList<>(Arrays.asList(phonesSams)));
        childData.add(new ArrayList<>(Arrays.asList(phonesLG)));

        FilterExpandableAdapter adapter = new FilterExpandableAdapter(
                getContext(),
                new ArrayList<>(Arrays.asList(groups)),
                childData
                );

        elvMain = (ExpandableListView) binding.fragmentFilterTypeExLv;
        elvMain.setAdapter(adapter);
    }
}
