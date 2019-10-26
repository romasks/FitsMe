package ru.fitsme.android.presentation.fragments.returns.processing.two;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ChooseOrderReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public ChooseOrderReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
    }

    LiveData<PagedList<ReturnsItem>> getPageLiveData() {
        return returnsInteractor.getPagedListLiveData();
    }

    public void goToReturnsChooseItems(int position) {
        navigation.goToReturnsChooseItems(position);
    }

    public void backToReturnsHowTo() {
        navigation.backToReturnsHowTo();
    }
}
