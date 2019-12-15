package ru.fitsme.android.presentation.fragments.returns.details;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class ReturnDetailsViewModel extends BaseViewModel {

    private IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<ReturnsOrder> returnsOrderLiveData = new MutableLiveData<>();

    public ReturnDetailsViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    public MutableLiveData<ReturnsOrder> getReturnsOrderLiveData() {
        return returnsOrderLiveData;
    }

    public void init(int returnId) {
        isLoading.set(true);
        addDisposable(returnsInteractor.getReturnById(returnId)
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(ReturnsOrder returnsOrder) {
        isLoading.set(false);
        returnsOrderLiveData.setValue(returnsOrder);
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        returnsOrderLiveData.setValue(null);
        Timber.d(throwable);
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }
}
