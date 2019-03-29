package ru.fitsme.android.presentation.fragments.basket.view;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class BasketViewModel {

    public boolean showEmpty(){
        return true;
    }

    public boolean loading(){
        return true;
    }

    public FavouritesItem getBasketItemAt(int position){
        return new FavouritesItem();
    }

    public String getAgreementText(){
        int color =
        String text = "<font color="
        return
    }
}
