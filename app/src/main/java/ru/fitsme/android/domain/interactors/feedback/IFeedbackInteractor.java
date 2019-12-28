package ru.fitsme.android.domain.interactors.feedback;

import io.reactivex.Single;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IFeedbackInteractor extends BaseInteractor {
    Single<Boolean> sendFeedback(String name, String email, String message);
}
