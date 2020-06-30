package ru.fitsme.android.presentation.main;

import android.graphics.PixelFormat;
import android.os.Bundle;

import com.bumptech.glide.manager.SupportRequestManagerFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.ActivityMainBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.terrakok.cicerone.Navigator;

public class MainActivity extends AppCompatActivity {

    @Inject
    AuthNavigation authNavigation;

    private ActivityMainBinding binding;
    private Navigator navigator = AuthSupportFragmentNavigator.getFragmentNavigator(this);

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

    @Override
    public void onBackPressed() {
        List<Fragment> list = getSupportFragmentManager().getFragments();
        // сверху находится какой-то glide support fragment manager, поэтому беру второй (1)
        // для случая при авторизации первый (0)
        int index = list.get(0) instanceof SupportRequestManagerFragment ? 1 : 0;
        ((BaseFragment) list.get(index)).onBackPressed();
    }
}
