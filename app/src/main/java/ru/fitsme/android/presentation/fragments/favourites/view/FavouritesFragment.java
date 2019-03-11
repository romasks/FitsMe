package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hendraanggrian.widget.PaginatedRecyclerView;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;


public class FavouritesFragment extends Fragment {
    @Inject
    IFavouritesInteractor favouritesInteractor;

    private FavouritesViewModel viewModel;
    private View fragmentView;

    public FavouritesFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new FavouritesViewModel.Factory(favouritesInteractor)).get(FavouritesViewModel.class);

        if (savedInstanceState == null) {
            viewModel.init();
        }

        ((PaginatedRecyclerView)fragmentView.findViewById(R.id.favourites_list_rv)).setAdapter(viewModel.getAdapter());

        viewModel.loading.set(View.VISIBLE);
        viewModel.getPageLiveData().observe(this, this::onLoadPage);
    }

    private void onLoadPage(List<ClothesItem> favouritesPage) {
        viewModel.loading.set(View.GONE);
        if (favouritesPage.size() == 0) {
            viewModel.showEmpty.set(View.VISIBLE);
        } else {
            viewModel.showEmpty.set(View.GONE);
            viewModel.setFavouritesInAdapter(favouritesPage);
        }
    }


}
