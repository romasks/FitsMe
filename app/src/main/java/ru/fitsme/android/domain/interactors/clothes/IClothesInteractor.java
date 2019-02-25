package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public interface IClothesInteractor {

    @NonNull
    Single<Integer> getLastIndexSingle();

    @NonNull
    Single<ClothesItem> getSingleClothesItem(int index);

    @NonNull
    Completable setLikeToClothesItem(int index, boolean liked);
}
