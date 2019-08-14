package ru.fitsme.android.domain.interactors.profile;

import android.databinding.ObservableInt;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.profile.Profile;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IProfileInteractor  extends BaseInteractor {
    ReplaySubject<List<ClotheSize>> getSizes();

    Single<Profile> getProfile();

    void setTopClothesSizeType(ClotheSizeType clothesSizeType);

    void setBottomClotheSizeType(ClotheSizeType clothesSizeType);

    ObservableInt getCurrentTopSizeType();

    ObservableInt getCurrentBottomSizeType();
}
