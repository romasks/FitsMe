package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.DialogFragmentProfileTopSizeBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.profile.events.TopSizeDialogFragmentEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

public class TopSizeDialogFragment extends DialogFragment
 implements TopSizeDialogFragmentEvents {

    private DialogFragmentProfileTopSizeBinding binding;
    private SizeProfileViewModel viewModel;
    private TopSizeObserver topSizeObserver = new TopSizeObserver();
    private int lastSavedTopSize = -1;

    @Inject
    IProfileInteractor interactor;

    @Inject
    ViewModelFactory viewModelFactory;

    public TopSizeDialogFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static TopSizeDialogFragment newInstance() {
        return new TopSizeDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_profile_top_size, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SizeProfileViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
//        setAdapterToTypeSpinners();
//        setAdapterToTopSizeSpinner();
//        setOnSizeTypeSpinnerClickListener();
//        setOnSizeValueSpinnerClickListener();
        setSizeCheckers();
    }

    private void setSizeCheckers() {
        viewModel.getTopSizeArray().observe(this, list -> {
            TableLayout tableLayout = binding.dialogFragmentProfileSizesTable;
            int numOfColumns = 4;
            int length = list.size();
            int numOfRow = (int) Math.ceil((double) length / numOfColumns);
            int eightDp = convertDpToPixels(8);
            for (int i = 0; i < numOfRow; i++) {
                TableRow tableRow = new TableRow(getContext());
                LinearLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, eightDp);
                tableRow.setLayoutParams(layoutParams);
                tableLayout.addView(tableRow);

                int numColonsInThisIteration;
                if (i == numOfRow - 1){
                    numColonsInThisIteration = length % numOfColumns;
                } else {
                    numColonsInThisIteration = numOfColumns;
                }
                for (int j = 0; j < numColonsInThisIteration; j++) {
                    int index = (numOfColumns * i) + j;
                    String size = list.get(index);
                    SizeButton button = new SizeButton(getContext(), null, R.style.FlatButtonStyle);
                    button.setText(size);
                    button.setId(index);
                    tableRow.addView(button);
                    button.setOnClickListener(view -> {
                        button.toggle();
                    });
                    button.subscribeOnButton(topSizeObserver);
                }
            }
            if (viewModel.getCurrentTopSizeIndex().get() != -1){
                lastSavedTopSize = viewModel.getCurrentTopSizeIndex().get();
                topSizeObserver.checkedSizeIndex = lastSavedTopSize;
                topSizeObserver.setState(lastSavedTopSize, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setMargins();
    }


    private int convertDpToPixels(int dp){
        Resources r = getResources();
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
//
//    private void setAdapterToTypeSpinners() {
//        if (getActivity() == null) return;
//        String[] array = makeSizeTypeArray();
//        TopSizeDialogFragment.SpinnerAdapter<String> adapter = new TopSizeDialogFragment.SpinnerAdapter<>(
//                getActivity(), android.R.layout.simple_spinner_item, array);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.dialogFragmentProfileTopSizeTypeSpinner.setAdapter(adapter);
//    }
//
//    private void setAdapterToTopSizeSpinner() {
//        if (getActivity() == null) return;
//        ArrayList<String> arrayList = new ArrayList<>();
//        TopSizeDialogFragment.SpinnerAdapter<String> adapter = new TopSizeDialogFragment.SpinnerAdapter<>(
//                getActivity(), android.R.layout.simple_spinner_item, arrayList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.dialogFragmentProfileTopSizeValueSpinner.setAdapter(adapter);
//        viewModel.getTopSizeArray().observe(this, list -> {
//            adapter.clear();
//            if (list != null) {
//                if (list.isEmpty()) list.add("");
//                adapter.addAll(list);
//            }
//            adapter.notifyDataSetChanged();
//        });
//    }
//
//    private void setOnSizeValueSpinnerClickListener() {
//        binding.dialogFragmentProfileTopSizeValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                viewModel.onTopSizeValueSpinnerSelected(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    private void setOnSizeTypeSpinnerClickListener() {
//        binding.dialogFragmentProfileTopSizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                viewModel.onTopSizeTypeSpinnerSelected(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

//    private String[] makeSizeTypeArray() {
//        ClotheSizeType[] clotheSizeTypes = ClotheSizeType.values();
//        String[] strings = new String[clotheSizeTypes.length];
//        for (int i = 0; i < clotheSizeTypes.length; i++) {
//            strings[i] = clotheSizeTypes[i].getString();
//        }
//        return strings;
//    }

    private void setMargins() {
        float width = 344.0f;
        float height = 500.0f;

        Resources r = getResources();
        int widthPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                width,
                r.getDisplayMetrics()
        );
        int heightPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                height,
                r.getDisplayMetrics()
        );

        if (getDialog() == null || getDialog().getWindow() == null) return;
        getDialog().getWindow().setLayout(widthPx, heightPx);
    }

    @Override
    public void onOkButtonClicked() {
        getDialog().dismiss();
    }

    @Override
    public void onCancelButtonClicked() {
        if (lastSavedTopSize != -1) {
            viewModel.onTopSizeValueSelected(lastSavedTopSize);
        }
        dismiss();
        viewModel.goBack();
    }

//    private class SpinnerAdapter<T> extends ArrayAdapter<T> {
//
//        SpinnerAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
//            super(context, resource, objects);
//        }
//
//        SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
//            super(context, resource, objects);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            View view = super.getView(position, convertView, parent);
//            view.setPadding(0, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
//            return view;
//        }
//    }

    class TopSizeObserver {
        private SparseArray<SizeButton> buttons = new SparseArray<>();
        private int checkedSizeIndex = -1;

        void setState(int id, Boolean isChecked) {
            if (isChecked){
                if (checkedSizeIndex != -1) {
                    SizeButton button = buttons.get(checkedSizeIndex);
                    if (button != null) {
                        button.setChecked(false);
                    }
                }
                checkedSizeIndex = id;
                SizeButton button = buttons.get(id);
                if (button != null) {
                    button.setChecked(true);
                }
                viewModel.onTopSizeValueSelected(id);
            } else {
                checkedSizeIndex = -1;
                SizeButton button = buttons.get(id);
                if (button != null) {
                    buttons.get(id).setChecked(false);
                }
            }
        }

        void addButton(int id, SizeButton button){
            buttons.put(id, button);
        }

        void resetButton(int id) {
            buttons.remove(id);
        }
    }
}
