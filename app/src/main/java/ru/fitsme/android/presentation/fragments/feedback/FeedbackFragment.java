package ru.fitsme.android.presentation.fragments.feedback;

import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFeedbackBinding;
import ru.fitsme.android.domain.entities.feedback.FeedbackStatus;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

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
        setUp();
    }

    private void setUp() {
        binding.nameEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.nameEt.setHint(R.string.feedback_hint_enter_name);
                binding.nameErrorIcon.setVisibility(View.GONE);
                binding.nameErrorLine.setVisibility(View.GONE);
            }
        });
        binding.emailEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.emailEt.setHint(R.string.feedback_hint_enter_email);
                binding.emailErrorIcon.setVisibility(View.GONE);
                binding.emailErrorLine.setVisibility(View.GONE);
            }
        });
        binding.messageEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.messageEt.setHint(R.string.feedback_hint_enter_message);
                binding.messageErrorIcon.setVisibility(View.GONE);
            }
        });
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).hideBottomNavbar();
        }
    }

    @Override
    protected void setUpObservers() {
        viewModel.getFeedbackStatusLiveData().observe(getViewLifecycleOwner(), this::onSuccessSendFeedback);
    }

    private void onSuccessSendFeedback(FeedbackStatus feedbackStatus) {
        binding.btnSendFeedback.setEnabled(true);
        switch (feedbackStatus) {
            case ERROR: {
                Toast.makeText(getActivity(), "Ошибка отправки", Toast.LENGTH_SHORT).show();
                break;
            }
            case SUCCESS: {
                Toast.makeText(getActivity(), "Успешно отправлено", Toast.LENGTH_SHORT).show();
                goBack();
                break;
            }
            case LOADING:
        }
    }

    @Override
    public void onClickSendFeedback() {
        KeyboardUtils.hide(getActivity(), binding.getRoot());
        binding.nameErrorIcon.setVisibility(View.GONE);
        binding.nameErrorLine.setVisibility(View.GONE);
        binding.emailErrorIcon.setVisibility(View.GONE);
        binding.emailErrorLine.setVisibility(View.GONE);
        binding.messageErrorIcon.setVisibility(View.GONE);
        binding.getRoot().requestFocus();
        if (validFields()) {
            binding.btnSendFeedback.setEnabled(false);
            viewModel.onClickSendFeedback(
                    binding.nameEt.getText().toString(),
                    binding.emailEt.getText().toString(),
                    binding.messageEt.getText().toString());
        }
    }

    private boolean validFields() {
        boolean result = true;
        if (binding.nameEt.getText().toString().isEmpty()) {
            binding.nameEt.setHint(R.string.feedback_error_text);
            binding.nameErrorIcon.setVisibility(View.VISIBLE);
            binding.nameErrorLine.setVisibility(View.VISIBLE);
            result = false;
        }
        if (binding.emailEt.getText().toString().isEmpty()) {
            binding.emailEt.setHint(R.string.feedback_error_text);
            binding.emailErrorIcon.setVisibility(View.VISIBLE);
            binding.emailErrorLine.setVisibility(View.VISIBLE);
            result = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.getText().toString()).matches()) {
            binding.emailErrorIcon.setVisibility(View.VISIBLE);
            binding.emailErrorLine.setVisibility(View.VISIBLE);
            result = false;
        }
        if (binding.messageEt.getText().toString().isEmpty()) {
            binding.messageEt.setHint(R.string.feedback_error_text);
            binding.messageErrorIcon.setVisibility(View.VISIBLE);
            result = false;
        }
        return result;
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavbar();
        }
    }
}
