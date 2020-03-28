package ru.fitsme.android.domain.interactors.feedback;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.entities.feedback.FeedbackResponse;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IFeedbackInteractor extends BaseInteractor {
    Single<FeedbackResponse> sendFeedback(FeedbackRequest request);
}
