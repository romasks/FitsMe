package ru.fitsme.android.domain.boundaries.profile;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.profile.Profile;

public interface IProfileRepository {
    Single<Profile> getProfile();

    Single<Profile> setProfile(Profile profile);
}
