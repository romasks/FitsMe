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
        binding.fieldName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.fieldName.hideError();
        });
        binding.fieldEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.fieldEmail.hideError();
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
        switch (feedbackStatus) {
            case INITIAL: {
                binding.progress.setVisibility(View.GONE);
                break;
            }
            case ERROR: {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Ошибка отправки", Toast.LENGTH_SHORT).show();
                break;
            }
            case SUCCESS: {
//                binding.progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Успешно отправлено", Toast.LENGTH_SHORT).show();
                goBack();
                break;
            }
            case LOADING: {
                binding.progress.setVisibility(View.VISIBLE);
            }
            default:
        }
    }

    @Override
    public void onClickSendFeedback() {
        KeyboardUtils.hide(getActivity(), binding.getRoot());
        binding.fieldName.hideError();
        binding.fieldEmail.hideError();
        binding.messageErrorIcon.setVisibility(View.GONE);
        binding.getRoot().requestFocus();
        if (validFields()) {
            viewModel.onClickSendFeedback(
                    binding.fieldName.getText(),
                    binding.fieldEmail.getText(),
                    binding.messageEt.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Некоторые поля заполнены неверно", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validFields() {
        boolean result = true;
        if (binding.fieldName.isEmpty()) {
            binding.fieldName.showErrorWithHint();
            result = false;
        }
        if (binding.fieldEmail.isEmpty()) {
            binding.fieldEmail.showErrorWithHint();
            result = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.fieldEmail.getText()).matches()) {
            binding.fieldEmail.showError();
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
