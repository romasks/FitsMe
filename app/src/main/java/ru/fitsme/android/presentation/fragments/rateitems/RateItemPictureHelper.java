package ru.fitsme.android.presentation.fragments.rateitems;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.ResourceManager;
import ru.fitsme.android.databinding.FragmentRateItemsBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.Picture;

class RateItemPictureHelper {

    private RateItemsFragment fragment;
    private final FragmentRateItemsBinding binding;
    private final ArrayList<PictureItem> pictureItemList = new ArrayList<>();

    private int currentPictureIndex;

    RateItemPictureHelper(RateItemsFragment fragment,
                          FragmentRateItemsBinding binding,
                          ClothesItem clothesItem) {
        this.fragment = fragment;
        this.binding = binding;

        String brandName = clothesItem.getBrand();
        String name = clothesItem.getName();

        binding.fragmentRateItemsBrandNameTv.setText(brandName);
        binding.fragmentRateItemsItemNameTv.setText(name);

        createPictureItemList(clothesItem);
        createUpperPictureCountIndicator(pictureItemList.size());

        currentPictureIndex = 0;
        setPicture(0);
        downloadNextPicture(currentPictureIndex);
    }

    private void setPicture(int i) {
        setLoadingInProgress();
        pictureItemList.get(i).subscribe(this);
        setActiveCountIndicatorItem(i);
    }

    private void setLoadingInProgress() {
        binding.fragmentRateItemsInfoCard.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsBrandNameCard.setVisibility(View.INVISIBLE);
        binding.fragmentRateItemsMessage.setText(App.getInstance().getString(R.string.loading));
        binding.fragmentRateItemsIvPhoto.setImageBitmap(null);
    }

    void setNextPicture() {
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

    void setPreviousPicture() {
        if (currentPictureIndex > 0) {
            pictureItemList.get(currentPictureIndex).unsubscribe();
            currentPictureIndex--;
            setPicture(currentPictureIndex);
        }
    }

    private void createPictureItemList(ClothesItem clothesItem) {
        for (Picture picture : clothesItem.getPics()) {
            pictureItemList.add(new PictureItem(picture));
        }
    }

    private void createUpperPictureCountIndicator(int size) {
        for (int i = 0; i < size; i++) {
            View view = new View(fragment.getContext());
            view.setBackgroundColor(ResourceManager.getColor(R.color.lightGrey));
            int endMerge = 4;
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    endMerge,
                    ResourceManager.getDisplayMetrics()
            );
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            );
            if (i != size - 1) {
                params.setMarginEnd(px);
            }
            view.setLayoutParams(params);
            view.setId(i);
            binding.fragmentRateItemsUpperPicCountIndicatorLl.addView(view);
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
        binding.fragmentRateItemsUpperPicCountIndicatorLl
                .findViewById(i)
                .setBackgroundColor(ResourceManager.getColor(R.color.colorPrimaryDark));
    }

    private void resetIndicator(int i) {
        if (binding.fragmentRateItemsUpperPicCountIndicatorLl.findViewById(i) != null) {
            binding.fragmentRateItemsUpperPicCountIndicatorLl
                    .findViewById(i)
                    .setBackgroundColor(ResourceManager.getColor(R.color.lightGrey));
        }
    }

    private void onPictureReady(Bitmap bitmap) {
        binding.fragmentRateItemsMessage.setText("");
        binding.fragmentRateItemsInfoCard.setVisibility(View.VISIBLE);
        binding.fragmentRateItemsBrandNameCard.setVisibility(View.VISIBLE);
        binding.fragmentRateItemsIvPhoto.setImageBitmap(bitmap);

        fragment.enableLikeButtons();
    }

    private void onPictureFailed() {
        binding.fragmentRateItemsMessage.setText(App.getInstance().getString(R.string.image_loading_error));
    }

    private class PictureItem {
        Picture picture;
        Bitmap bitmap;
        RateItemPictureHelper observer;

        PictureItem(Picture picture) {
            this.picture = picture;
        }

        void subscribe(RateItemPictureHelper observer) {
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
            if (picture.getBitmap() != null) {
                bitmap = picture.getBitmap();
                if (observer != null) {
                    observer.onPictureReady(bitmap);
                }
            } else {
                Glide.with(binding.fragmentRateItemsIvPhoto.getContext())
                        .asBitmap()
                        .load(picture.getUrl())
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                if (observer != null) {
                                    observer.onPictureFailed();
                                }
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
}
