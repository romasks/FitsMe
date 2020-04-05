package ru.fitsme.android.presentation.fragments.iteminfo;

import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.Constraints;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.iteminfo.states.AddToCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.InCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.ItemInfoState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.NoMatchSize;
import ru.fitsme.android.presentation.fragments.iteminfo.states.RemoveFromCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.SetSizeState;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemTouchListener;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;

public class ItemInfoFragment extends BaseFragment<ItemInfoViewModel>
        implements BindingEventsClickListener, ItemInfoTouchListener.Callback, ItemInfoState.StateSettable {

    @Inject
    IClothesInteractor clothesInteractor;

    private static ClotheInfo clotheInfo;
    private static int containerHeight;
    private static int containerWidth;
    private FragmentItemInfoBinding binding;
    private ItemInfoPictureHelper pictureHelper;
    private static boolean isFullState = false;
    private ItemInfoState itemInfoState;

    private static RateItemTouchListener rateItemTouchListener;

    public static ItemInfoFragment newInstance(ClotheInfo item,
                                               int containerHeight, int containerWidth,
                                               RateItemTouchListener rateItemTouchListener) {
        ItemInfoFragment.rateItemTouchListener = rateItemTouchListener;
        ItemInfoFragment fragment = new ItemInfoFragment();
        isFullState = false;
        ItemInfoFragment.clotheInfo = item;
        ItemInfoFragment.containerHeight = containerHeight;
        ItemInfoFragment.containerWidth = containerWidth;
        return fragment;
    }

    public static ItemInfoFragment newInstance(Object object) {
        ItemInfoFragment fragment = new ItemInfoFragment();
        isFullState = true;
        ItemInfoFragment.clotheInfo = (ClotheInfo) object;
        ItemInfoFragment.containerHeight = 0;
        ItemInfoFragment.containerWidth = 0;
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_item_info;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentItemInfoBinding.bind(view);
        binding.setBindingEvents(this);
        setUp();
        setFullState(isFullState);
    }

    private void setUp() {
        setMargins();

        if (clotheInfo.getClothe() == null) {
            onError(clotheInfo.getError());
        } else if (clotheInfo.getClothe() instanceof ClothesItem) {
            onClothesItem((ClothesItem) clotheInfo.getClothe());
            setListeners();
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            onLikedClothesItem();
            setListeners();
        } else {
            throw new TypeNotPresentException(clotheInfo.getClothe().toString(), null);
        }

        setItemInfoState();
    }

    private void setItemInfoState() {
        ClothesItem clothesItem;
        if (clotheInfo.getClothe() instanceof ClothesItem) {
            clothesItem = (ClothesItem) clotheInfo.getClothe();
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            LikedClothesItem likedItem = (LikedClothesItem) clotheInfo.getClothe();
            clothesItem = likedItem.getClothe();
        } else {
            throw new IllegalArgumentException("Inappropriate ClotheInfo");
        }

        if (clotheInfo.getState() == ClotheInfo.CART_STATE){
            setState(new RemoveFromCartState(clotheInfo, this, binding));
        } else if (clotheInfo.getState() == ClotheInfo.FAVOURITES_IN_CART_STATE){
            setState(new InCartState(clotheInfo, this, binding));
        } else if (clotheInfo.getState() == ClotheInfo.FAVOURITES_NOT_IN_CART_STATE){
            if (clothesItem.getSizeInStock() == ClothesItem.SizeInStock.YES){
                setState(new AddToCartState(clotheInfo, this, binding));
            } else if (clothesItem.getSizeInStock() == ClothesItem.SizeInStock.NO){
                setState(new NoMatchSize(clotheInfo, this, binding));
            } else if (clothesItem.getSizeInStock() == ClothesItem.SizeInStock.UNDEFINED){
                setState(new SetSizeState(clotheInfo, this, binding));
            }
        }
    }

    private void setListeners() {
        binding.itemInfoScrollView.setListener(rateItemTouchListener);
        binding.itemInfoScrollView.setListener(new ItemInfoTouchListener(this));
        setOnBrandNameTouchListener();
    }

    private void setMargins() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            float topMerge = -3.0f;

            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    topMerge,
                    r.getDisplayMetrics()
            );

            Constraints.LayoutParams params = new Constraints.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, px, 0, 0);
        }
    }

    private void onError(UserException error) {
        binding.itemInfoMessage.setText(error.getMessage());
        binding.itemInfoItemInfoCard.setVisibility(View.INVISIBLE);
    }

    private void onClothesItem(ClothesItem clothesItem) {
        binding.itemInfoMessage.setText(getString(R.string.loading));
        binding.itemInfoMessage.requestLayout();
        binding.itemInfoItemInfoCard.setVisibility(View.INVISIBLE);
        String brandName = clothesItem.getBrand();
        String name = clothesItem.getName();
        String description = clothesItem.getDescription();
        String url = clothesItem.getPics().get(0).getUrl();

        StringBuilder clotheContentStr = new StringBuilder();
        String itemsString = clothesItem.getMaterialPercentage().replaceAll("[{}]", "");
        for (String item : itemsString.split(",")) {
            String material = item.split(":")[0].replace("'", "").trim();
            String percent = item.split(":")[1].trim();
            clotheContentStr.append(percent).append("% ").append(material).append("\n");
        }

        binding.itemInfoBrandNameTv.setText(brandName);
        binding.itemInfoItemNameTv.setText(name);
        binding.itemInfoItemDescriptionTv.setText(description);
        binding.itemInfoItemContentTv.setText(clotheContentStr.toString());

        pictureHelper =
                new ItemInfoPictureHelper(this, binding, clothesItem, containerWidth, containerHeight);

    }

    private void onLikedClothesItem() {
        // TODO: 30.07.2019 Выполняется в случае отмены лайка. Не сделано еще
    }

    @Override
    public void onClickBrandName() {

    }

    @Override
    public void onClickAdd() {
        itemInfoState.onClickAdd();
    }

    @Override
    public void onClickRemove() {
        itemInfoState.onClickRemove();
    }

    private void setOnBrandNameTouchListener() {
        binding.itemInfoBrandNameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (binding.itemInfoBrandFieldDownArrow.getVisibility() == View.VISIBLE) {
                            ItemInfoFragment.this.setDetailState();
                        } else {
                            ItemInfoFragment.this.setSummaryState();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void showYes(boolean b, float alpha) {
        if (b) {
            binding.rateItemsYes.setAlpha(alpha);
            binding.rateItemsYes.setVisibility(View.VISIBLE);
            showNo(false);
        } else {
            binding.rateItemsYes.setVisibility(View.INVISIBLE);
        }
    }

    public void showYes(boolean b) {
        showYes(b, 1.0f);
    }

    public void showNo(boolean b, float alpha) {
        if (b) {
            binding.rateItemsNo.setVisibility(View.VISIBLE);
            binding.rateItemsNo.setAlpha(alpha);
            showYes(false);
        } else {
            binding.rateItemsNo.setVisibility(View.INVISIBLE);
        }
    }

    public void showNo(boolean b) {
        showNo(b, 1.0f);
    }

    private void setFullState(boolean b) {
        if (b) {
            setDetailState();
        } else {
            setSummaryState();
        }
    }

    private void setSummaryState() {
        binding.ivPhoto.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
        binding.ivPhoto.getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT;
        binding.ivPhoto.requestLayout();
        binding.itemInfoItemInfoCard.getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT;
        binding.itemInfoBrandFieldDownArrow.setVisibility(View.VISIBLE);
        binding.itemInfoBrandFieldUpArrow.setVisibility(View.INVISIBLE);
        binding.itemInfoItemDescriptionLayout.setVisibility(View.GONE);
        binding.itemInfoScrollView.setDetailState(false);
        if (getParentFragment() != null) {
            ((RateItemsFragment) getParentFragment()).setFullItemInfoState(false);
        }
        int paddingVal = App.getInstance().getResources().getDimensionPixelSize(R.dimen.item_info_card_padding);
        binding.itemInfoItemInfoContainer.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
        binding.itemInfoItemInfoCard.setRadius(App.getInstance().getResources().getDimension(R.dimen.item_info_card_radius));
        binding.itemInfoItemInfoCard.setCardElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
        binding.itemInfoBrandNameCard.setCardElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
    }

    private void setDetailState() {
        binding.itemInfoBrandFieldDownArrow.setVisibility(View.INVISIBLE);
        binding.itemInfoBrandFieldUpArrow.setVisibility(View.VISIBLE);
        binding.itemInfoItemDescriptionLayout.setVisibility(View.VISIBLE);
        binding.itemInfoScrollView.setDetailState(true);
        int paddingVal = 0;
        binding.itemInfoBrandNameCard.setCardElevation(0);
        binding.itemInfoItemInfoContainer.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
        binding.itemInfoItemInfoCard.setRadius(0);
        binding.itemInfoItemInfoCard.setCardElevation(0);
        binding.itemInfoItemInfoCard.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
        binding.ivPhoto.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
        binding.ivPhoto.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
        binding.ivPhoto.requestLayout();
        if (getParentFragment() != null) {
            if (getParentFragment() instanceof RateItemsFragment) {
                ((RateItemsFragment) getParentFragment()).setFullItemInfoState(true);
            } else if (getParentFragment() instanceof MainFragment) {
                binding.itemInfoBrandNameCard.setVisibility(View.GONE);
                binding.itemInfoBrandNameLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void nextPicture() {
        pictureHelper.setNextPicture();
    }

    @Override
    public void previousPicture() {
        pictureHelper.setPreviousPicture();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void setState(ItemInfoState state) {
        itemInfoState = state;
    }

    @Override
    public void finish() {
        onBackPressed();
    }


    public interface Callback {
        Single<OrderItem> add(ClothesItem clothesItem);
        void remove(ClothesItem clothesItem);
    }
}
