package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;

public abstract class InListItemState {
    FavouritesAdapter.InListViewHolder viewHolder;
    FavouritesAdapter.OnItemClickCallback callback;

    InListItemState(FavouritesAdapter.InListViewHolder viewHolder, FavouritesAdapter.OnItemClickCallback callback){
        this.viewHolder = viewHolder;
        this.callback = callback;
        this.viewHolder.binding.getRoot().setOnClickListener(v -> {
            callback.setDetailView(viewHolder.getFavouritesItem());
        });
    }

    public abstract void onButtonClick(FavouritesViewModel viewModel, int position);
}
