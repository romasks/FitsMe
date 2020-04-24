package ru.fitsme.android.presentation.fragments.favourites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemFavouriteBinding;
import ru.fitsme.android.databinding.ItemFavouriteRemovedBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheType;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.InCartState;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.InListItemState;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.NoMatchSizeState;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.NormalState;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.SetBottomSizeState;
import ru.fitsme.android.presentation.fragments.favourites.inlistitem.SetTopSizeState;
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;
import timber.log.Timber;

public class FavouritesAdapter extends PagedListAdapter<FavouritesItem, FavouritesAdapter.FavouritesViewHolder> {

    private FavouritesViewModel viewModel;
    private OnItemClickCallback callback;

    private List<Boolean> isInCartList = new ArrayList<>();

    private static final int IN_LIST_TYPE = 1;
    private static final int REMOVED_TYPE = 2;

    FavouritesAdapter(FavouritesViewModel viewModel, OnItemClickCallback callback) {
        super(FavouritesItem.DIFF_CALLBACK);
        this.viewModel = viewModel;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == IN_LIST_TYPE) {
            ItemFavouriteBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_favourite, parent, false);
            return new InListViewHolder(binding);
        } else if (viewType == REMOVED_TYPE) {
            ItemFavouriteRemovedBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_favourite_removed, parent, false);
            return new RemovedViewHolder(binding);
        } else
            throw new IllegalArgumentException("Can't create view holder from view type " + viewType);
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
            return IN_LIST_TYPE;
        }
    }

    abstract static class FavouritesViewHolder extends RecyclerView.ViewHolder {
        FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }


    public class InListViewHolder extends FavouritesViewHolder
            implements TopSizeDialogFragment.TopSizeDialogCallback,
            BottomSizeDialogFragment.BottomSizeDialogCallback {
        final public ViewDataBinding binding;
        final ImageView rightDeleteIcon;
        final ImageView leftDeleteIcon;
        final RelativeLayout viewBackground;
        final RelativeLayout viewForeground;
        final public ImageView imageView;
        final public TextView brandName;
        final public TextView name;
        final public TextView price;
        final public Button button;
        private InListItemState state;
        private FavouritesItem favouritesItem;

        InListViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            viewBackground = view.findViewById(R.id.item_favourite_view_background);
            viewForeground = view.findViewById(R.id.item_favourite_view_foreground);
            rightDeleteIcon = view.findViewById(R.id.item_favourite_delete_icon_right);
            leftDeleteIcon = view.findViewById(R.id.item_favourite_delete_icon_left);
            imageView = view.findViewById(R.id.item_favourite_image);
            brandName = view.findViewById(R.id.item_favourite_brand_name);
            name = view.findViewById(R.id.item_favourite_name);
            price = view.findViewById(R.id.item_favourite_price);
            button = view.findViewById(R.id.item_favourite_btn);
        }

        @Override
        void bind(int position) {
            favouritesItem = getItem(position);
            ClothesItem clothesItem = favouritesItem == null ? new ClothesItem() : favouritesItem.getItem();

            setItemState(favouritesItem, position);
            button.setOnClickListener(view -> state.onButtonClick(viewModel, position));

            binding.setVariable(BR.clotheItem, clothesItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

        private void setItemState(@Nullable FavouritesItem favouritesItem, int position) {
            if (favouritesItem == null) return;
            try {
                isInCartList.get(position);
            } catch (IndexOutOfBoundsException ex) {
                isInCartList.add(favouritesItem.isInCart());
            }
            if (isInCartList.get(position)) {
                state = new InCartState(this, callback);
            } else {
                ClothesItem.SizeInStock sizeInStock = favouritesItem.getItem().getSizeInStock();
                switch (sizeInStock) {
                    case UNDEFINED: {
                        if (favouritesItem.getItem().getClotheType().getType() == ClotheType.Type.TOP) {
                            setItemState(new SetTopSizeState(this, callback));
                        } else if (favouritesItem.getItem().getClotheType().getType() == ClotheType.Type.BOTTOM) {
                            setItemState(new SetBottomSizeState(this, callback));
                        }
                        break;
                    }
                    case YES: {
                        setItemState(new NormalState(this, callback));
                        break;
                    }
                    case NO: {
                        setItemState(new NoMatchSizeState(this, callback));
                        break;
                    }
                }
            }
        }

        void setItemState(InListItemState state) {
            this.state = state;
        }

        public void setItemState(InListItemState state, int position) {
            if (state instanceof InCartState) {
                isInCartList.set(position, true);
            }
            this.state = state;
        }

        public FavouritesItem getFavouritesItem() {
            return favouritesItem;
        }

        @Override
        public void onBottomOkButtonClick() {
            viewModel.updateList();
        }

        @Override
        public void onBottomCancelButtonClick() {

        }

        @Override
        public void onTopOkButtonClick() {
            viewModel.updateList();
        }

        @Override
        public void onTopCancelButtonClick() {

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
            undoButton.setOnClickListener(v -> viewModel.restoreItem(position)
                    .subscribe(favouritesItem -> {
                                if (favouritesItem.getId() != 0) {
                                    notifyItemChanged(position);
                                }
                            },
                            Timber::e));
        }
    }


    public interface OnItemClickCallback {
        void setDetailView(FavouritesItem favouritesItem);
    }
}
