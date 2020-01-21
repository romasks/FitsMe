package ru.fitsme.android.data.repositories.feedback;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.boundaries.IFeedbackRepository;

@Singleton
public class FeedbackRepository implements IFeedbackRepository {

    private final WebLoaderNetworkChecker webLoader;

    @Inject
    public FeedbackRepository(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public Single<Boolean> sendFeedback(String name, String email, String message) {
        return Single.create(emitter ->
                webLoader.sendFeedback(new FeedbackRequest(message))
                        .subscribe(orderOkResponse -> emitter.onSuccess(orderOkResponse.getResponse()),
                                emitter::onError));
    }
}
