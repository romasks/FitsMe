package ru.fitsme.android.domain.interactors.feedback;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.domain.boundaries.IFeedbackRepository;
import ru.fitsme.android.domain.entities.feedback.FeedbackResponse;

@Singleton
public class FeedbackInteractor implements IFeedbackInteractor {

    private final IFeedbackRepository feedbackRepository;
    private final Scheduler mainThread;

    @Inject
    public FeedbackInteractor(IFeedbackRepository feedbackRepository,
                              @Named("main") Scheduler mainThread) {
        this.feedbackRepository = feedbackRepository;
        this.mainThread = mainThread;
    }

    @Override
    public Single<FeedbackResponse> sendFeedback(FeedbackRequest request) {
        return Single.create(emitter ->
                feedbackRepository.sendFeedback(request)
                        .observeOn(mainThread)
                        .subscribe(isSuccess -> emitter.onSuccess(isSuccess),
                                emitter::onError));
    }
}
