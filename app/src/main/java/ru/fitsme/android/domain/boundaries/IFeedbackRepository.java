package ru.fitsme.android.domain.boundaries;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.entities.feedback.FeedbackResponse;

public interface IFeedbackRepository {
    Single<FeedbackResponse> sendFeedback(FeedbackRequest reques);
}
