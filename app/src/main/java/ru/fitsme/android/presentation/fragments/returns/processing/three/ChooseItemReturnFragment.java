package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseItemBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import timber.log.Timber;

public class ChooseItemReturnFragment extends BaseFragment<ChooseItemReturnViewModel> implements ChooseItemReturnBindingEvents, BackClickListener {

    private FragmentReturnChooseItemBinding binding;
    private ReturnOrderItemsAdapter adapter;

    public static ChooseItemReturnFragment newInstance() {
        return new ChooseItemReturnFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_return_choose_item;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnChooseItemBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_items_header));
    }

    @Override
    protected void setUpRecyclers() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrderItemsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getErrorMsgLiveData().observe(getViewLifecycleOwner(), this::onErrorMsg);
        viewModel.getOrderLiveData().observe(getViewLifecycleOwner(), this::onLoadOrder);
    }

    private void onLoadOrder(Order order) {
        binding.setReturnsOrder(order);
        adapter.setItems(order.getOrderItemList());
    }

    private void onErrorMsg(String msg) {
        if (msg.isEmpty()) return;
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onNext() {
        if (adapter.noItemsSelected()) {
            Timber.e("Ни одного элемента не выбрано");
            onErrorMsg("Ни одного элемента не выбрано");
        } else {
            viewModel.goToReturnsIndicateNumber();
        }
    }
}
