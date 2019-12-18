package ru.fitsme.android.presentation.fragments.cart;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

public class CartFragment extends BaseFragment<CartViewModel>
        implements CartBindingEvents,
        CartRecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        CartAdapter.OnItemClickCallback {

    private FragmentCartBinding binding;
    private CartAdapter adapter;

    public CartFragment() {
        App.getInstance().getDi().inject(this);
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
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new CartAdapter(viewModel, this);

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getPageLiveData().observe(this, this::onLoadPage);
        viewModel.getCartIsEmpty().observe(this, this::onCartIsEmpty);
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomShadow(pagedList == null || pagedList.size() == 0);
        }
        adapter.submitList(pagedList);
    }

    private void onCartIsEmpty(Boolean b) {
        if (b) {
            binding.cartNoItemGroup.setVisibility(View.VISIBLE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.GONE);
        } else {
            binding.cartNoItemGroup.setVisibility(View.GONE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickGoToCheckout() {
        viewModel.goToCheckout();
        /*if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToCheckout();
        }*/
    }

    @Override
    public void onClickGoToFavourites() {
        viewModel.goToFavourites();
        /*if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToFavourites();
        }*/
    }

    @Override
    public void onClickGoToRateItems() {
        viewModel.goToRateItems();
        /*if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToRateItems();
        }*/
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
}
