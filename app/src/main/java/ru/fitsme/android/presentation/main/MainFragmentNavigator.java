package ru.fitsme.android.presentation.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import ru.fitsme.android.presentation.fragments.signinup.SignInUpFragment;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.presentation.main.MainViewModel.NAV_DEBUG;
import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_IN;
import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_IN_UP;
import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_UP;

public class MainFragmentNavigator extends SupportFragmentNavigator {
    private Context context;

    public MainFragmentNavigator(FragmentManager fragmentManager, int containerId, Context context) {
        super(fragmentManager, containerId);
        this.context = context;
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case NAV_SIGN_IN_UP:
                return SignInUpFragment.newInstance();
            case NAV_SIGN_UP:
                break;
            case NAV_SIGN_IN:
                break;
            case NAV_DEBUG:
                break;
        }
        throw new RuntimeException("Unknown screen key");
    }

    @Override
    protected void showSystemMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
    }
}
