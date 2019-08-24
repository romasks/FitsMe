package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;

public abstract class InListItemState {
    FavouritesAdapter.InListViewHolder viewHolder;

    InListItemState(FavouritesAdapter.InListViewHolder viewHolder){
        this.viewHolder = viewHolder;
    }

    public abstract void onClick(FavouritesViewModel viewModel, int position);
}
