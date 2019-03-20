package ru.fitsme.android.presentation.fragments.profile.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.fitsme.android.R;


public class ProfileFragment extends Fragment {

    Button crashBtn;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        crashBtn = (Button) getView().findViewById(R.id.crash_btn_fragment_profile);
        crashBtn.setOnClickListener(v -> {
            throw new RuntimeException("This is a crash!!!");
        });
    }
}
