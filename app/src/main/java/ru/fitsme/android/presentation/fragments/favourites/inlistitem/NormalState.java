package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import timber.log.Timber;

public class NormalState extends InListItemState {
    public NormalState(FavouritesAdapter.InListViewHolder viewHolder) {
        super(viewHolder);
        this.viewHolder.imageView.setAlpha(1f);
        this.viewHolder.brandName.setAlpha(1f);
        this.viewHolder.name.setAlpha(1f);
        this.viewHolder.price.setAlpha(1f);
        this.viewHolder.button.setAlpha(1f);
        this.viewHolder.button.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.viewHolder.button.setEnabled(true);
        this.viewHolder.button.setText(R.string.to_cart);
        this.viewHolder.button.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(FavouritesViewModel viewModel, int position) {
        viewModel.addItemToCart(position)
                .subscribe(orderItem -> {
                    if (orderItem.getId() != 0){
                        viewHolder.setItemState(new InCartState(viewHolder));
                    }
                }, Timber::e);
    }
}
