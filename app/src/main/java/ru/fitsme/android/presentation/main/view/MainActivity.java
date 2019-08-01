package ru.fitsme.android.presentation.main.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;
import ru.fitsme.android.databinding.ActivityMainBinding;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignInUpFragment;
import ru.fitsme.android.presentation.fragments.signinup.view.SignUpFragment;
import ru.fitsme.android.presentation.fragments.splash.SplashFragment;
import ru.fitsme.android.presentation.main.viewmodel.MainViewModel;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.app.Navigation.NAV_MAIN_ITEM;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_IN;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_IN_UP;
import static ru.fitsme.android.app.Navigation.NAV_SIGN_UP;
import static ru.fitsme.android.app.Navigation.NAV_SPLASH;

public class MainActivity extends AppCompatActivity {

    @Inject
    Navigation navigation;

    private ActivityMainBinding binding;
    private Navigator navigator = getFragmentNavigator();
    private WeakReference<RateItemsFragment.MyOnSwipeTouchListener> weakSwipeTouchListener;

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

        navigation.setNavigator(navigator);
        navigation.goToSplash();
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

    //передает TouchEvent в RateFragment, т.к. из-за ScrollView события автоматом не передаются
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isDispatch = super.dispatchTouchEvent(ev);
        if (weakSwipeTouchListener != null && weakSwipeTouchListener.get() != null){
            weakSwipeTouchListener.get().onTouch(new TextView(this), ev);
        }
        return isDispatch;
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

    public void observeSwipe(RateItemsFragment.MyOnSwipeTouchListener swipeTouchListener) {
        this.weakSwipeTouchListener = new WeakReference<>(swipeTouchListener);
    }

    public void unsubscribe() {
        weakSwipeTouchListener.clear();
    }
}
