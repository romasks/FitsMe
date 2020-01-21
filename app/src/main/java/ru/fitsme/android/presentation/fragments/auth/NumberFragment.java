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

public class NumberFragment extends BaseFragment<NumberViewModel> implements NumberBindingEvents {

    private FragmentAuthByPhoneNumBinding binding;
    private MyTextWatcher textWatcher = new MyTextWatcher();

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

    @Override
    protected int getLayout() {
        return R.layout.fragment_auth_by_phone_num;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentAuthByPhoneNumBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onGetCodeClicked() {
        String countryCode = binding.fragmentPhoneAuthCodeEt.getText().toString();
        String phoneNumber = binding.fragmentPhoneAuthNumberEt.getText().toString();
        viewModel.getAuthCode(countryCode + phoneNumber);
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
            String string = editable.toString();
            String clean = string.replaceAll("[()-]", " ");
            StringBuilder builder = new StringBuilder(clean);
            if (builder.length() > 0){
                builder.insert(0, '(');
            }
            if (builder.length() > 4){
                builder.insert(4, ")");
            }
            if (builder.length() > 8){
                builder.insert(8, "-");
            }
            if (builder.length() > 11){
                builder.insert(11 , "-");
            }
        }
    }
}
