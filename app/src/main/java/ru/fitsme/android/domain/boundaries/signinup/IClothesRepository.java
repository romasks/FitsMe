package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.domain.entities.clothes.LastItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public interface IClothesRepository {

    //Возвращает номер страницы и индекс в списке предмета, который ещё не оценивали
    LastItem getLastPage();

    @NonNull
    ClothesPage loadNextPage(int page) throws AppException;

    void rateItem(@NonNull ClothesItem clothesItem, boolean like);

    void setLastItem(int page);
}
