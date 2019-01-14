package ru.fitsme.android.presentation.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.fitsme.android.R;

public class MainActivity extends AppCompatActivity {

    /*@Inject
    SignInUpInteractor signInUpInteractor;*/

    public MainActivity() {
        //App.getApp().getDi().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mainViewModel.getNavigationLiveData()
                .observe(this, this::switchFragment);
    }

    private void switchFragment(int navId) {
        switch (navId) {
            case MainViewModel.NAV_AUTH_REGISTER:

                break;
            case MainViewModel.NAV_AUTH:

                break;
            case MainViewModel.NAV_REGISTER:

                break;
            case MainViewModel.NAV_DEBUG:

                break;
            default:
                throw new RuntimeException("Unknown navigation id");
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
