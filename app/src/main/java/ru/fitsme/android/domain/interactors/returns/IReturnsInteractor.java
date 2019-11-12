package ru.fitsme.android.domain.interactors.returns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IReturnsInteractor extends BaseInteractor {

    LiveData<PagedList<ReturnsItem>> getPagedListLiveData();

    LiveData<Boolean> getReturnsIsEmpty();

    ObservableField<String> getShowMessage();

    boolean itemIsInCart(int position);

    void sendReturnOrder(ReturnsItem returnsItem);
}
