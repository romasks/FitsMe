package ru.fitsme.android.presentation.fragments.returns;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.favourites.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ReturnsViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;
    private MainFragment mainFragment;

    public ObservableField<String> showMessage;

    public ReturnsViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        showMessage = returnsInteractor.getShowMessage();
    }

    LiveData<PagedList<ReturnsItem>> getPageLiveData() {
        return returnsInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getReturnsIsEmpty() {
        return returnsInteractor.getReturnsIsEmpty();
    }

    public void goToCheckout() {
        mainFragment.goToCheckout();
    }

    public void goToReturnsHowTo() {
        navigation.goToReturnsHowTo();
    }
}
