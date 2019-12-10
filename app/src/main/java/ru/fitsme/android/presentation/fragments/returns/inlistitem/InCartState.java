package ru.fitsme.android.presentation.fragments.returns.inlistitem;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.presentation.fragments.returns.ReturnsAdapter;
import ru.fitsme.android.presentation.fragments.returns.ReturnsViewModel;

public class InCartState extends InListItemState {
    public InCartState(ReturnsAdapter.InListViewHolder viewHolder) {
        super(viewHolder);
        this.viewHolder.button.setAlpha(1f);
        this.viewHolder.button.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.viewHolder.button.setEnabled(true);
        this.viewHolder.button.setText("Продолжить оформление");
        this.viewHolder.button.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(ReturnsViewModel viewModel, int position, int returnsId) {
        viewModel.goToCheckout();
    }
}
