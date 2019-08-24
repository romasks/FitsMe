package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFavouritesBinding;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import timber.log.Timber;

public class FavouritesFragment extends BaseFragment<FavouritesViewModel>
        implements FavouritesRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Inject
    IFavouritesInteractor favouritesInteractor;

    private FragmentFavouritesBinding binding;
    private FavouritesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static DiffUtil.ItemCallback<FavouritesItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavouritesItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull FavouritesItem oldItem, @NonNull FavouritesItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavouritesItem oldItem, @NonNull FavouritesItem newItem) {
            return oldItem.equals(newItem);
        }
    };

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
                new ViewModelFactory(favouritesInteractor)).get(FavouritesViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new FavouritesAdapter(viewModel);

        binding.favouritesListRv.setLayoutManager(linearLayoutManager);
        binding.favouritesListRv.setHasFixedSize(true);
        binding.favouritesListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(
                this, this::onLoadPage);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new FavouritesRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.favouritesListRv);
    }

    private void onLoadPage(PagedList<FavouritesItem> pagedList) {
        adapter.submitList(pagedList);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (position != RecyclerView.NO_POSITION) {
            viewModel.removeItem(position)
                    .subscribe(removedItem -> {
                        if (removedItem.getId() != 0){
                            adapter.notifyItemChanged(position);
                        }
                    }, Timber::e);
        }
    }
}
