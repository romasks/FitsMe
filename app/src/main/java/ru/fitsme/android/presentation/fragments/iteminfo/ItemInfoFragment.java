package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.security.MessageDigest;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class ItemInfoFragment extends BaseFragment<ItemInfoViewModel>
            implements BindingEventsClickListener{

    @Inject
    IClothesInteractor clothesInteractor;

    private static final String INDEX_KEY = "indexKey";

    private int index;
    private ItemInfoState.State state;
    private FragmentItemInfoBinding binding;

    public static ItemInfoFragment newInstance(int index) {
        ItemInfoFragment fragment = new ItemInfoFragment();

        Bundle args = new Bundle();
        args.putInt(INDEX_KEY, index);
        fragment.setArguments(args);

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

        index = getArguments().getInt(INDEX_KEY);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(clothesInteractor, index)).get(ItemInfoViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.getItemLiveData()
                .observe(this, this::onItem);
    }

    private void onItem(ItemInfoState itemInfoState) {
        state = itemInfoState.getState();

        switch (state) {
            case ERROR:
                binding.tvIndex.setText("error");
                break;
            case LOADING:
                binding.tvIndex.setText("loading");
                break;
            case OK:
                binding.tvIndex.setText(null);
                String url = itemInfoState.getClothesItem().getImageUrl();
                String description = itemInfoState.getClothesItem().getDescription();
                List<String> contentList = itemInfoState.getClothesItem().getMaterial();
                String content = contentList.toString();
                String brandName = itemInfoState.getClothesItem().getBrandName();
                String name = itemInfoState.getClothesItem().getName();
                binding.itemInfoBrandNameTv.setText(brandName);
                binding.itemInfoItemNameTv.setText(name);
                binding.itemInfoItemDescriptionTv.setText(description);
                binding.itemInfoItemContentTv.setText(content);
                Glide.with(binding.ivPhoto)
                        .asBitmap()
                        .load(url)
                        .into(binding.ivPhoto);
                break;
        }
    }

    public boolean isActive() {
        return state == ItemInfoState.State.OK;
    }

    @Override
    public void onClickBrandName() {
        if(binding.itemInfoBrandFieldDownArrow.getVisibility() == View.VISIBLE){
            binding.itemInfoBrandFieldDownArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.VISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.VISIBLE);
        } else {
            binding.itemInfoBrandFieldDownArrow.setVisibility(View.VISIBLE);
            binding.itemInfoBrandFieldUpArrow.setVisibility(View.INVISIBLE);
            binding.itemInfoItemDescriptionLayout.setVisibility(View.GONE);
        }
    }

    public void showYes(boolean flag){
        if (flag){
            binding.rateItemsYes.setVisibility(View.VISIBLE);
        } else {
            binding.rateItemsYes.setVisibility(View.INVISIBLE);
        }
    }

    public void showNo(boolean flag){
        if (flag){
            binding.rateItemsNo.setVisibility(View.VISIBLE);
        } else {
            binding.rateItemsNo.setVisibility(View.INVISIBLE);
        }
    }
}
