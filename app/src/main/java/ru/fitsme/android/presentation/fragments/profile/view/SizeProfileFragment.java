package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.databinding.FragmentProfileChangeSizeBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.profile.events.SizeProfileBindingEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

public class SizeProfileFragment extends BaseFragment<SizeProfileViewModel> implements SizeProfileBindingEvents {

    private FragmentProfileChangeSizeBinding binding;

    public static SizeProfileFragment newInstance() {
        return new SizeProfileFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile_change_size;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentProfileChangeSizeBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setUp();
    }

    private void setUp() {
        setAdapterToTypeSpinners();
        setAdapterToTopSizeSpinner();
        setAdapterToBottomSizeSpinner();
        setOnSizeTypeSpinnersClickListeners();
        setOnSizeValueSpinnersClickListeners();
    }

    private void setAdapterToTypeSpinners() {
        if (getActivity() == null) return;
        String[] array = makeSizeTypeArray();
        SpinnerAdapter<String> adapter = new SpinnerAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentChangeSizeTopSizeLayoutSizeTypeSpinner.setAdapter(adapter);
        binding.fragmentChangeSizeBottomSizeLayoutSizeTypeSpinner.setAdapter(adapter);
    }

    private void setAdapterToTopSizeSpinner() {
        if (getActivity() == null) return;
        ArrayList<String> arrayList = new ArrayList<>();
        SpinnerAdapter<String> adapter = new SpinnerAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentChangeSizeTopSizeLayoutSizeValueSpinner.setAdapter(adapter);
        viewModel.getTopSizeArray().observe(this, list -> {
            adapter.clear();
            if (list != null) {
                if (list.isEmpty()) list.add("");
                adapter.addAll(list);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void setAdapterToBottomSizeSpinner() {
        if (getActivity() == null) return;
        ArrayList<String> arrayList = new ArrayList<>();
        SpinnerAdapter<String> adapter = new SpinnerAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fragmentChangeSizeBottomSizeLayoutSizeValueSpinner.setAdapter(adapter);
        viewModel.getBottomSizeArray().observe(this, list -> {
            adapter.clear();
            if (list != null) {
                if (list.isEmpty()) list.add("");
                adapter.addAll(list);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void setOnSizeValueSpinnersClickListeners() {
        binding.fragmentChangeSizeTopSizeLayoutSizeValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onTopSizeValueSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.fragmentChangeSizeBottomSizeLayoutSizeValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onBottomSizeValueSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setOnSizeTypeSpinnersClickListeners() {
        binding.fragmentChangeSizeTopSizeLayoutSizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onTopSizeTypeSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.fragmentChangeSizeBottomSizeLayoutSizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onBottomSizeTypeSpinnerSelected(position);
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

    private String[] makeSizeTypeArray() {
        ClotheSizeType[] clotheSizeTypes = ClotheSizeType.values();
        String[] strings = new String[clotheSizeTypes.length];
        for (int i = 0; i < clotheSizeTypes.length; i++) {
            strings[i] = clotheSizeTypes[i].getString();
        }
        return strings;
    }

    private class SpinnerAdapter<T> extends ArrayAdapter<T> {

        SpinnerAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
            super(context, resource, objects);
        }

        SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            view.setPadding(0, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            return view;
        }
    }
}
