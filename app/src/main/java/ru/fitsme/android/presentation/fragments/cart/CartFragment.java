package ru.fitsme.android.presentation.fragments.cart;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.ButtonState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.NormalState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.RemoveNoMatchSizeState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.SetSizeState;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;
import timber.log.Timber;

public class CartFragment extends BaseFragment<CartViewModel>
        implements CartBindingEvents,
        CartRecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        CartAdapter.OnItemClickCallback,
        TopSizeDialogFragment.TopSizeDialogCallback,
        BottomSizeDialogFragment.BottomSizeDialogCallback,
        ItemInfoFragment.Callback {

    private FragmentCartBinding binding;
    private CartAdapter adapter;
    private ButtonState state;

    private String topSize = "";
    private String bottomSize = "";

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentCartBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setUp();
    }

    private void setUp() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new CartRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.cartListRv);
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new CartAdapter(viewModel, this);

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getPageLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);
        viewModel.getCartIsEmpty().observe(getViewLifecycleOwner(), this::onCartIsEmpty);
        viewModel.getCurrentTopSize().observe(getViewLifecycleOwner(), value -> {
            topSize = value;
            adapter.notifyDataSetChanged();
        });
        viewModel.getCurrentBottomSize().observe(getViewLifecycleOwner(), value -> {
            bottomSize = value;
            adapter.notifyDataSetChanged();
        });
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomShadow(pagedList == null || pagedList.size() == 0);
        }
        adapter.submitList(pagedList);
        updateButtonState(pagedList);
    }

    private void onCartIsEmpty(Boolean isEmpty) {
        if (isEmpty) {
            binding.cartNoItemGroup.setVisibility(View.VISIBLE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.GONE);
            binding.cartProceedToCheckoutShadow.setVisibility(View.GONE);
        } else {
            binding.cartNoItemGroup.setVisibility(View.GONE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.VISIBLE);
            binding.cartProceedToCheckoutShadow.setVisibility(View.VISIBLE);
            updateButtonState(adapter.getCurrentList());
        }
    }

    @Override
    public void onClickGoToCheckout() {
        state.onButtonClick(viewModel, this);
    }

    public void goToCheckout() {
        viewModel.goToCheckout();
    }

    @Override
    public void onClickGoToFavourites() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToFavourites();
        }
    }

    @Override
    public void onClickGoToRateItems() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToRateItems();
        }
    }

    @Override
    public void onDestroyView() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomShadow(true);
        }
        super.onDestroyView();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (position != RecyclerView.NO_POSITION) {
            viewModel.removeItemFromOrder(position)
                    .subscribe(removedOrderItem -> {
                        if (removedOrderItem.getId() != 0) {
                            adapter.notifyItemChanged(position);
                        }
                    }, Timber::e);
        }
    }

    @Override
    public void setDetailView(OrderItem orderItem) {
        ClothesItem clothesItem = orderItem.getClothe();
        ClotheInfo clotheInfo = new ClotheInfo<ClothesItem>(clothesItem, ClotheInfo.CART_STATE);
        clotheInfo.setCallback(this);
        viewModel.setDetailView(clotheInfo);
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

    @Override
    public Single<OrderItem> add(ClothesItem clothesItem) {
        // В корзине кнопка add не задействована
        return Single.just(new OrderItem());
    }

    @SuppressLint("CheckResult")
    @Override
    public void remove(ClothesItem clothesItem) {
        PagedList<OrderItem> pagedList = adapter.getCurrentList();
        if (pagedList != null) {
            for (int i = 0; i < pagedList.size(); i++) {
                final int j = i;
                OrderItem orderItem = pagedList.get(i);
                if (orderItem != null && clothesItem.getId() == orderItem.getClothe().getId()) {
                    viewModel.removeItemFromOrder(j)
                            .subscribe(removedItem -> {
                                if (removedItem.getId() != 0) {
//                                    adapter.notifyItemChanged(j);
                                }
                            }, Timber::e);
                }
            }
        }
    }

    public void handleButtonClick() {
        state.onButtonClick(viewModel, this);
    }

    public void removeNoSizeItems() {
        viewModel.removeNoSizeItems(adapter.getNoSizeItemsIds());
    }

    public void setSizeInProfile() {
        if (isNeededSetSizeTop()) showTopSizeDialog();
        else if (isNeededSetSizeBottom()) showBottomSizeDialog();
        else updateButtonState(adapter.getCurrentList());
    }

    private void updateButtonState(PagedList<OrderItem> orderItemsList) {
        if (isNeededSetSizeTop() || isNeededSetSizeBottom()) {
            setState(new SetSizeState(binding, this));
        } else if (hasNoSizeItems(orderItemsList)) {
            setState(new RemoveNoMatchSizeState(binding, this));
        } else {
            setState(new NormalState(binding, this));
        }
    }

    private boolean isNeededSetSizeTop() {
        return adapter.hasTopSizeItems();
//        return viewModel.getCurrentTopSizeIndex().get() == SizeObserver.NO_SIZE;
    }

    private boolean isNeededSetSizeBottom() {
        return adapter.hasBottomSizeItems();
//        return viewModel.getCurrentBottomSizeIndex().get() == SizeObserver.NO_SIZE;
    }

    private Boolean hasNoSizeItems(PagedList<OrderItem> orderItemsList) {
        /*if (orderItemsList == null) return false;
        for (OrderItem item : orderItemsList.snapshot()) {
            if (item.getClothe().getSizeInStock() == ClothesItem.SizeInStock.NO) return true;
        }
        return false;*/
        return adapter.hasNoSizeItems();
    }

    private void setState(ButtonState buttonState) {
        state = buttonState;
    }

    private void showTopSizeDialog() {
        String message = App.getInstance().getString(R.string.cart_fragment_message_for_size_dialog);
        TopSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "topSizeDf");
    }

    private void showBottomSizeDialog() {
        String message = App.getInstance().getString(R.string.cart_fragment_message_for_size_dialog);
        BottomSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "bottomSizeDf");
    }

    private FragmentManager fragmentManager() {
        return ((AppCompatActivity) binding.getRoot().getContext()).getSupportFragmentManager();
    }


    String getTopSize() {
        return topSize;
    }

    String getBottomSize() {
        return bottomSize;
    }
}
