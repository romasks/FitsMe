package ru.fitsme.android.presentation.fragments.favourites.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hendraanggrian.widget.PaginatedRecyclerView;

import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
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
        holder.bind(viewModel, position);
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

        void bind(FavouritesViewModel viewModel, Integer position) {
            ClothesItem item = getFavouriteItemAt(position).getItem();
            String imageUrl = item.getPics()
                    .get(0)
                    .getUrl()
                    .replace("random", "image=");
            imageUrl += item.getId() % 400;

            ImageView imageView = binding.getRoot().findViewById(R.id.favourite_image);
            Glide.with(imageView)
                    .load(imageUrl)
                    .placeholder(R.drawable.clother_example)
                    .into(imageView);

            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
