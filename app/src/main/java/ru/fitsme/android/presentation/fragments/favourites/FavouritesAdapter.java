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
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemFavouriteBinding;
import ru.fitsme.android.databinding.ItemFavouriteRemovedBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import timber.log.Timber;

public class FavouritesAdapter extends PagedListAdapter<FavouritesItem, FavouritesAdapter.FavouritesViewHolder> {

    private FavouritesViewModel viewModel;

    private static final int NORMAL_TYPE = 1;
    private static final int REMOVED_TYPE = 2;

    FavouritesAdapter(FavouritesViewModel viewModel) {
        super(FavouritesFragment.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == NORMAL_TYPE){
            ItemFavouriteBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_favourite, parent, false);
            return new NormalViewHolder(binding);
        } else if (viewType == REMOVED_TYPE){
            ItemFavouriteRemovedBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_favourite_removed, parent, false);
            return new RemovedViewHolder(binding);
        } else throw new IllegalArgumentException("Can't create view holder from view type " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (viewModel.itemIsRemoved(position)) {
            return REMOVED_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

    abstract class FavouritesViewHolder extends RecyclerView.ViewHolder{
        FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }


    class NormalViewHolder extends FavouritesViewHolder {
        final ViewDataBinding binding;
        final ImageView rightDeleteIcon;
        final ImageView leftDeleteIcon;
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;
        Button inCartBtn;

        NormalViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_favourite_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_favourite_view_foreground);
            rightDeleteIcon = binding.getRoot().findViewById(R.id.item_favourite_delete_icon_right);
            leftDeleteIcon = binding.getRoot().findViewById(R.id.item_favourite_delete_icon_left);
            inCartBtn = binding.getRoot().findViewById(R.id.favourites_btn_to_cart);
        }

        @Override
        void bind(int position) {
            FavouritesItem favouritesItem = getItem(position);
            ClothesItem clothesItem = favouritesItem.getItem();

            setButtonIsInCartState(favouritesItem.isInCart());

            inCartBtn.setOnClickListener(view ->
                    viewModel.addItemToCart(position)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(orderItem -> {
                                if (orderItem.getId() != 0){
                                    setButtonIsInCartState(true);
                                }
                            }, Timber::e));

            binding.setVariable(BR.clotheItem, clothesItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

        private void setButtonIsInCartState(boolean b) {
            if (b) {
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


    class RemovedViewHolder extends FavouritesViewHolder {
        final ViewDataBinding binding;

        RemovedViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        void bind(int position) {
            TextView undoButton = binding.getRoot().findViewById(R.id.item_favourite_removed_back_tv);
            undoButton.setOnClickListener(v -> {
                viewModel.restoreItem(position)
                        .subscribe(favouritesItem -> {
                            if (favouritesItem.getId() != 0) {
                                notifyItemChanged(position);
                            }},
                                Timber::e);
            });
        }
    }
}
