package ru.fitsme.android.presentation.fragments.feedback;

import javax.inject.Inject;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.fitsme.android.domain.interactors.feedback.IFeedbackInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class FeedbackViewModel extends BaseViewModel {

    @Inject
    IFeedbackInteractor feedbackInteractor;

    private MutableLiveData<Boolean> successSendFeedbackLiveData;

    public ObservableBoolean isLoading;

    public FeedbackViewModel() {
        inject(this);
    }

    LiveData<Boolean> getSuccessSendFeedbackLiveData() {
        return successSendFeedbackLiveData;
    }

    @Override
    protected void init() {
        successSendFeedbackLiveData = new MutableLiveData<>(false);
        isLoading = new ObservableBoolean(false);
    }

    void onClickSendFeedback(String name, String email, String message) {
        isLoading.set(true);
        addDisposable(feedbackInteractor.sendFeedback(name, email, message)
                .subscribe(this::onSendFeedback, this::onError));
    }

    private void onSendFeedback(boolean isSuccess) {
        isLoading.set(false);
        if (isSuccess) {
            Timber.tag(getClass().getName()).d("SUCCESS");
            successSendFeedbackLiveData.setValue(true);
        }
    }

    private void onError(Throwable t) {
        Timber.e(t);
        successSendFeedbackLiveData.setValue(false);
        isLoading.set(false);
    }
}
