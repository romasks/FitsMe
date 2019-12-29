package ru.fitsme.android.domain.interactors.feedback;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.domain.boundaries.IFeedbackRepository;

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
    public Single<Boolean> sendFeedback(String name, String email, String message) {
        return Single.create(emitter ->
                feedbackRepository.sendFeedback(name, email, message)
                        .observeOn(mainThread)
                        .subscribe(isSuccess -> emitter.onSuccess(isSuccess),
                                emitter::onError));
    }
}
