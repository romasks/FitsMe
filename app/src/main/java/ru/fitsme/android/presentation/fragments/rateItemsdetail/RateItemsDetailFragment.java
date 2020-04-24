package ru.fitsme.android.presentation.fragments.rateItemsdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentRateItemDetailBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class RateItemsDetailFragment extends Fragment
        implements BindingEventsClickListener, RateItemsDetailTouchListener.Callback {

    private static ClothesItem clotheInfo;
    private static Callback callback;
    private FragmentRateItemDetailBinding binding;
    private RateItemsDetailPictureHelper pictureHelper;

    public static RateItemsDetailFragment newInstance(ClothesItem clothesItem, Callback callback) {
        RateItemsDetailFragment.callback = callback;
        RateItemsDetailFragment fragment = new RateItemsDetailFragment();
        RateItemsDetailFragment.clotheInfo = clothesItem;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_item_detail, container, false);
        binding = FragmentRateItemDetailBinding.bind(view);
        binding.setBindingEvents(this);
        setUp();
        return view;
    }

    private void setUp() {
        onClothesItem(clotheInfo);
        setListeners();
    }

    private void setListeners() {
        binding.fragmentRateItemDetailImageContainer.setOnTouchListener(new RateItemsDetailTouchListener(this));
    }

    private void onClothesItem(ClothesItem clothesItem) {
        binding.fragmentRateItemDetailMessage.setText(getString(R.string.loading));
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

        binding.fragmentRateItemDetailBrandNameTv.setText(brandName);
        binding.fragmentRateItemDetailItemNameTv.setText(name);
        binding.fragmentRateItemDetailItemDescriptionTv.setText(description);
        binding.fragmentRateItemDetailItemContentTv.setText(clotheContentStr.toString());

        binding.fragmentRateItemDetailContainer.setVisibility(View.VISIBLE);
        binding.fragmentRateItemDetailContainer.setVisibility(View.VISIBLE);
        binding.fragmentRateItemDetailItemDescriptionLayout.setVisibility(View.VISIBLE);

        pictureHelper =
                new RateItemsDetailPictureHelper(this, binding, clothesItem);
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
    public void onClickBrandName() {
        setFullState();
    }

    private void setFullState() {
        callback.closeRateItemsDetailFragment();
    }


    public interface Callback {
        void closeRateItemsDetailFragment();
    }
}
