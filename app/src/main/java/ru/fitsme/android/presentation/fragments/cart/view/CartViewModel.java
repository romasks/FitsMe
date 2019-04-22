package ru.fitsme.android.presentation.fragments.cart.view;

import ru.fitsme.android.data.entities.response.favourites.FavouritesItem;

public class CartViewModel {

    public boolean showEmpty(){
        return true;
    }

    public boolean loading(){
        return true;
    }

    public FavouritesItem getCartItemAt(int position){
        return new FavouritesItem();
    }

}
