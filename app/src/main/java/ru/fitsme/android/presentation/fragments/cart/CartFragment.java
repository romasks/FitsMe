package ru.fitsme.android.presentation.fragments.cart;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheType;
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

    private LiveData<Boolean> isNeedShowSizeDialogForTop;
    private LiveData<Boolean> isNeedShowSizeDialogForBottom;

    public CartFragment() {
        // App.getInstance().getDi().inject(this);
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

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

        isNeedShowSizeDialogForTop = viewModel.getIsNeedShowSizeDialogForTop();
        isNeedShowSizeDialogForBottom = viewModel.getIsNeedShowSizeDialogForBottom();
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
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomShadow(pagedList == null || pagedList.size() == 0);
        }
        adapter.submitList(pagedList);
        updateButtonState();
    }

    private void updateButtonState() {
        if (isNeedShowSizeDialogForTop.getValue()) {
            setState(new SetSizeState(binding, this));
        } else if (isNeedShowSizeDialogForBottom.getValue()) {
            setState(new SetSizeState(binding, this));
        } else if (adapter.hasNoSizeItems()) {
            setState(new RemoveNoMatchSizeState(binding, this));
        } else {
            setState(new NormalState(binding, this));
        }
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
        }
    }

    @Override
    public void onClickGoToCheckout() {
        boolean canGoToCheckout = true;
        PagedList<OrderItem> pagedList = adapter.getCurrentList();
        for (int i = 0; i < pagedList.size(); i++) {
            if (!viewModel.itemIsRemoved(i)) {
                ClothesItem clothesItem = pagedList.get(i).getClothe();
                if (clothesItem.getSizeInStock() == ClothesItem.SizeInStock.UNDEFINED) {
                    canGoToCheckout = false;
                    if (clothesItem.getClotheType().getType() == ClotheType.Type.TOP) {
                        showTopSizeDialog();
                    } else if (clothesItem.getClotheType().getType() == ClotheType.Type.BOTTOM) {
                        showBottomSizeDialog();
                    }
                } else if (clothesItem.getSizeInStock() == ClothesItem.SizeInStock.NO) {
                    canGoToCheckout = false;
                    Toast.makeText(getContext(), R.string.no_item_toast, Toast.LENGTH_LONG).show();
                }
            }
        }
        if (canGoToCheckout) {
            viewModel.goToCheckout();
        }
    }

    @Override
    public void onClickGoToFavourites() {
//        viewModel.goToFavourites();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToFavourites();
        }
    }

    @Override
    public void onClickGoToRateItems() {
//        viewModel.goToRateItems();
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


    private void showTopSizeDialog(){
        String message = App.getInstance().getString(R.string.cart_fragment_message_for_size_dialog);
        DialogFragment dialogFragment = TopSizeDialogFragment.newInstance(this, message);
        FragmentManager fm = ((AppCompatActivity) binding.getRoot().getContext()).getSupportFragmentManager();
        dialogFragment.show(fm, "topSizeDf");
    }

    private void showBottomSizeDialog(){
        String message = App.getInstance().getString(R.string.cart_fragment_message_for_size_dialog);
        DialogFragment dialogFragment = BottomSizeDialogFragment.newInstance(this, message);
        FragmentManager fm = ((AppCompatActivity) binding.getRoot().getContext()).getSupportFragmentManager();
        dialogFragment.show(fm, "bottomSizeDf");
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

    @Override
    public void onTopOkButtonClick() {
        viewModel.setIsNeedShowSizeDialogForTop(true);
    }

    @Override
    public void onTopCancelButtonClick() {
        viewModel.setIsNeedShowSizeDialogForTop(false);
    }

    @Override
    public void onBottomOkButtonClick() {
        viewModel.setIsNeedShowSizeDialogForBottom(true);
    }

    @Override
    public void onBottomCancelButtonClick() {
        viewModel.setIsNeedShowSizeDialogForBottom(false);
    }

    public void handleButtonClick() {
        state.onButtonClick(viewModel, this);
    }

    private void setState(ButtonState buttonState) {
        state = buttonState;
    }

    public void removeNoSizeItems() {
        adapter.removeNoSizeItems();
    }

    public void setSizeInProfile() {
        showSizeDialog();
    }

    private void showSizeDialog() {
        if (isNeedShowSizeDialogForTop.getValue()) {
            TopSizeDialogFragment.newInstance(this).show(fragmentManager(), "sizeDf");
        }
        if (isNeedShowSizeDialogForBottom.getValue()) {
            BottomSizeDialogFragment.newInstance(this).show(fragmentManager(), "sizeDf");
        }
    }

    private FragmentManager fragmentManager() {
        return ((AppCompatActivity) binding.getRoot().getContext()).getSupportFragmentManager();
    }
}
