package ru.fitsme.android.presentation.fragments.favourites;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hendraanggrian.widget.PaginatedRecyclerView;

import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FavouritesAdapter extends PaginatedRecyclerView.Adapter<FavouritesAdapter.GenericViewHolder> {

    private int layoutId;
    private List<FavouritesItem> items;
    private BaseViewModel viewModel;

    public FavouritesAdapter(@LayoutRes int layoutId, BaseViewModel viewModel) {
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

    public void setFavouritesItems(List<FavouritesItem> items) {
        this.items = items;
    }

    public void addFavouritesItems(List<FavouritesItem> items) {
        this.items.addAll(items);
    }

    public void changeStatus(int index, boolean inCart) {
        items.get(index).setInCart(inCart);
        notifyItemChanged(index);
    }

    public FavouritesItem getFavouriteItemAt(Integer index) {
        return items.get(index);
    }

    public void removeItemAt(Integer index) {
        items.remove(items.get(index));
        notifyItemRemoved(index);
    }

    public class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;

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

            binding.setVariable(BR.clothesItem, clothesItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
