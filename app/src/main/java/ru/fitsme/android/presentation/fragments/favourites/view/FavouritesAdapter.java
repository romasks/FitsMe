package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemFavouriteBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import timber.log.Timber;

public class FavouritesAdapter extends PagedListAdapter<FavouritesItem, FavouritesAdapter.GenericViewHolder> {

    private FavouritesViewModel viewModel;

    FavouritesAdapter(FavouritesViewModel viewModel) {
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
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_favourite_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_favourite_view_foreground);
        }

        void bind(int position) {
            Timber.d("onBind %s", position);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();

            FavouritesItem favouritesItem = getItem(position);
            ClothesItem clothesItem = Objects.requireNonNull(favouritesItem).getItem();
            String imageUrl = clothesItem.getPics()
                    .get(0)
                    .getUrl()
                    .replace("random", "image=");
            imageUrl += clothesItem.getId() % 400;

            ImageView imageView = binding.getRoot().findViewById(R.id.favourite_image);
            Glide.with(imageView)
                    .load(imageUrl)
                    .placeholder(R.drawable.clother_example)
                    .into(imageView);

            TextView brandName = binding.getRoot().findViewById(R.id.favourites_brand_name);
            TextView name = binding.getRoot().findViewById(R.id.favourites_name);
            TextView price = binding.getRoot().findViewById(R.id.favourites_price);
            Button inCartBtn = binding.getRoot().findViewById(R.id.favourites_btn_to_cart);

            brandName.setText(clothesItem.getBrand());
            name.setText(clothesItem.getName());
            price.setText(clothesItem.getDescription());
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
        }
    }

//
//    @Override
//    public int getItemCount() {
//        return items == null ? 0 : items.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getLayoutIdForPosition(position);
//    }
//
//    private int getLayoutIdForPosition(int position) {
//        return layoutId;
//    }
//
//    void setFavouritesItems(List<FavouritesItem> items) {
//        this.items = items;
//    }
//
//    void addFavouritesItems(List<FavouritesItem> items) {
//        this.items.addAll(items);
//    }
//
//    void changeStatus(int index, boolean inCart) {
//        items.get(index).setInCart(inCart);
//        notifyItemChanged(index);
//    }
//
//    FavouritesItem getFavouriteItemAt(Integer index) {
//        return items.get(index);
//    }
//
//    void removeItemAt(Integer index) {
//        items.remove(getFavouriteItemAt(index));
//    }
//

}
