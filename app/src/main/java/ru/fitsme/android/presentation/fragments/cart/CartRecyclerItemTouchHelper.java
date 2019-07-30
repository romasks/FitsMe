package ru.fitsme.android.presentation.fragments.cart;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.presentation.fragments.favourites.FavouritesAdapter;


public class CartRecyclerItemTouchHelper extends
        ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    CartRecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((CartAdapter.GenericViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NotNull Canvas c, @NotNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CartAdapter.GenericViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CartAdapter.GenericViewHolder) viewHolder).viewForeground;
        final View rightDeleteIcon = ((CartAdapter.GenericViewHolder) viewHolder).rightDeleteIcon;
        final View leftDeleteIcon = ((CartAdapter.GenericViewHolder) viewHolder).leftDeleteIcon;
        rightDeleteIcon.setVisibility(View.INVISIBLE);
        leftDeleteIcon.setVisibility(View.INVISIBLE);
        foregroundView.setAlpha(1.0f);
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NotNull Canvas c, @NotNull RecyclerView recyclerView,
                            @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CartAdapter.GenericViewHolder) viewHolder).viewForeground;
        final View rightDeleteIcon = ((CartAdapter.GenericViewHolder) viewHolder).rightDeleteIcon;
        final View leftDeleteIcon = ((CartAdapter.GenericViewHolder) viewHolder).leftDeleteIcon;
        if (isCurrentlyActive){
            if (dX > 0){
                leftDeleteIcon.setVisibility(View.VISIBLE);
            }
            if (dX < 0){
                rightDeleteIcon.setVisibility(View.VISIBLE);
            }
            foregroundView.setAlpha(0.6f);
        }
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
