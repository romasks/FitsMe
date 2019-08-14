package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import android.databinding.ObservableInt;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;

public class SizeProfileViewModel extends BaseViewModel {

    public ObservableInt selectedTopType;
    public ObservableInt selectedBottomType;

    @Inject
    MainNavigation navigation;
    private final IProfileInteractor profileInteractor;

    public SizeProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
        inject(this);
        selectedTopType = profileInteractor.getCurrentTopSizeType();
        selectedBottomType = profileInteractor.getCurrentBottomSizeType();
    }

    public void init() {
    }

    public void goBack() {
        navigation.goToMainProfile();
    }

    public void onTopSizeItemSelected(int position) {
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

    public void onBottomSizeItemSelected(int position) {
        ClotheSizeType sizeType = getClotheSizeType(position);
        profileInteractor.setBottomClotheSizeType(sizeType);
    }
}
