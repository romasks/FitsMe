package ru.fitsme.android.domain.interactors.returns.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableField;

import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IReturnsInteractor extends BaseInteractor {

    LiveData<PagedList<ReturnsItem>> getPagedListLiveData();

    LiveData<Boolean> getReturnsIsEmpty();

    ObservableField<String> getShowMessage();

    boolean itemIsInCart(int position);
}
