package ru.fitsme.android.presentation.fragments.iteminfo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.Picture;

class ItemInfoPictureHelper {

    private ItemInfoFragment fragment;
    private final FragmentItemInfoBinding binding;
    private final ArrayList<PictureItem> pictureItemList = new ArrayList<>();
    private int availableWidth;
    private int availableHeight;

    private int currentPictureIndex;

    ItemInfoPictureHelper(ItemInfoFragment fragment,
                          FragmentItemInfoBinding binding,
                          ClothesItem clothesItem,
                          int containerWidth, int containerHeight) {
        this.fragment = fragment;
        this.binding = binding;

        createPictureItemList(clothesItem);
        createUpperPictureCountIndicator(pictureItemList.size());

        Resources r = App.getInstance().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                App.getInstance().getResources().getDimensionPixelSize(R.dimen.item_info_card_padding),
                r.getDisplayMetrics()
        );

        availableWidth = containerWidth - px;
        availableHeight = containerHeight - px;

        currentPictureIndex = 0;
        setPicture(0);
        downloadNextPicture(currentPictureIndex);
    }

    private void setPicture(int i) {
        pictureItemList.get(i).subscribe(this);
        setActiveCountIndicatorItem(i);
    }

    public void setNextPicture() {
        if (currentPictureIndex < pictureItemList.size() - 1) {
            pictureItemList.get(currentPictureIndex).unsubscribe();
            currentPictureIndex++;
            setPicture(currentPictureIndex);
            downloadNextPicture(currentPictureIndex);
        }
    }

    private void downloadNextPicture(int currentPictureIndex) {
        if (currentPictureIndex + 1 < pictureItemList.size() - 1) {
            pictureItemList.get(currentPictureIndex + 1).preparePicture();
        }
    }

    public void setPreviousPicture() {
        if (currentPictureIndex > 0) {
            pictureItemList.get(currentPictureIndex).unsubscribe();
            currentPictureIndex--;
            setPicture(currentPictureIndex);
        }
    }

    private void createPictureItemList(ClothesItem clothesItem) {
        List<Picture> pictures = clothesItem.getPics();
        for (int i = 0; i < pictures.size(); i++) {
            pictureItemList.add(new PictureItem(pictures.get(i)));
        }
    }

    private void createUpperPictureCountIndicator(int size) {
        for (int i = 0; i < size; i++) {
            View view = new View(fragment.getContext());
            Resources r = App.getInstance().getResources();
            view.setBackgroundColor(r.getColor(R.color.lightGrey));
            int endMerge = 4;
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    endMerge,
                    r.getDisplayMetrics()
            );
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            if (i != size - 1) {
                params.setMarginEnd(px);
            }
            view.setLayoutParams(params);
            view.setId(i);
            binding.itemInfoUpperPicCountIndicatorLl.addView(view);
            setActiveCountIndicatorItem(currentPictureIndex);
        }
    }

    private void setActiveCountIndicatorItem(int i) {
        if (i - 1 >= 0) {
            resetIndicator(i - 1);
        }
        if (i + 1 < pictureItemList.size()) {
            resetIndicator(i + 1);
        }
        binding.itemInfoUpperPicCountIndicatorLl
                .findViewById(i)
                .setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorPrimaryDark));
    }

    private void resetIndicator(int i) {
        if (binding.itemInfoUpperPicCountIndicatorLl.findViewById(i) != null) {
            binding.itemInfoUpperPicCountIndicatorLl
                    .findViewById(i)
                    .setBackgroundColor(App.getInstance().getResources().getColor(R.color.lightGrey));
        }
    }


    private void onPictureReady(Bitmap bitmap) {
        binding.itemInfoMessage.setText("");

        binding.itemInfoItemInfoCard.setVisibility(View.VISIBLE);
        binding.itemInfoBrandNameCard.setVisibility(View.VISIBLE);

        binding.ivPhoto.setImageBitmap(bitmap);
    }

    private void onPictureFailed() {
        binding.itemInfoMessage.setText(App.getInstance().getString(R.string.image_loading_error));
    }


    private class PictureItem {
        Picture picture;
        Bitmap bitmap;
        ItemInfoPictureHelper observer;

        PictureItem(Picture picture) {
            this.picture = picture;
        }

        void subscribe(ItemInfoPictureHelper observer) {
            this.observer = observer;
            if (bitmap != null) {
                observer.onPictureReady(bitmap);
            } else {
                loadPicture();
            }
        }

        void unsubscribe() {
            observer = null;
        }

        void preparePicture() {
            loadPicture();
        }

        private void loadPicture() {
            Glide.with(binding.ivPhoto.getContext())
                    .asBitmap()
                    .load(picture.getUrl())
                    .override(availableWidth, availableHeight)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            observer.onPictureFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bitmap = resource;
                            if (observer != null) {
                                observer.onPictureReady(bitmap);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }
}
