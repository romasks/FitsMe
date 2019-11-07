package ru.fitsme.android.presentation.fragments.returns;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemReturnBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.presentation.fragments.returns.inlistitem.InCartState;
import ru.fitsme.android.presentation.fragments.returns.inlistitem.InListItemState;
import ru.fitsme.android.presentation.fragments.returns.inlistitem.NormalState;

public class ReturnsAdapter extends PagedListAdapter<ReturnsItem, ReturnsAdapter.ReturnsViewHolder> {

    private ReturnsViewModel viewModel;

    ReturnsAdapter(ReturnsViewModel viewModel) {
        super(ReturnsFragment.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReturnsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReturnBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_return, parent, false);
        return new InListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnsViewHolder holder, int position) {
        holder.bind(position);
    }

    abstract class ReturnsViewHolder extends RecyclerView.ViewHolder {
        ReturnsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);
    }


    public class InListViewHolder extends ReturnsViewHolder {
        final public ViewDataBinding binding;
        final public Button button;
        TextView returnStatus;
        InListItemState state;

        InListViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            returnStatus = binding.getRoot().findViewById(R.id.item_return_status_value);
            button = binding.getRoot().findViewById(R.id.item_returns_btn);
        }

        @Override
        void bind(int position) {
            ReturnsItem returnsItem = getItem(position);

            button.setOnClickListener(view -> state.onClick(viewModel, position));

            if (returnsItem != null) {
                setItemState(returnsItem);
                returnStatus.setTextColor(binding.getRoot().getResources().getColor(
                        returnsItem.getStatus().equals("выполнено") ?
                                android.R.color.holo_green_dark :
                                returnsItem.getStatus().equals("отказ") ?
                                        android.R.color.holo_red_dark :
                                        android.R.color.black
                ));
            }

            binding.setVariable(BR.returnsItem, returnsItem);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

        private void setItemState(ReturnsItem returnsItem) {
            state = returnsItem.isInCart() ? new InCartState(this) : new NormalState(this);
        }
    }
}
