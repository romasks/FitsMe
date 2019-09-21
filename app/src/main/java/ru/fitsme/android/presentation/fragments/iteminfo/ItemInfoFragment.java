package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
        implements BindingEventsClickListener {

    @Inject
    IClothesInteractor clothesInteractor;

    private static ClotheInfo clotheInfo;
    private static boolean isFullState;
    private static int containerHeight;
    private static int containerWidth;
    private FragmentItemInfoBinding binding;

    public static ItemInfoFragment newInstance(ClotheInfo item, boolean isFullItemInfoState,
                                               int containerHeight, int containerWidth) {
        ItemInfoFragment fragment = new ItemInfoFragment();

        ItemInfoFragment.clotheInfo = item;
        ItemInfoFragment.isFullState = isFullItemInfoState;
        ItemInfoFragment.containerHeight = containerHeight;
        ItemInfoFragment.containerWidth = containerWidth;
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

        setMargins();
        setFullState(isFullState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(clothesInteractor)).get(ItemInfoViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        if (clotheInfo.getClothe() == null) {
            onError(clotheInfo.getError());
        } else if (clotheInfo.getClothe() instanceof ClothesItem) {
            onClothesItem((ClothesItem) clotheInfo.getClothe());
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            onLikedClothesItem();
        }
    }

    private void setMargins() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
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
        binding.tvIndex.setText(error.getMessage());
    }

    private void onClothesItem(ClothesItem clothesItem) {
        binding.tvIndex.setText(getString(R.string.loading));
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

        Glide.with(binding.ivPhoto.getContext())
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        binding.tvIndex.setText(App.getInstance().getString(R.string.image_loading_error));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        binding.tvIndex.setText("");
                        binding.itemInfoItemInfoCard.setVisibility(View.VISIBLE);
                        binding.itemInfoBrandNameCard.setVisibility(View.VISIBLE);
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
        if (binding.itemInfoBrandFieldDownArrow.getVisibility() == View.VISIBLE) {
            setFullState(true);
        } else {
            setFullState(false);
        }
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

    public void showYes(boolean b){
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

    public void showNo(boolean b){
        showNo(b, 1.0f);
    }

    private void setFullState(boolean b) {
        if (b) {
            isFullState = true;

            binding.itemInfoBrandFieldDownArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.VISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.VISIBLE);
            ((RateItemsFragment) getParentFragment()).setFullItemInfoState(true);
            int paddingVal = 0;
            binding.itemInfoItemInfoContainer.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
            binding.itemInfoItemInfoCard.setRadius(0);
            binding.itemInfoItemInfoCard.setCardElevation(0);
            binding.itemInfoBrandNameCard.setCardElevation(0);
            binding.itemInfoItemInfoCard.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
            binding.ivPhoto.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
            binding.ivPhoto.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
            binding.ivPhoto.requestLayout();
        } else {
            isFullState = false;

            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    App.getInstance().getResources().getDimensionPixelSize(R.dimen.item_info_card_padding),
                    r.getDisplayMetrics()
            );

            binding.ivPhoto.getLayoutParams().height = containerHeight - px;
            binding.ivPhoto.getLayoutParams().width = containerWidth - px;
            binding.ivPhoto.requestLayout();
//            binding.itemInfoItemInfoCard.getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT;
            binding.itemInfoBrandFieldDownArrow.setVisibility(View.VISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.GONE);
            ((RateItemsFragment) getParentFragment()).setFullItemInfoState(false);
            int paddingVal = App.getInstance().getResources().getDimensionPixelSize(R.dimen.item_info_card_padding);
            binding.itemInfoItemInfoContainer.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
            binding.itemInfoItemInfoCard.setRadius(App.getInstance().getResources().getDimension(R.dimen.item_info_card_radius));
            binding.itemInfoItemInfoCard.setCardElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
            binding.itemInfoBrandNameCard.setCardElevation(App.getInstance().getResources().getDimension(R.dimen.items_info_elevation));
        }
    }
}
