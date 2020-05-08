package ru.fitsme.android.presentation.fragments.rateItemsdetail;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentRateItemDetailBinding;
import ru.fitsme.android.domain.entities.clothes.ClotheType;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class RateItemsDetailFragment extends BaseFragment<RateItemsDetailViewModel>
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

    @Override
    protected int getLayout() {
        return R.layout.fragment_rate_item_detail;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentRateItemDetailBinding.bind(view);
        binding.setBindingEvents(this);
        setUp();
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
        if (clothesItem.getClotheType().getType() == ClotheType.Type.TOP){
            viewModel.getCurrentTopSize().observe(getViewLifecycleOwner(), size -> {
                binding.fragmentRateItemDetailItemSize.setText(size);
            });
        } else {
            viewModel.getCurrentBottomSize().observe(getViewLifecycleOwner(), size -> {
                binding.fragmentRateItemDetailItemSize.setText(size);
            });
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
