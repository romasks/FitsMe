package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IClothesInteractor extends BaseInteractor {

    @NonNull
    Single<Integer> getLastIndexSingle();

    @NonNull
    Single<ClothesItem> getSingleClothesItem(int index);

    @NonNull
    Single<List<ClothesItem>> getSingleClothesItems(int firstIndex, int count);

    @NonNull
    Completable setLikeToClothesItem(int index, boolean liked);
}
