package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseItemBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class ChooseItemReturnFragment extends BaseFragment<ChooseItemReturnViewModel> implements ChooseItemReturnBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private static final String KEY_RETURN_ITEM = "RETURN_ITEM";

    private FragmentReturnChooseItemBinding binding;
    private ReturnOrderItemsAdapter adapter;
    private ReturnsItem returnsItem;

    public static ChooseItemReturnFragment newInstance(ReturnsItem returnsItem) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_RETURN_ITEM, returnsItem);
        ChooseItemReturnFragment fragment = new ChooseItemReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_choose_item, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_items_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            returnsItem = getArguments().getParcelable(KEY_RETURN_ITEM);
            binding.setReturnsItem(returnsItem);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(ChooseItemReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrderItemsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);

        adapter.setItems(returnsItem.getItems());
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsChooseOrder();
    }

    @Override
    public void onNext() {
        viewModel.goToReturnsIndicateNumber();
    }
}
