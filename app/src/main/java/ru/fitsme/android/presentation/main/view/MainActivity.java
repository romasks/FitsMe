package ru.fitsme.android.presentation.main.view;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.ActivityMainBinding;
import ru.fitsme.android.presentation.fragments.auth.NumberFragment;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInUpFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignUpFragment;
import ru.fitsme.android.presentation.fragments.splash.SplashFragment;
import ru.fitsme.android.presentation.main.AuthNavigation;
import ru.fitsme.android.presentation.main.viewmodel.MainViewModel;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_AUTH;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_MAIN_ITEM;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SIGN_IN;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SIGN_IN_UP;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SIGN_UP;
import static ru.fitsme.android.presentation.main.AuthNavigation.NAV_SPLASH;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        List<Fragment> list  = getSupportFragmentManager().getFragments();
        BaseFragment fragment = (BaseFragment) list.get(list.size() - 2); //сверху находится какой-то glide support fragment manager, поэтому беру второй
        fragment.onBackPressed();
    }

    @Inject
    AuthNavigation authNavigation;

    private ActivityMainBinding binding;
    private Navigator navigator = getFragmentNavigator();

    public MainActivity() {
        App.getInstance().getDi().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainViewModel mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        binding.setViewModel(mainViewModel);

        authNavigation.setNavigator(navigator);
        authNavigation.goToSplash();
    }

    @Override
    protected void onResume() {
        super.onResume();

        authNavigation.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();

        authNavigation.removeNavigator();
    }

    public void logout() {
        authNavigation.goSignInUp();
    }

    @NonNull
    private SupportFragmentNavigator getFragmentNavigator() {
        return new SupportFragmentNavigator(getSupportFragmentManager(), R.id.activity_main_container) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case NAV_SIGN_IN_UP:
                        return SignInUpFragment.newInstance();
                    case NAV_SIGN_UP:
                        return SignUpFragment.newInstance();
                    case NAV_SIGN_IN:
                        return SignInFragment.newInstance();
                    case NAV_MAIN_ITEM:
                        return MainFragment.newInstance();
                    case NAV_SPLASH:
                        return SplashFragment.newInstance();
                    case NAV_AUTH:
                        return NumberFragment.newInstance();
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
