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

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.DialogFragmentProfileBottomSizeBinding;
import ru.fitsme.android.databinding.DialogFragmentProfileTopSizeBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.profile.events.SizeDialogFragmentEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

public class BottomSizeDialogFragment extends DialogFragment
 implements SizeDialogFragmentEvents {

    private DialogFragmentProfileBottomSizeBinding binding;
    private SizeProfileViewModel viewModel;
    private BottomSizeObserver bottomSizeObserver = new BottomSizeObserver();
    private int lastSavedBottomSize = -1;

    @Inject
    IProfileInteractor interactor;

    @Inject
    ViewModelFactory viewModelFactory;

    public BottomSizeDialogFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static BottomSizeDialogFragment newInstance() {
        return new BottomSizeDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_profile_bottom_size, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SizeProfileViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setSizeCheckers();
    }

    private void setSizeCheckers() {
        viewModel.getBottomSizeArray().observe(this, list -> {
            TableLayout tableLayout = binding.dialogFragmentProfileBottomSizesTable;
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
                    button.setCallback(bottomSizeObserver);
                }
            }
            if (viewModel.getCurrentBottomSizeIndex().get() != -1){
                lastSavedBottomSize = viewModel.getCurrentBottomSizeIndex().get();
                bottomSizeObserver.checkedSizeIndex = lastSavedBottomSize;
                bottomSizeObserver.setState(lastSavedBottomSize, true);
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
        dismiss();
    }

    @Override
    public void onCancelButtonClicked() {
        if (lastSavedBottomSize != -1) {
            viewModel.onBottomSizeValueSelected(lastSavedBottomSize);
        }
        dismiss();
    }

    class BottomSizeObserver implements SizeButton.OnSizeButtonClickCallback{
        private SparseArray<SizeButton> buttons = new SparseArray<>();
        private int checkedSizeIndex = -1;

        @Override
        public void setState(int id, Boolean isChecked) {
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
                viewModel.onBottomSizeValueSelected(id);
            } else {
                checkedSizeIndex = -1;
                SizeButton button = buttons.get(id);
                if (button != null) {
                    buttons.get(id).setChecked(false);
                }
            }
        }

        @Override
        public void addButton(int id, SizeButton button){
            buttons.put(id, button);
        }
    }
}
