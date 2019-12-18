package ru.fitsme.android.presentation.fragments.filters;

import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.Fragment;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFiltersBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class FiltersFragment extends BaseFragment<FiltersViewModel>
        implements BackClickListener {

    private FragmentFiltersBinding binding;

    String[] groups;

    String[] phonesHTC = new String[]{"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[]{"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[]{"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<String>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    ExpandableListView elvMain;

    public static Fragment newInstance() {
        return new FiltersFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_filters;
    }

    @Override
    protected void afterCreateView(View view) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        }
        binding = FragmentFiltersBinding.bind(view);
        binding.setBackClickListener(this);
        setUp();
    }

    private void setUp() {
        createList();
    }

    @Override
    protected void setUpRecyclers() {

    }

    @Override
    protected void setUpObservers() {

    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    private void createList() {
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
