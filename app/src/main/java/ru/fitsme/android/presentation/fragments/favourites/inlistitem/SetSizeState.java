package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;
import timber.log.Timber;

public class SetSizeState extends InListItemState {
    public SetSizeState(FavouritesAdapter.InListViewHolder button) {
        super(button);
        this.viewHolder.imageView.setAlpha(0.5f);
        this.viewHolder.brandName.setAlpha(0.5f);
        this.viewHolder.name.setAlpha(0.5f);
        this.viewHolder.price.setAlpha(0.5f);
        this.viewHolder.button.setAlpha(1f);
        this.viewHolder.button.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.viewHolder.button.setEnabled(true);
        this.viewHolder.button.setText(R.string.set_your_size);
        this.viewHolder.button.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(FavouritesViewModel viewModel, int position) {
        Timber.d("Set size state. onClick()");
        DialogFragment dialogFragment = new TopSizeDialogFragment();
        FragmentManager fm = ((AppCompatActivity) viewHolder.binding.getRoot().getContext()).getSupportFragmentManager();
        dialogFragment.show(fm, "topSizeDf");
    }
}
