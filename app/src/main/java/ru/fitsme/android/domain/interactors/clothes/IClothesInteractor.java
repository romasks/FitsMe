package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.subjects.Subject;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesInteractor extends BaseInteractor {

    @NonNull
    Subject<ClotheInfo> getItemInfoState();

    @NonNull
    Single<ClotheInfo> setLikeToClothesItem(ClothesItem clothesItem, boolean liked);

    void getNext();

    void updateList();
}
