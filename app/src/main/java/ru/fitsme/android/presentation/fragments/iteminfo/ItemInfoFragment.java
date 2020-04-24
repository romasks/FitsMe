package ru.fitsme.android.presentation.fragments.iteminfo;

import android.view.View;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.iteminfo.states.AddToCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.InCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.ItemInfoState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.NoMatchSize;
import ru.fitsme.android.presentation.fragments.iteminfo.states.RemoveFromCartState;
import ru.fitsme.android.presentation.fragments.iteminfo.states.SetSizeState;
import ru.fitsme.android.presentation.fragments.returns.ReturnsBindingEvents;

public class ItemInfoFragment extends BaseFragment<ItemInfoViewModel>
        implements BindingEventsClickListener, BackClickListener, ItemInfoTouchListener.Callback, ItemInfoState.StateSettable {

    @Inject
    IClothesInteractor clothesInteractor;

    private static ClotheInfo clotheInfo;
    private FragmentItemInfoBinding binding;
    private ItemInfoPictureHelper pictureHelper;
    private ItemInfoState itemInfoState;

    public static ItemInfoFragment newInstance(Object object) {
        ItemInfoFragment fragment = new ItemInfoFragment();
        ItemInfoFragment.clotheInfo = (ClotheInfo) object;
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
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.item_info_title));
        setUp();
    }

    private void setUp() {
        if (clotheInfo.getClothe() == null) {
            onError(clotheInfo.getError());
        } else if (clotheInfo.getClothe() instanceof ClothesItem) {
            onClothesItem((ClothesItem) clotheInfo.getClothe());
            setListeners();
        }
//        else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
//            onLikedClothesItem();
//            setListeners();
//        }
        else {
            throw new TypeNotPresentException(clotheInfo.getClothe().toString(), null);
        }
        setItemInfoState();
    }

    private void setItemInfoState() {
        ClothesItem clothesItem;
        if (clotheInfo.getClothe() instanceof ClothesItem) {
            clothesItem = (ClothesItem) clotheInfo.getClothe();
        }
//        else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
//            LikedClothesItem likedItem = (LikedClothesItem) clotheInfo.getClothe();
//            clothesItem = likedItem.getClothe();
//        }
        else {
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
        binding.itemInfoImageContainer.setOnTouchListener(new ItemInfoTouchListener(this));
    }

    private void onError(UserException error) {
        binding.itemInfoMessage.setText(error.getMessage());
        binding.itemInfoImageContainer.setVisibility(View.INVISIBLE);
        binding.itemInfoItemInfoContainer.setVisibility(View.INVISIBLE);
        binding.itemInfoItemDescriptionLayout.setVisibility(View.INVISIBLE);
    }

    private void onClothesItem(ClothesItem clothesItem) {
        binding.itemInfoMessage.setText(getString(R.string.loading));
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

        binding.itemInfoImageContainer.setVisibility(View.VISIBLE);
        binding.itemInfoItemInfoContainer.setVisibility(View.VISIBLE);
        binding.itemInfoItemDescriptionLayout.setVisibility(View.VISIBLE);

        pictureHelper =
                new ItemInfoPictureHelper(this, binding, clothesItem);
    }

    @Override
    public void onClickAdd() {
        itemInfoState.onClickAdd();
    }

    @Override
    public void onClickRemove() {
        itemInfoState.onClickRemove();
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

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }


    public interface Callback {
        Single<OrderItem> add(ClothesItem clothesItem);
        void remove(ClothesItem clothesItem);
    }
}
