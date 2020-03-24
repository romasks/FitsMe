package ru.fitsme.android.data.repositories.feedback;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.boundaries.IFeedbackRepository;
import ru.fitsme.android.domain.entities.feedback.FeedbackResponse;

@Singleton
public class FeedbackRepository implements IFeedbackRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    public FeedbackRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public Single<FeedbackResponse> sendFeedback(FeedbackRequest request) {
        return Single.create(emitter ->
                webLoader.sendFeedback(request)
                        .subscribe(orderOkResponse -> emitter.onSuccess(orderOkResponse.getResponse()),
                                emitter::onError));
    }
}
