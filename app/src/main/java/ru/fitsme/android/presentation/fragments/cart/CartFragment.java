package ru.fitsme.android.presentation.fragments.cart;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.ButtonState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.NormalState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.RemoveNoMatchSizeState;
import ru.fitsme.android.presentation.fragments.cart.buttonstate.SetSizeState;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;
import timber.log.Timber;

public class CartFragment extends BaseFragment<CartViewModel>
        implements CartBindingEvents,
        CartRecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        CartAdapter.OnItemClickCallback,
        TopSizeDialogFragment.TopSizeDialogCallback,
        BottomSizeDialogFragment.BottomSizeDialogCallback {

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
        viewModel.goToCheckout();
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
        viewModel.setDetailView(orderItem);
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
