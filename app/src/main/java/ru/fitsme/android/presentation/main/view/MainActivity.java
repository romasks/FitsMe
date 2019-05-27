package ru.fitsme.android.presentation.main.view;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInUpFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignUpFragment;
import ru.fitsme.android.presentation.main.viewmodel.MainViewModel;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.app.Navigation.NAV_MAIN_ITEM;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_IN;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_IN_UP;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_UP;

public class MainActivity extends AppCompatActivity {

    @Inject
    Navigation navigation;

    private Navigator navigator = getFragmentNavigator();

    public MainActivity() {
        App.getInstance().getDi().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigation.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();

        navigation.removeNavigator();
    }

    @NonNull
    private SupportFragmentNavigator getFragmentNavigator() {
        return new SupportFragmentNavigator(getSupportFragmentManager(), R.id.fragment_container) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case NAV_SIGN_IN_UP:
                        return SignInUpFragment.newInstance();
                    case NAV_SIGN_UP:
                        return SignUpFragment.newInstance();
                    case NAV_SIGN_IN:
                        return SignInFragment.newInstance();
             /*       case NAV_RATE_ITEM:
                        return RateItemFragment.newInstance();
             */
                    case NAV_MAIN_ITEM:
                        return MainFragment.newInstance();
                }
                throw new RuntimeException("Unknown screen key");
            }

            @Override
            protected void showSystemMessage(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                finish();
            }
        };
    }
}
