package ru.fitsme.android.presentation.fragments.rateitems;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentRateItemsBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheType;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.profile.view.BottomSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.profile.view.TopSizeDialogFragment;
import ru.fitsme.android.presentation.fragments.rateItemsdetail.RateItemsDetailFragment;

public class RateItemsFragment extends BaseFragment<RateItemsViewModel>
        implements BindingEventsClickListener,
        RateItemsDetailFragment.Callback,
        RateItemTouchListener.Callback,
        RateItemAnimation.Callback,
        TopSizeDialogFragment.TopSizeDialogCallback,
        BottomSizeDialogFragment.BottomSizeDialogCallback {

    private static final String KEY_ITEM_INFO_STATE = "state";

    @Inject
    IClothesInteractor clothesInteractor;

    private FragmentRateItemsBinding binding;
    private RateItemAnimation itemAnimation;
    private boolean isFullItemInfoState;
    private ClothesItem clothesItem;

    private LiveData<Boolean> isNeedShowSizeDialogForTop;
    private LiveData<Boolean> isNeedShowSizeDialogForBottom;

    private RateItemPictureHelper pictureHelper;

    public static RateItemsFragment newInstance() {
        return new RateItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (viewModel.isItFirstStart()){
            binding.fragmentRateItemsContainer.setVisibility(View.GONE);
            binding.fragmentRateItemsContainerDemo.setVisibility(View.VISIBLE);
        }
        binding.fragmentRateItemsMessage.setText(getString(R.string.loading));
        isNeedShowSizeDialogForTop = viewModel.getIsNeedShowSizeDialogForTop();
        isNeedShowSizeDialogForBottom = viewModel.getIsNeedShowSizeDialogForBottom();
        setListeners();
    }

    private void setUp() {
        itemAnimation = new RateItemAnimation(this, binding);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getClotheInfoLiveData().observe(getViewLifecycleOwner(), this::onChange);
        viewModel.getFilterIconLiveData().observe(getViewLifecycleOwner(), this::onFilterIconChange);
        viewModel.getReturnIconLiveData().observe(getViewLifecycleOwner(), this::onReturnIconChange);
    }

    private void onChange(ClotheInfo clotheInfo) {
        setSummaryItemInfoState();
        binding.fragmentRateItemsMessage.setText(getString(R.string.loading));
        binding.fragmentRateItemsInfoCard.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsBrandNameCard.setVisibility(View.INVISIBLE);
        setClotheInfo(clotheInfo);
    }

    private void onFilterIconChange(Boolean isChecked) {
        if (isChecked) {
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.VISIBLE);
        } else {
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.INVISIBLE);
        }
    }

    private void onReturnIconChange(Boolean isEnabled) {
        binding.fragmentRateItemsReturnBtn.setEnabled(isEnabled);
    }

    private void setListeners() {
        binding.fragmentRateItemsInfoCard.setOnTouchListener(new RateItemTouchListener(this));
        binding.fragmentRateItemsContainerDemo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewModel.setFirstStartCompleted();
                binding.fragmentRateItemsContainer.setVisibility(View.VISIBLE);
                binding.fragmentRateItemsContainerDemo.setVisibility(View.GONE);
                return true;
            }
        });
    }

    private void setClotheInfo(ClotheInfo clotheInfo) {
        resetContainerView();
        if (clotheInfo.getClothe() == null) {
            onError(clotheInfo.getError());
        } else if (clotheInfo.getClothe() instanceof ClothesItem) {
            onClothesItem((ClothesItem) clotheInfo.getClothe());
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            onLikedClothesItem();
        } else {
            throw new TypeNotPresentException(clotheInfo.getClothe().toString(), null);
        }
    }

    private void onClothesItem(ClothesItem clothesItem) {
        this.clothesItem = clothesItem;
        pictureHelper = new RateItemPictureHelper(this, binding, clothesItem);
    }

    private void onLikedClothesItem() {
    }

    private void onError(UserException error) {
        this.clothesItem = null;
        binding.fragmentRateItemsMessage.setText(error.getMessage());
        binding.fragmentRateItemsInfoCard.setVisibility(View.INVISIBLE);
    }

    private void showYes(boolean b, float alpha) {
        if (b) {
            binding.fragmentRateItemsYes.setAlpha(alpha);
            binding.fragmentRateItemsYes.setVisibility(View.VISIBLE);
            showNo(false);
        } else {
            binding.fragmentRateItemsYes.setVisibility(View.INVISIBLE);
        }
    }

    private void showYes(boolean b) {
        showYes(b, 1.0f);
    }

    private void showNo(boolean b, float alpha) {
        if (b) {
            binding.fragmentRateItemsNo.setVisibility(View.VISIBLE);
            binding.fragmentRateItemsNo.setAlpha(alpha);
            showYes(false);
        } else {
            binding.fragmentRateItemsNo.setVisibility(View.INVISIBLE);
        }
    }

    private void showNo(boolean b) {
        showNo(b, 1.0f);
    }

    @Override
    public void onBackPressed() {
        if (isFullItemInfoState) {
            closeRateItemsDetailFragment();
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
        viewModel.onReturnClicked(new ClotheInfo<ClothesItem>(clothesItem));
    }

    @Override
    public void onClickDislikeItem() {
        startToDislikeItem();
    }

    @Override
    public void onClickFilter() {
        viewModel.onFilterClicked();
    }

    @Override
    public void onClickBrandName() {
        setDetailItemInfoState();
    }

    private void setSummaryItemInfoState() {
        isFullItemInfoState = false;
        showSummaryStateViews();
        setConstraintToFullState(false);
    }

    private void showSummaryStateViews() {
        binding.fragmentRateItemsInfoCard.setVisibility(View.VISIBLE);
        binding.fragmentRateItemsBrandNameCard.setVisibility(View.VISIBLE);
        binding.fragmentRateItemsReturnBtn.setVisibility(View.VISIBLE);
        binding.fragmentRateItemsFilterBtn.setVisibility(View.VISIBLE);
        boolean filterIsChecked = viewModel.getFilterIconLiveData().getValue();
        if (filterIsChecked) {
            binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.VISIBLE);
        }
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(true);
        }
    }

    private void setDetailItemInfoState() {
        isFullItemInfoState = true;
        hideSummaryStateViews();
        setConstraintToFullState(true);
        RateItemsDetailFragment fragment = RateItemsDetailFragment.newInstance(clothesItem, this);
        fragmentManager().beginTransaction()
                .add(R.id.fragment_rate_items_container, fragment)
                .commit();
    }

    private void hideSummaryStateViews() {
        binding.fragmentRateItemsInfoCard.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsBrandNameCard.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsReturnBtn.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsFilterBtn.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsFilterCheckedIv.setVisibility(View.INVISIBLE);
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setConstraintToFullState(boolean b) {
        ConstraintSet set = new ConstraintSet();
        set.clone(binding.fragmentRateItemsLayout);
        if (b) {
            int val = 0;
            binding.fragmentRateItemsContainer.setPadding(val, val, val, val);
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        } else {
            int val = App.getInstance().getResources().getDimensionPixelSize(R.dimen.rate_items_card_padding);
            binding.fragmentRateItemsContainer.setPadding(val, val, val, val);
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, R.id.fragment_rate_items_buttons_group, ConstraintSet.TOP);
        }
        set.applyTo(binding.fragmentRateItemsLayout);
    }

    @Override
    public void maybeLikeItem(float alpha) {
        if (clothesItem != null) {
            showYes(true, alpha);
        }
    }

    @Override
    public void startToLikeItem() {
        if (clothesItem != null) {
            showYes(true);
            itemAnimation.moveViewOutOfScreenToRight();
        }
    }

    @Override
    public void maybeDislikeItem(float alpha) {
        if (clothesItem != null) {
            showNo(true, alpha);
        }
    }

    @Override
    public void startToDislikeItem() {
        if (clothesItem != null) {
            showNo(true);
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
        binding.fragmentRateItemsUpperPicCountIndicatorLl.removeAllViews();
        showYes(false);
        showNo(false);
        itemAnimation.resetContainerView();
    }

    @Override
    public void previousPicture() {
        pictureHelper.setPreviousPicture();
    }

    @Override
    public void nextPicture() {
        pictureHelper.setNextPicture();
    }

    @Override
    public void likeItem() {
        showSizeDialog();
        if (isFullItemInfoState) {
            closeRateItemsDetailFragment();
        }
        viewModel.likeClothesItem(new ClotheInfo<ClothesItem>(clothesItem), true);
    }

    @Override
    public void dislikeItem() {
        if (isFullItemInfoState) {
            closeRateItemsDetailFragment();
        }
        viewModel.likeClothesItem(new ClotheInfo<ClothesItem>(clothesItem), false);
    }

    private void showSizeDialog() {
        if (isNeedShowSizeDialogForTop.getValue() != null && isNeedShowSizeDialogForBottom.getValue() != null &&
                (isNeedShowSizeDialogForTop.getValue() || isNeedShowSizeDialogForBottom.getValue())) {
            ClothesItem item = (ClothesItem) clothesItem;
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

    @Override
    public void closeRateItemsDetailFragment() {
        Fragment fragment = fragmentManager().findFragmentById(R.id.fragment_rate_items_container);
        if (fragment != null) {
            fragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        setSummaryItemInfoState();
    }
}
