package ru.fitsme.android.presentation.fragments.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentAuthByPhoneNumBinding;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class NumberFragment extends BaseFragment<NumberViewModel> {

    @Inject
    IAuthInteractor authInteractor;

    private FragmentAuthByPhoneNumBinding binding;
    private MyTextWatcher textWatcher;

    public static NumberFragment newInstance() {
        return new NumberFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_by_phone_num, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCountyFlag();
        setListeners();
    }

    private void setListeners() {
        binding.fragmentPhoneAuthGetCodeBtn.setOnClickListener(view -> {
            String countryCode = binding.fragmentPhoneAuthCodeEt.getText().toString();
            String phoneNumber = binding.fragmentPhoneAuthNumberEt.getText().toString();
            viewModel.getAuthCode(countryCode + phoneNumber);
        });
        binding.fragmentPhoneAuthNumberEt.addTextChangedListener(textWatcher);
    }

    private void setCountyFlag() {
        if (binding.fragmentPhoneAuthCodeEt.getText().toString().equals("+7")){
            binding.fragmentPhoneAuthCodeEt
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rus_flag, 0, 0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }


    private class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            binding.fragmentPhoneAuthNumberEt.addTextChangedListener(null);
//            if (editable.length() > 0){
//                editable.insert(0, "(");
//            }
//            if (editable.length() > 4){
//                editable.insert(4, ")");
//            }
//            if (editable.length() > )
        }
    }
}
