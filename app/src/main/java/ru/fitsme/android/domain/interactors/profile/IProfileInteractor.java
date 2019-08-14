package ru.fitsme.android.domain.interactors.profile;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.SparseArray;

import java.util.List;

import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IProfileInteractor  extends BaseInteractor {
    ReplaySubject<SparseArray<ClotheSize>> getSizes();

    void setTopClothesSizeType(ClotheSizeType clothesSizeType);

    void setBottomClotheSizeType(ClotheSizeType clothesSizeType);

    ObservableInt getCurrentTopSizeTypeValue();

    ObservableInt getCurrentBottomSizeTypeValue();

    LiveData<List<String>> getCurrentTopSizeArray();

    LiveData<List<String>> getCurrentBottomSizeArray();

    ObservableField<String> getCurrentChestSize();

    ObservableField<String> getCurrentTopWaistSize();

    ObservableField<String> getCurrentTopHipsSize();

    ObservableField<String> getCurrentSleeveSize();

    ObservableField<String> getCurrentBottomWaistSize();

    ObservableField<String> getCurrentBottomHipsSize();

    ObservableInt getCurrentTopSizeIndex();

    ObservableInt getCurrentBottomSizeIndex();

    void setCurrentTopSizeIndex(int position);

    void setCurrentBottomSizeIndex(int position);
}
