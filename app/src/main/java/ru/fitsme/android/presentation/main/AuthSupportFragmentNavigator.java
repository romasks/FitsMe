package ru.fitsme.android.presentation.main;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.agreement.AgreementFragment;
import ru.fitsme.android.presentation.fragments.auth.CodeFragment;
import ru.fitsme.android.presentation.fragments.auth.NumberFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.splash.SplashFragment;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_AGREEMENT;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_AUTH;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_CODE_INPUT;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_MAIN_ITEM;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SPLASH;

public class AuthSupportFragmentNavigator {

    @NonNull
    public static SupportFragmentNavigator getFragmentNavigator(FragmentActivity activity) {
        return new SupportFragmentNavigator(activity.getSupportFragmentManager(), R.id.activity_main_container) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case NAV_MAIN_ITEM:
                        return MainFragment.newInstance();
                    case NAV_SPLASH:
                        return SplashFragment.newInstance();
                    case NAV_AUTH:
                        return NumberFragment.newInstance();
                    case NAV_CODE_INPUT:
                        return CodeFragment.newInstance();
                    case NAV_AGREEMENT:
                        return AgreementFragment.newInstance();
                }
                throw new RuntimeException("Unknown screen key");
            }

            @Override
            protected void showSystemMessage(String message) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                activity.finish();
            }
        };
    }
}
