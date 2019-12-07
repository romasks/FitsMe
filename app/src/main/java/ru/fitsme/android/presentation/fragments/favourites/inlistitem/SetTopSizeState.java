package ru.fitsme.android.presentation.fragments.favourites.inlistitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;

public class SetTopSizeState extends InListItemState {
    public SetTopSizeState(FavouritesAdapter.InListViewHolder viewHolder, FavouritesAdapter.OnItemClickCallback callback) {
        super(viewHolder, callback);
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
    public void onButtonClick(FavouritesViewModel viewModel, int position) {
        DialogFragment dialogFragment = new TopSizeDialogFragment();
        FragmentManager fm = ((AppCompatActivity) viewHolder.binding.getRoot().getContext()).getSupportFragmentManager();
        dialogFragment.show(fm, "topSizeDf");
    }
}
