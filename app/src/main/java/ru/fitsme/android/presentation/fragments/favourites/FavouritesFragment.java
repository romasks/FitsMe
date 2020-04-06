package ru.fitsme.android.presentation.fragments.favourites;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentFavouritesBinding;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

public class FavouritesFragment extends BaseFragment<FavouritesViewModel>
        implements FavouritesBindingEvents,
        FavouritesRecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        FavouritesAdapter.OnItemClickCallback {

    private FragmentFavouritesBinding binding;
    private FavouritesAdapter adapter;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_favourites;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentFavouritesBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setUp();
    }

    private void setUp() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new FavouritesRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.favouritesListRv);
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new FavouritesAdapter(viewModel, this);

        binding.favouritesListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favouritesListRv.setHasFixedSize(true);
        binding.favouritesListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getPageLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);
        viewModel.getFavouritesIsEmpty().observe(getViewLifecycleOwner(), this::onFavouritesIsEmpty);
    }

    private void onLoadPage(PagedList<FavouritesItem> pagedList) {
        adapter.submitList(pagedList);
    }

    private void onFavouritesIsEmpty(Boolean hasNoItems) {
        binding.favouritesNoItemsGroup.setVisibility(hasNoItems ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (position != RecyclerView.NO_POSITION) {
            viewModel.removeItem(position)
                    .subscribe(removedItem -> {
                        if (removedItem.getId() != 0) {
                            adapter.notifyItemChanged(position);
                        }
                    }, Timber::e);
        }
    }

    @Override
    public void onClickGoToRateItems() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToRateItems();
        }
    }

    @Override
    public void setDetailView(FavouritesItem favouritesItem) {
        viewModel.setDetailView(favouritesItem);
    }
}
