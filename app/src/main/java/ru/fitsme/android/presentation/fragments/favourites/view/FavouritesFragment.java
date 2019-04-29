package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentFavouritesBinding;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import timber.log.Timber;


public class FavouritesFragment extends Fragment
    implements FavouritesRecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    @Inject
    IFavouritesInteractor favouritesInteractor;

    private final String TAG = getClass().getName();

    private FavouritesViewModel viewModel;
    private FragmentFavouritesBinding binding;
    private FavouritesAdapter adapter;

    private ContentLoadingProgressBar loadingProgressBar;
    private TextView emptyTv;

    public static  DiffUtil.ItemCallback<FavouritesItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavouritesItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull FavouritesItem oldItem, @NonNull FavouritesItem newItem) {
            Timber.d("areItemsTheSame()");
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavouritesItem oldItem, @NonNull FavouritesItem newItem) {
            return oldItem.equals(newItem);
        }
    };

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

        loadingProgressBar = getView().findViewById(R.id.spinnerLoading);
        emptyTv = getView().findViewById(R.id.empty_tv_fragment_favourites);

        viewModel = ViewModelProviders.of(this,
                new FavouritesViewModel.Factory(favouritesInteractor)).get(FavouritesViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        adapter = new FavouritesAdapter(viewModel);

        binding.favouritesListRv.setHasFixedSize(true);
        binding.favouritesListRv.setAdapter(adapter);

        loadingProgressBar.setVisibility(View.VISIBLE);
        viewModel.getPageLiveData().observe(
                this, this::onLoadPage);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new FavouritesRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.favouritesListRv);
    }

    private void onLoadPage(PagedList<FavouritesItem> pagedList) {
        loadingProgressBar.setVisibility(View.GONE);
        if (pagedList == null || pagedList.size() == 0) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.INVISIBLE);
        }
        adapter.submitList(pagedList);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (position != RecyclerView.NO_POSITION){
            viewModel.deleteItem(position);
        }
    }
}
