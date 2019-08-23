package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;

public class SizeProfileViewModel extends BaseViewModel {

    public ObservableInt selectedTopType;
    public ObservableInt selectedBottomType;

    public ObservableInt currentTopSizeIndex;
    public ObservableInt currentBottomSizeIndex;
    private LiveData<List<String>> currentTopSizeArray;
    private LiveData<List<String>> currentBottomSizeArray;
    public ObservableField<String> currentChestSize;
    public ObservableField<String> currentTopWaistSize;
    public ObservableField<String> currentTopHipsSize;
    public ObservableField<String> currentSleeveSize;
    public ObservableField<String> currentBottomWaistSize;
    public ObservableField<String> currentBottomHipsSize;
    public ObservableField<String> message;

    @Inject
    MainNavigation navigation;
    private final IProfileInteractor profileInteractor;

    public SizeProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
        inject(this);
        selectedTopType = profileInteractor.getCurrentTopSizeTypeValue();
        selectedBottomType = profileInteractor.getCurrentBottomSizeTypeValue();
        currentTopSizeIndex = profileInteractor.getCurrentTopSizeIndex();
        currentBottomSizeIndex = profileInteractor.getCurrentBottomSizeIndex();
        currentTopSizeArray = profileInteractor.getCurrentTopSizeArray();
        currentBottomSizeArray = profileInteractor.getCurrentBottomSizeArray();
        currentChestSize = profileInteractor.getCurrentChestSize();
        currentTopWaistSize = profileInteractor.getCurrentTopWaistSize();
        currentTopHipsSize = profileInteractor.getCurrentTopHipsSize();
        currentSleeveSize = profileInteractor.getCurrentSleeveSize();
        currentBottomWaistSize = profileInteractor.getCurrentBottomWaistSize();
        currentBottomHipsSize = profileInteractor.getCurrentBottomHipsSize();
        message = profileInteractor.getMessage();
    }

    public void init() {
    }

    public void goBack() {
        navigation.goToMainProfile();
    }

    public void onTopSizeTypeSpinnerSelected(int position) {
        ClotheSizeType sizeType = getClotheSizeType(position);
        profileInteractor.setTopClothesSizeType(sizeType);
    }


    private ClotheSizeType getClotheSizeType(int value) {
        for (int i = 0; i < ClotheSizeType.values().length; i++) {
            if (ClotheSizeType.values()[i].getValue() == value) {
                return ClotheSizeType.values()[i];
            }
        }
        throw new IndexOutOfBoundsException("Value out of ClotheSizeType bounds");
    }

    public void onBottomSizeTypeSpinnerSelected(int position) {
        ClotheSizeType sizeType = getClotheSizeType(position);
        profileInteractor.setBottomClotheSizeType(sizeType);
    }

    public LiveData<List<String>> getTopSizeArray() {
        return currentTopSizeArray;
    }

    public LiveData<List<String>> getBottomSizeArray() {
        return currentBottomSizeArray;
    }

    public void onTopSizeValueSpinnerSelected(int position) {
        profileInteractor.setCurrentTopSizeIndex(position);
    }

    public void onBottomSizeValueSpinnerSelected(int position) {
        profileInteractor.setCurrentBottomSizeIndex(position);
    }
}
