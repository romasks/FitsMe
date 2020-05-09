package ru.fitsme.android.presentation.fragments.feedback;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.entities.feedback.FeedbackResponse;
import ru.fitsme.android.domain.entities.feedback.FeedbackStatus;
import ru.fitsme.android.domain.interactors.feedback.IFeedbackInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class FeedbackViewModel extends BaseViewModel {

    @Inject
    IFeedbackInteractor feedbackInteractor;

    private MutableLiveData<FeedbackStatus> feedbackStatusLiveData;

    public ObservableBoolean isLoading;

    public FeedbackViewModel() {
        inject(this);
    }

    LiveData<FeedbackStatus> getFeedbackStatusLiveData() {
        return feedbackStatusLiveData;
    }

    @Override
    protected void init() {
        feedbackStatusLiveData = new MutableLiveData<>(FeedbackStatus.INITIAL);
        isLoading = new ObservableBoolean(false);
    }

    void onClickSendFeedback(String name, String email, String message) {
        feedbackStatusLiveData.setValue(FeedbackStatus.LOADING);
        isLoading.set(true);
        addDisposable(feedbackInteractor.sendFeedback(new FeedbackRequest(name, email, message))
                .subscribe(this::onSendFeedback, this::onError));
    }

    private void onSendFeedback(FeedbackResponse response) {
//        isLoading.set(false);
        Timber.tag(getClass().getName()).d(response.getMessage());
        feedbackStatusLiveData.setValue(FeedbackStatus.SUCCESS);
    }

    private void onError(Throwable t) {
        Timber.e(t);
        feedbackStatusLiveData.setValue(FeedbackStatus.ERROR);
        isLoading.set(false);
    }
}
