package ru.fitsme.android.Presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.fitsme.android.App.App;
import ru.fitsme.android.Domain.Interactors.Auth.SignInUpInteractor;
import ru.fitsme.android.R;

public class MainActivity extends AppCompatActivity {

    @Inject
    SignInUpInteractor signInUpInteractor;

    public MainActivity() {
        App.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
