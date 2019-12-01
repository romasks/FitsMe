package ru.fitsme.android.presentation.fragments.returns.processing.five;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnBillingInfoBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

import static ru.fitsme.android.utils.Constants.CARD_NUMBER_MASK;

public class BillingInfoReturnFragment extends BaseFragment<BillingInfoReturnViewModel> implements BillingInfoReturnBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private static final String KEY_RETURN_ID = "RETURN_ID";

    private FragmentReturnBillingInfoBinding binding;
    private int returnId;

    //    private boolean isMaskFilled = false;
    String a;
    int keyDel;

    public static BillingInfoReturnFragment newInstance(int returnId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RETURN_ID, returnId);
        BillingInfoReturnFragment fragment = new BillingInfoReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_billing_info, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.returns_billing_info_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(BillingInfoReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        initCardNumberFieldListener(binding.cardNumber);
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsChooseItems();
    }

    @Override
    public void onNext() {
        if (binding.cardNumber.length() < 19) {
            Toast.makeText(getContext(), R.string.warning_card_number_is_not_filled, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.goToReturnsVerifyData(String.valueOf(binding.cardNumber.getText()), returnId);
        }
    }


    private void initCardNumberFieldListener(EditText cardField) {
        // Method 4:
        cardField.addTextChangedListener(new FourDigitCardFormatWatcher());


        // Method 3:
        /*Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("____ ____ ____ ____");
        final MaskImpl mask = MaskImpl.createTerminated(slots);
//        final MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDART);
        mask.setPlaceholder('0');
        mask.setShowingEmptySlots(true);
        FormatWatcher formatWatcher = new MaskFormatWatcher(mask);
        formatWatcher.installOn(cardField);*/


        // Method 2:
        /*MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDART_MASKABLE);
        mask.setShowingEmptySlots(true);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(cardField);*/


        // Method 1:
        /*final MaskedTextChangedListener cardListener = new MaskedTextChangedListener(
                CARD_NUMBER_MASK, cardField,
                (maskFilled, extractedValue) -> isMaskFilled = maskFilled
        ) {
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(@Nullable Editable edit) {
                super.afterTextChanged(edit);

                Mask inputMask = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDART);
                inputMask.insertFront(edit);

                cardField.setText(inputMask.toString());
                cardField.setSelection(edit.length());
            }
        };

        cardField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                cardField.addTextChangedListener(cardListener);

                cardField.setCursorVisible(false);
                cardField.post(() -> {
                    cardField.setSelection(cardField.getText().length());
                    cardField.setCursorVisible(true);
                });
            } else {
                cardField.removeTextChangedListener(cardListener);

                if (!isMaskFilled)
                    Toast.makeText(getContext(), R.string.warning_card_number_is_not_filled, Toast.LENGTH_SHORT).show();
            }
        });*/

        cardField.setText(CARD_NUMBER_MASK);
        cardField.requestFocus();
    }

    public class FourDigitCardFormatWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean flag = true;
            final EditText text = binding.cardNumber;
            String[] eachBlock = text.getText().toString().split("-");
            for (String value : eachBlock) {
                if (value.length() > 4)
                    flag = false;
            }
            if (flag) {
                text.setOnKeyListener((v, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                });

                if (keyDel == 0) {
                    if (((text.getText().length() + 1) % 5) == 0) {
                        if (text.getText().toString().split("-").length <= 3) {
                            text.setText(text.getText() + "-");
                            text.setSelection(text.getText().length());
                        }
                    }
                    a = text.getText().toString();
                } else {
                    a = text.getText().toString();
                    keyDel = 0;
                }

            } else {
                text.setText(a);
            }
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
