package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentFavouritesBinding;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.VISIBLE;


public class FavouritesFragment extends Fragment {
    @Inject
    IFavouritesInteractor favouritesInteractor;

    private final String TAG = getClass().getName();

    private FavouritesViewModel viewModel;
    private FragmentFavouritesBinding binding;

    public FavouritesFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new FavouritesViewModel.Factory(favouritesInteractor)).get(FavouritesViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        binding.favouritesListRv.setHasFixedSize(true);
        binding.favouritesListRv.setAdapter(viewModel.getAdapter());
        binding.favouritesListRv.setPagination(viewModel.getPagination());

        viewModel.loading.set(VISIBLE);
        viewModel.getPageLiveData().observe(this, this::onLoadPage);
    }

    private void onLoadPage(List<FavouritesItem> favouritesItems) {
        viewModel.loading.set(GONE);
        if (favouritesItems == null || favouritesItems.size() == 0) {
            viewModel.showEmpty.set(VISIBLE);
        } else {
            viewModel.showEmpty.set(GONE);
            viewModel.setFavouritesInAdapter(favouritesItems);
        }
    }

}
