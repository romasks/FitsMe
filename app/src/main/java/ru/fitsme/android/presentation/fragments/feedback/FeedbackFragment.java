package ru.fitsme.android.presentation.fragments.feedback;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFeedbackBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class FeedbackFragment extends BaseFragment<FeedbackViewModel> implements FeedbackBindingEvents, BackClickListener {

    private FragmentFeedbackBinding binding;

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentFeedbackBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.feedback_header_text));
    }

    @Override
    protected void setUpObservers() {
        viewModel.getSuccessSendFeedbackLiveData().observe(getViewLifecycleOwner(), this::onSuccessSendFeedback);
    }

    private void onSuccessSendFeedback(Boolean successSendFeedback) {
        if (successSendFeedback) goBack();
    }

    @Override
    public void onClickSendFeedback() {
        if (validFields()) {
            viewModel.onClickSendFeedback("", "", "");
        } else {
            binding.nameLayout.setError("ERROR");
        }
    }

    private boolean validFields() {
        return false;
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }
}
