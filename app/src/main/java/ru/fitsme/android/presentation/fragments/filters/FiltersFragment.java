package ru.fitsme.android.presentation.fragments.filters;

import android.util.SparseArray;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFiltersBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheFilter;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

public class FiltersFragment extends BaseFragment<FiltersViewModel>
        implements BackClickListener {

    public static final int PRODUCT_NAME_NUMBER = 0;
    public static final int BRAND_NAME_NUMBER = 1;
    public static final int COLOR_NUMBER = 2;

    private FragmentFiltersBinding binding;

    private SparseArray<ArrayList<ClotheFilter>> filters = new SparseArray<>();

    private ExpandableListView elvMain;
    private FilterExpandableAdapter adapter;

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
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new FilterExpandableAdapter(getContext(), filters);
        elvMain = (ExpandableListView) binding.fragmentFilterTypeExLv;
        elvMain.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getProductNames().observe(this, new Observer<List<FilterProductName>>() {
            @Override
            public void onChanged(List<FilterProductName> productNameList) {
                filters.put(PRODUCT_NAME_NUMBER, (ArrayList) productNameList);
                adapter.swap(filters);
            }
        });
        viewModel.getBrands().observe(this, new Observer<List<FilterBrand>>() {
            @Override
            public void onChanged(List<FilterBrand> brandList) {
                filters.put(BRAND_NAME_NUMBER, (ArrayList) brandList);
                Timber.d("brandList size: %s", brandList.size());
                adapter.swap(filters);
            }
        });
        viewModel.getColors().observe(this, list -> {
            FilterColorListWrapper wrapper = new FilterColorListWrapper(list);
            ArrayList<ClotheFilter> listWrappers = new ArrayList<>();
            listWrappers.add(wrapper);
            filters.put(COLOR_NUMBER, listWrappers);
            adapter.swap(filters);
        });
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }


    class FilterColorListWrapper implements ClotheFilter{

        private ArrayList<FilterColor> filterColorArrayList;

        FilterColorListWrapper(List filterColorArrayList){
            this.filterColorArrayList = (ArrayList) filterColorArrayList;
        }

        ArrayList<FilterColor> getColorList(){
            return filterColorArrayList;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public boolean isChecked() {
            return false;
        }
    }
}
