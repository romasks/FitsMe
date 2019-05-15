package ru.fitsme.android.presentation.fragments.cart.view;

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
import ru.fitsme.android.databinding.ItemCartBinding;
import ru.fitsme.android.databinding.ItemFavouriteBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.favourites.view.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.favourites.view.FavouritesViewModel;
import timber.log.Timber;

public class CartAdapter extends PagedListAdapter<OrderItem, CartAdapter.GenericViewHolder> {

    private CartViewModel viewModel;

    CartAdapter(CartViewModel viewModel) {
        super(CartFragment.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CartAdapter.GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);
        return new CartAdapter.GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.GenericViewHolder holder, int position) {
        holder.bind(position);
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            Timber.d("onBind %s", position);
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.model, viewModel);
            binding.executePendingBindings();

            OrderItem orderItem = getItem(position);
            ClothesItem clothesItem = Objects.requireNonNull(orderItem).getClothe();

            String imageUrl = clothesItem.getPics()
                    .get(0)
                    .getUrl()
                    .replace("random", "image=");
            imageUrl += clothesItem.getId() % 400;

            ImageView imageView = binding.getRoot().findViewById(R.id.item_cart_image);
            Glide.with(imageView)
                    .load(imageUrl)
                    .placeholder(R.drawable.clother_example)
                    .into(imageView);

            TextView brandName = binding.getRoot().findViewById(R.id.item_cart_brand_name);
            TextView name = binding.getRoot().findViewById(R.id.item_cart_name);
            TextView price = binding.getRoot().findViewById(R.id.item_cart_price);

            brandName.setText(clothesItem.getBrand());
            name.setText(clothesItem.getName());
            price.setText(clothesItem.getDescription());
        }
    }
}
