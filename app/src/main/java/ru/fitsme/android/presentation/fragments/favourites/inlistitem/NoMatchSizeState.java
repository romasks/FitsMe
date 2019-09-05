package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;

public class NoMatchSizeState extends InListItemState {
    public NoMatchSizeState(FavouritesAdapter.InListViewHolder viewHolder) {
        super(viewHolder);
        this.viewHolder.imageView.setAlpha(0.5f);
        this.viewHolder.brandName.setAlpha(0.5f);
        this.viewHolder.name.setAlpha(0.5f);
        this.viewHolder.price.setAlpha(0.5f);
        this.viewHolder.button.setAlpha(0.5f);
        this.viewHolder.button.setBackgroundResource(R.drawable.bg_in_cart_btn);
        this.viewHolder.button.setEnabled(false);
        this.viewHolder.button.setText(App.getInstance().getString(R.string.no_match_size));
        this.viewHolder.button.setTextColor(App.getInstance().getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(FavouritesViewModel viewModel, int position) {

    }
}
