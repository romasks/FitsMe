package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;

public class ItemInfoFragment extends BaseFragment<ItemInfoViewModel>
            implements BindingEventsClickListener{

    @Inject
    IClothesInteractor clothesInteractor;

    private static ClotheInfo clotheInfo;
    private static boolean isFullState;
    private FragmentItemInfoBinding binding;

    public static ItemInfoFragment newInstance(ClotheInfo item, boolean isFullItemInfoState) {
        ItemInfoFragment fragment = new ItemInfoFragment();

        clotheInfo = item;
        isFullState = isFullItemInfoState;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_info, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFullState(isFullState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(clothesInteractor)).get(ItemInfoViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        if (clotheInfo.getClothe() == null){
            onError(clotheInfo.getError());
        } else if (clotheInfo.getClothe() instanceof ClothesItem) {
            onClothesItem((ClothesItem) clotheInfo.getClothe());
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            onLikedClothesItem();
        }
    }

    private void onError(UserException error) {
        binding.tvIndex.setText(error.getMessage());
    }

    private void onClothesItem(ClothesItem clothesItem) {
            binding.tvIndex.setText("loading");
            String brandName = clothesItem.getBrand();
            String name = clothesItem.getName();
            String description = clothesItem.getDescription();
            List<String> contentList = clothesItem.getMaterial();
            String content = contentList.toString();
            String url = clothesItem.getPics().get(0).getUrl();

            binding.itemInfoBrandNameTv.setText(brandName);
            binding.itemInfoItemNameTv.setText(name);
            binding.itemInfoItemDescriptionTv.setText(description);
            binding.itemInfoItemContentTv.setText(content);
            Glide.with(binding.ivPhoto.getContext())
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.tvIndex.setText(App.getInstance().getString(R.string.image_loading_error));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.tvIndex.setText("");
                            return false;
                        }
                    })
                    .into(binding.ivPhoto);
    }

    private void onLikedClothesItem() {
        // TODO: 30.07.2019 Выполняется в случае отмены лайка. Не сделано еще
    }

    @Override
    public void onClickBrandName() {
        if(binding.itemInfoBrandFieldDownArrow.getVisibility() == View.VISIBLE){
            setFullState(true);
        } else {
            setFullState(false);
        }
    }

    public void showYes(boolean b){
        if (b){
            binding.rateItemsYes.setVisibility(View.VISIBLE);
            showNo(false);
        } else {
            binding.rateItemsYes.setVisibility(View.INVISIBLE);
        }
    }

    public void showNo(boolean b){
        if (b){
            binding.rateItemsNo.setVisibility(View.VISIBLE);
            showYes(false);
        } else {
            binding.rateItemsNo.setVisibility(View.INVISIBLE);
        }
    }

    private void setFullState(boolean b){
        if (b){
            isFullState = true;
            binding.itemInfoBrandFieldDownArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.VISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.VISIBLE);
            ((RateItemsFragment) getParentFragment()).setFullItemInfoState(true);
            binding.itemInfoItemInfoContainer.setPadding(0,0,0,0);
            binding.itemInfoItemInfoCard.setRadius(0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                binding.itemInfoItemInfoCard.setElevation(0);
                binding.itemInfoBrandNameCard.setElevation(0);
            }
        } else {
            isFullState = false;
            binding.itemInfoBrandFieldDownArrow.setVisibility(View.VISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.GONE);
            ((RateItemsFragment) getParentFragment()).setFullItemInfoState(false);
            int paddingVal = App.getInstance().getResources().getDimensionPixelSize(R.dimen.item_info_card_padding);
            binding.itemInfoItemInfoContainer.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
            binding.itemInfoItemInfoCard.setRadius(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                binding.itemInfoItemInfoCard.setElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
                binding.itemInfoBrandNameCard.setElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
            }
        }
    }
}
