package ru.fitsme.android.presentation.fragments.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemCartBinding;
import ru.fitsme.android.databinding.ItemCartRemovedBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.cart.orderstate.InOrderState;
import ru.fitsme.android.presentation.fragments.cart.orderstate.NoSizeState;
import ru.fitsme.android.presentation.fragments.cart.orderstate.OrderState;
import timber.log.Timber;

public class CartAdapter extends PagedListAdapter<OrderItem, CartAdapter.CartViewHolder> {

    private CartViewModel viewModel;
    private OnItemClickCallback callback;

    private static final int NORMAL_TYPE = 1;
    private static final int REMOVED_TYPE = 2;

    CartAdapter(CartViewModel viewModel, CartAdapter.OnItemClickCallback callback) {
        super(OrderItem.DIFF_CALLBACK);
        this.viewModel = viewModel;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == NORMAL_TYPE) {
            ItemCartBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);
            return new NormalViewHolder(binding);
        } else if (viewType == REMOVED_TYPE) {
            ItemCartRemovedBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart_removed, parent, false);
            return new RemovedViewHolder(binding);
        } else
            throw new IllegalArgumentException("Can't create view holder from view type " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
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

    boolean hasNoSizeItems() {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).getClothe().getSizeInStock() == ClothesItem.SizeInStock.NO)
                return true;
        }
        return false;
    }

    public void removeNoSizeItems() {
        // TODO: remove no size items
    }

    abstract class CartViewHolder extends RecyclerView.ViewHolder {
        CartViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }

    public class NormalViewHolder extends CartViewHolder {
        public final ViewDataBinding binding;
        final ImageView rightDeleteIcon;
        final ImageView leftDeleteIcon;
        final RelativeLayout viewBackground;
        final RelativeLayout viewForeground;
        public final ImageView imageView;
        public final TextView brandName;
        public final TextView name;
        public final TextView price;
        public final TextView noSize;

        private OrderItem orderItem;
        private OrderState state;

        NormalViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_cart_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_cart_view_foreground);
            rightDeleteIcon = binding.getRoot().findViewById(R.id.item_cart_delete_icon_right);
            leftDeleteIcon = binding.getRoot().findViewById(R.id.item_cart_delete_icon_left);
            imageView = binding.getRoot().findViewById(R.id.item_cart_image);
            brandName = binding.getRoot().findViewById(R.id.item_cart_brand_name);
            name = binding.getRoot().findViewById(R.id.item_cart_name);
            price = binding.getRoot().findViewById(R.id.item_cart_price);
            noSize = binding.getRoot().findViewById(R.id.item_cart_no_size);
        }

        void bind(int position) {
            orderItem = getItem(position);
            ClothesItem clothesItem = orderItem == null ? new ClothesItem() : orderItem.getClothe();

            setState(orderItem);

            binding.setVariable(BR.clotheItem, clothesItem);
            binding.executePendingBindings();
        }

        private void setState(@Nullable OrderItem orderItem) {
            if (orderItem == null) return;
            switch (orderItem.getClothe().getSizeInStock()) {
                case UNDEFINED:
                case YES: {
                    setState(new InOrderState(this, callback));
                    break;
                }
                case NO: {
                    setState(new NoSizeState(this, callback));
                    break;
                }
            }
        }

        private void setState(OrderState inOrderState) {
            state = inOrderState;
        }

        public OrderItem getOrderItem() {
            return orderItem;
        }
    }


    public class RemovedViewHolder extends CartViewHolder {
        final ViewDataBinding binding;

        RemovedViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        void bind(int position) {
            TextView undoButton = binding.getRoot().findViewById(R.id.item_cart_removed_back_tv);
            undoButton.setOnClickListener(v -> viewModel.restoreItemToOrder(position)
                    .subscribe(orderItem -> {
                        if (orderItem.getId() != 0) {
                            notifyItemChanged(position);
                        }
                    }, Timber::e));
        }
    }


    public interface OnItemClickCallback {
        void setDetailView(OrderItem orderItem);
    }
}
