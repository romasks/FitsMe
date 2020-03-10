package ru.fitsme.android.presentation.fragments.returns.inlistitem;

import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.presentation.fragments.returns.ReturnsAdapter;
import ru.fitsme.android.presentation.fragments.returns.ReturnsViewModel;

public abstract class InListItemState {
    ReturnsAdapter.InListViewHolder viewHolder;

    InListItemState(ReturnsAdapter.InListViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public abstract void onClick(ReturnsViewModel viewModel, ReturnsOrder returnsOrder);
}
