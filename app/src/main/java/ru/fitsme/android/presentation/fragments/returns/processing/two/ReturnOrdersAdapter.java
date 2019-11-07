package ru.fitsme.android.presentation.fragments.returns.processing.two;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemReturnOrderBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;

public class ReturnOrdersAdapter extends PagedListAdapter<ReturnsItem, ReturnOrdersAdapter.ReturnOrdersViewHolder> {

    private ChooseOrderReturnViewModel viewModel;

    ReturnOrdersAdapter(ChooseOrderReturnViewModel viewModel) {
        super(ChooseOrderReturnFragment.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReturnOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReturnOrderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_return_order, parent, false);
        return new ReturnOrdersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnOrdersViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ReturnOrdersViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;
//        TextView returnStatus;

        ReturnOrdersViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            returnStatus = binding.getRoot().findViewById(R.id.item_return_status_value);
        }

        void bind(int position) {
            ReturnsItem returnsItem = getItem(position);

            /*returnStatus.setTextColor(binding.getRoot().getResources().getColor(
                    returnOrdersItem.getStatus().equals("выполнено") ?
                            android.R.color.holo_green_dark :
                            returnOrdersItem.getStatus().equals("отказ") ?
                                    android.R.color.holo_red_dark :
                                    android.R.color.black
            ));*/

            binding.setVariable(BR.returnsItem, returnsItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
