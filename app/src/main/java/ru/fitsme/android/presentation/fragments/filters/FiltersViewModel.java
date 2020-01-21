package ru.fitsme.android.presentation.fragments.filters;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FiltersViewModel extends BaseViewModel {

    @Inject
    IClothesInteractor clothesInteractor;

    public FiltersViewModel() {
        inject(this);
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }

    @Override
    protected void init() {

    }

    public LiveData<List<FilterBrand>> getBrands(){
        return clothesInteractor.getBrands();
    }

    public LiveData<List<FilterProductName>> getProductNames(){
        return clothesInteractor.getProductNames();
    }

    public LiveData<List<FilterColor>> getColors(){
        return clothesInteractor.getColors();
    }

    public void setFilterProductName(FilterProductName filterProductName) {
        clothesInteractor.setFilterProductName(filterProductName);
    }

    public void setFilterBrand(FilterBrand filterBrand) {
        clothesInteractor.setFilterBrand(filterBrand);
    }

    public void setFilterColor(FilterColor filterColor) {
        clothesInteractor.setFilterColor(filterColor);
    }

    public void onResetClicked() {
        clothesInteractor.resetCheckedFilters();
    }
}
