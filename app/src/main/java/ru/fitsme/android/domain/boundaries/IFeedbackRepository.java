package ru.fitsme.android.domain.boundaries;

import io.reactivex.Single;

public interface IFeedbackRepository {
    Single<Boolean> sendFeedback(String name, String email, String message);
}
