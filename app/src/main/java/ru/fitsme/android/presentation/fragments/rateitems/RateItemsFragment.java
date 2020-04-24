package ru.fitsme.android.presentation.fragments.rateitems;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentRateItemsBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheType;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;

public class RateItemsFragment extends BaseFragment<RateItemsViewModel>
        implements BindingEventsClickListener,
        RateItemTouchListener.Callback,
        RateItemAnimation.Callback,
        TopSizeDialogFragment.TopSizeDialogCallback,
        BottomSizeDialogFragment.BottomSizeDialogCallback {

    private static final String KEY_ITEM_INFO_STATE = "state";

    @Inject
    IClothesInteractor clothesInteractor;

    private ItemInfoFragment currentFragment;
    private FragmentRateItemsBinding binding;
    private RateItemAnimation itemAnimation;
    private boolean isFullItemInfoState;
    private RateItemTouchListener rateItemTouchListener;
    private ClotheInfo currentClotheInfo;

    private LiveData<Boolean> isNeedShowSizeDialogForTop;
    private LiveData<Boolean> isNeedShowSizeDialogForBottom;


    public static RateItemsFragment newInstance() {
        return new RateItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFullItemInfoState = getArguments().getBoolean(KEY_ITEM_INFO_STATE);
        } else {
            isFullItemInfoState = false;
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY_ITEM_INFO_STATE, isFullItemInfoState);
            setArguments(bundle);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_rate_items;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentRateItemsBinding.bind(view);
        binding.setBindingEvents(this);
        setUp();
        viewModel.onAfterCreateView();
        isNeedShowSizeDialogForTop = viewModel.getIsNeedShowSizeDialogForTop();
        isNeedShowSizeDialogForBottom = viewModel.getIsNeedShowSizeDialogForBottom();
    }

    private void setUp() {
        itemAnimation = new RateItemAnimation(this, binding);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getClotheInfoLiveData().observe(getViewLifecycleOwner(), this::onChange);
        viewModel.getFilterIconLiveData().observe(getViewLifecycleOwner(), this::onFilterIconChange);
    }

    private void onChange(ClotheInfo clotheInfo) {
        currentClotheInfo = clotheInfo;
        setClotheInfo(clotheInfo);
        setFullItemInfoState(false);
    }

    private void onFilterIconChange(Boolean isChecked) {
        if (isChecked) {
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.VISIBLE);
        } else {
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.INVISIBLE);
        }
    }

    private void setClotheInfo(ClotheInfo clotheInfo) {
        int containerWidth = binding.fragmentRateItemsContainer.getWidth();
        int containerHeight = getContainerHeight();

        //если убрать это условие, то изображение дергается,
        // потому что команда срабатывает когда контейнер еще не создан
        if (containerHeight != 0 && containerWidth != 0) {
            rateItemTouchListener = new RateItemTouchListener(this);
            currentFragment = ItemInfoFragment.newInstance(
                    clotheInfo,
                    containerHeight, containerWidth,
                    rateItemTouchListener
            );

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_rate_items_container, currentFragment)
                    .commit();
            resetContainerView();
        }
    }

    private int getContainerHeight() {
        int containerHeight;
        if (isFullItemInfoState) {
            int px = binding.fragmentRateItemsButtonsGroup.getHeight();
            int bottomPx = getParentFragment() == null ? 0 : ((MainFragment) getParentFragment()).getBottomNavigationSize();
            containerHeight = binding.fragmentRateItemsContainer.getHeight() - px - bottomPx;
        } else {
            containerHeight = binding.fragmentRateItemsContainer.getHeight();
        }
        return containerHeight;
    }

    @Override
    public void onBackPressed() {
        if (isFullItemInfoState) {
            setClotheInfo(currentClotheInfo);
            setFullItemInfoState(false);
        } else {
            viewModel.onBackPressed();
        }
    }

    @Override
    public void onClickLikeItem() {
        startToLikeItem();
    }

    @Override
    public void onClickReturn() {
        viewModel.onReturnClicked(currentClotheInfo);
    }

    @Override
    public void onClickDislikeItem() {
        startToDislikeItem();
    }

    @Override
    public void onClickFilter() {
        viewModel.onFilterClicked();
    }

    public void setFullItemInfoState(boolean b) {
        isFullItemInfoState = b;
        getArguments().putBoolean(KEY_ITEM_INFO_STATE, isFullItemInfoState);
        if (b) {
            binding.fragmentRateItemsReturnBtn.setVisibility(View.INVISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.INVISIBLE);
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.INVISIBLE);
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).showBottomNavigation(false);
            }
            setConstraintToFullState(true);
        } else {
            binding.fragmentRateItemsReturnBtn.setVisibility(View.VISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.VISIBLE);
            boolean filterIsChecked = viewModel.getFilterIconLiveData().getValue();
            if (filterIsChecked) {
                binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.VISIBLE);
            }
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).showBottomNavigation(true);
            }
            setConstraintToFullState(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setConstraintToFullState(boolean b) {
        ConstraintSet set = new ConstraintSet();
        set.clone(binding.rateItemsLayout);
        if (b) {
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        } else {
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, R.id.fragment_rate_items_buttons_group, ConstraintSet.TOP);
        }
        set.applyTo(binding.rateItemsLayout);
    }

    @Override
    public void maybeLikeItem(float alpha) {
        if (currentFragment != null && currentClotheInfo.getClothe() != null) {
            currentFragment.showYes(true, alpha);
        }
    }

    @Override
    public void startToLikeItem() {
        if (currentFragment != null && currentClotheInfo.getClothe() != null) {
            currentFragment.showYes(true);
            itemAnimation.moveViewOutOfScreenToRight();
        }
    }

    @Override
    public void maybeDislikeItem(float alpha) {
        if (currentFragment != null && currentClotheInfo.getClothe() != null) {
            currentFragment.showNo(true, alpha);
        }
    }

    @Override
    public void startToDislikeItem() {
        if (currentFragment != null && currentClotheInfo.getClothe() != null) {
            currentFragment.showNo(true);
            itemAnimation.moveViewOutOfScreenToLeft();
        }
    }

    @Override
    public void moveViewToXY(int deltaX, int deltaY) {
        itemAnimation.moveViewToXY(deltaX, deltaY);
    }

    @Override
    public void rotateView(float degrees) {
        itemAnimation.rotateView(degrees);
    }

    @Override
    public void resetContainerViewWithAnimation() {
        itemAnimation.resetContainerViewWithAnimation();
    }

    @Override
    public void resetContainerView() {
        itemAnimation.resetContainerView();
    }

    @Override
    public void likeItem() {
        showSizeDialog();
        if (currentFragment != null) {
            viewModel.likeClothesItem(currentClotheInfo, true);
        }
    }

    @Override
    public void dislikeItem() {
        if (currentFragment != null) {
            viewModel.likeClothesItem(currentClotheInfo, false);
        }
    }

    private void showSizeDialog() {
        if (isNeedShowSizeDialogForTop.getValue() != null && isNeedShowSizeDialogForBottom.getValue() != null &&
                (isNeedShowSizeDialogForTop.getValue() || isNeedShowSizeDialogForBottom.getValue())) {
            if (currentClotheInfo.getClothe() instanceof ClothesItem) {
                ClothesItem item = (ClothesItem) currentClotheInfo.getClothe();
                boolean sizeIsNotSet = (item.getSizeInStock() == ClothesItem.SizeInStock.UNDEFINED);
                ClotheType type = item.getClotheType();
                if (sizeIsNotSet) {
                    if (type.getType() == ClotheType.Type.TOP && isNeedShowSizeDialogForTop.getValue()) {
                        String message = App.getInstance().getString(R.string.rateitems_fragment_message_for_size_dialog);
                        TopSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "sizeDf");
                    }
                    if (type.getType() == ClotheType.Type.BOTTOM && isNeedShowSizeDialogForBottom.getValue()) {
                        String message = App.getInstance().getString(R.string.rateitems_fragment_message_for_size_dialog);
                        BottomSizeDialogFragment.newInstance(this, message).show(fragmentManager(), "sizeDf");
                    }
                }
            }
        }
    }

    private FragmentManager fragmentManager() {
        return ((AppCompatActivity) binding.getRoot().getContext()).getSupportFragmentManager();
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
}
