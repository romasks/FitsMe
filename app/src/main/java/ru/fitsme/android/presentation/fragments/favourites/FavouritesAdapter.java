package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.databinding.ItemFavouriteBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FavouritesAdapter extends PagedListAdapter<FavouritesItem, FavouritesAdapter.GenericViewHolder> {

    private BaseViewModel viewModel;

    FavouritesAdapter(BaseViewModel viewModel) {
        super(FavouritesFragment.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFavouriteBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_favourite, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(position);
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        final ImageView rightDeleteIcon;
        final ImageView leftDeleteIcon;
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_favourite_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_favourite_view_foreground);
            rightDeleteIcon = binding.getRoot().findViewById(R.id.item_favourite_delete_icon_right);
            leftDeleteIcon = binding.getRoot().findViewById(R.id.item_favourite_delete_icon_left);
        }

        void bind(int position) {
            FavouritesItem favouritesItem = getItem(position);
            ClothesItem clothesItem = favouritesItem.getItem();

            Button inCartBtn = binding.getRoot().findViewById(R.id.favourites_btn_to_cart);

            if (favouritesItem.isInCart()) {
                inCartBtn.setBackgroundResource(R.drawable.bg_in_cart_btn);
                inCartBtn.setEnabled(false);
                inCartBtn.setText(R.string.clothe_in_cart);
                inCartBtn.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
            } else {
                inCartBtn.setBackgroundResource(R.drawable.bg_to_cart_btn);
                inCartBtn.setEnabled(true);
                inCartBtn.setText(R.string.to_cart);
                inCartBtn.setTextColor(binding.getRoot().getResources().getColor(R.color.white));
            }

            binding.setVariable(BR.clotheItem, clothesItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
