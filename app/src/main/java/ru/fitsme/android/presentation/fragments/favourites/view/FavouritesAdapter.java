package ru.fitsme.android.presentation.fragments.favourites.view;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hendraanggrian.widget.PaginatedRecyclerView;

import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesAdapter extends PaginatedRecyclerView.Adapter<FavouritesAdapter.GenericViewHolder> {

    private int layoutId;
    private List<FavouritesItem> items;
    private FavouritesViewModel viewModel;

    FavouritesAdapter(@LayoutRes int layoutId, FavouritesViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    void setFavouritesItems(List<FavouritesItem> items) {
        this.items = items;
    }

    void addFavouritesItems(List<FavouritesItem> items) {
        this.items.addAll(items);
    }

    void changeStatus(int index, boolean inCart) {
        items.get(index).setInCart(inCart);
        notifyItemChanged(index);
    }

    FavouritesItem getFavouriteItemAt(Integer index) {
        return items.get(index);
    }

    void removeItemAt(Integer index) {
        items.remove(getFavouriteItemAt(index));
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_favourite_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_favourite_view_foreground);
        }

        void bind(int position) {
            FavouritesItem favouritesItem = items.get(position);
            ClothesItemModel clothesItem = new ClothesItemModel(favouritesItem.getItem());

            Button inCartBtn = binding.getRoot().findViewById(R.id.favourites_btn_to_cart);

            if (favouritesItem.isInCart()){
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

            binding.setVariable(BR.clothesItem, clothesItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }

    @BindingAdapter({"app:imageUrl", "app:defaultImage"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable defaultImage) {
        Glide.with(imageView).load(imageUrl).placeholder(defaultImage).error(defaultImage).into(imageView);
    }
}
