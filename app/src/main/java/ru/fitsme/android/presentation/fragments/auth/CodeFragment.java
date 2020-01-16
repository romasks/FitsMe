package ru.fitsme.android.presentation.fragments.auth;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class CodeFragment extends BaseFragment<CodeViewModel> {

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_code;
    }

    @Override
    protected void afterCreateView(View view) {

    }
}
