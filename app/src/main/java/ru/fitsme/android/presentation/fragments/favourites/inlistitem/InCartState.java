package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;

public class InCartState extends InListItemState {
    public InCartState(FavouritesAdapter.InListViewHolder viewHolder) {
        super(viewHolder);
        this.viewHolder.imageView.setAlpha(1f);
        this.viewHolder.brandName.setAlpha(1f);
        this.viewHolder.name.setAlpha(1f);
        this.viewHolder.price.setAlpha(1f);
        this.viewHolder.button.setAlpha(1f);
        this.viewHolder.button.setBackgroundResource(R.drawable.bg_in_cart_btn);
        this.viewHolder.button.setEnabled(false);
        this.viewHolder.button.setText(R.string.clothe_in_cart);
        this.viewHolder.button.setTextColor(App.getInstance().getResources().getColor(R.color.black));
    }

    @Override
    public void onButtonClick(FavouritesViewModel viewModel, int position) {
    }
}
