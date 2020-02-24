package ru.fitsme.android.domain.interactors.profile;

import android.util.SparseArray;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IProfileInteractor extends BaseInteractor {
    ReplaySubject<SparseArray<ClotheSize>> getSizes();

//    void setTopClothesSizeType(ClotheSizeType clothesSizeType);
//
//    void setBottomClotheSizeType(ClotheSizeType clothesSizeType);

//    ObservableInt getCurrentTopSizeTypeValue();
//
//    ObservableInt getCurrentBottomSizeTypeValue();

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

    ObservableField<String> getMessage();

    void setCurrentTopSizeIndex(int position);

    void setCurrentBottomSizeIndex(int position);

    void updateInfo();
}
