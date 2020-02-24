package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.DialogFragmentProfileBottomSizeBinding;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.profile.events.SizeDialogFragmentEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

public class BottomSizeDialogFragment extends DialogFragment
 implements SizeDialogFragmentEvents, SizeObserver.Callback {

    private DialogFragmentProfileBottomSizeBinding binding;
    private SizeProfileViewModel viewModel;
    private SizeObserver sizeObserver = new SizeObserver(this, 0);
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
            TableLayout tableLayout = binding.dialogFragmentBottomSizeLayout.bottomSizeProfileBottomSizesTable;
            if (getContext() != null) {
                TableFiller.fillButtons(getContext(), sizeObserver, list, tableLayout);
            }
            if (viewModel.getCurrentBottomSizeIndex().get() != -1){
                lastSavedBottomSize = viewModel.getCurrentBottomSizeIndex().get();
                sizeObserver.setCheckedSizeIndex(lastSavedBottomSize);
                sizeObserver.setState(lastSavedBottomSize, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setMargins();
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

    @Override
    public void onSizeValueSelected(int tag, int id) {
        viewModel.onBottomSizeValueSelected(id);
    }
}
