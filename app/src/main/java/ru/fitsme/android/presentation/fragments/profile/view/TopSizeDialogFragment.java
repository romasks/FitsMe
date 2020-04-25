package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

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
import ru.fitsme.android.presentation.fragments.profile.events.SizeDialogFragmentEvents;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;

import static ru.fitsme.android.presentation.fragments.profile.view.SizeObserver.NO_SIZE;

public class TopSizeDialogFragment extends DialogFragment
 implements SizeDialogFragmentEvents, SizeObserver.Callback {

    private DialogFragmentProfileTopSizeBinding binding;
    private SizeProfileViewModel viewModel;
    private SizeObserver topSizeObserver = new SizeObserver(this, 0);
    private int lastSavedTopSize = NO_SIZE;

    @Inject
    IProfileInteractor interactor;

    @Inject
    ViewModelFactory viewModelFactory;
    private TopSizeDialogCallback callback;
    private String message;

    private TopSizeDialogFragment(TopSizeDialogCallback callback, String message) {
        this.callback = callback;
        this.message = message;
        App.getInstance().getDi().inject(this);
    }

    public static TopSizeDialogFragment newInstance(TopSizeDialogCallback callback, String message) {
        return new TopSizeDialogFragment(callback, message);
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
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.dialogFragmentProfileTopSizeHintTv.setText(message);
        setSizeCheckers();
    }

    private void setSizeCheckers() {
        viewModel.getTopSizeArray().observe(this.getViewLifecycleOwner(), list -> {
            TableLayout tableLayout = binding.dialogFragmentTopSizeLayout.topSizeProfileSizesTable;
            if (getContext() != null) {
                TableFiller.fillButtons(getContext(), topSizeObserver, list, tableLayout);
            }
            if (viewModel.getCurrentTopSizeIndex().get() != NO_SIZE){
                lastSavedTopSize = viewModel.getCurrentTopSizeIndex().get();
                topSizeObserver.setCheckedSizeIndex(lastSavedTopSize);
                topSizeObserver.setState(lastSavedTopSize, true);
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
        if (callback != null){
            callback.onTopOkButtonClick();
        }
        dismiss();
    }

    @Override
    public void onCancelButtonClicked() {
        if (callback != null){
            callback.onTopCancelButtonClick();
        }
        if (lastSavedTopSize != NO_SIZE) {
            viewModel.onTopSizeValueSelected(lastSavedTopSize);
        }
        dismiss();
    }

    @Override
    public void onSizeValueSelected(int tag, int id) {
        viewModel.onTopSizeValueSelected(id);
    }

    public interface TopSizeDialogCallback {
        void onTopOkButtonClick();
        void onTopCancelButtonClick();
    }
}
