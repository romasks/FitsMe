package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.clothes.IClothesIndexRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesLikeRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class ClothesInteractor implements IClothesInteractor {

    private final IClothesIndexRepository clothesIndexRepository;
    private final IClothesRepository clothesRepository;
    private final IClothesLikeRepository clothesLikeRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    ClothesInteractor(IClothesIndexRepository clothesIndexRepository,
                      IClothesRepository clothesRepository,
                      IClothesLikeRepository clothesLikeRepository,
                      IUserInfoRepository userInfoRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.clothesIndexRepository = clothesIndexRepository;
        this.clothesRepository = clothesRepository;
        this.clothesLikeRepository = clothesLikeRepository;
        this.userInfoRepository = userInfoRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Single<Integer> getLastIndexSingle() {
        return Single.create((SingleOnSubscribe<Integer>) emitter ->
                emitter.onSuccess(clothesIndexRepository.getLastClothesItemIndex()))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<ClothesItem> getSingleClothesItem(int index) {
        return Single.create((SingleOnSubscribe<ClothesItem>) emitter ->
                emitter.onSuccess(getClothesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    private ClothesItem getClothesItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return getClothesItem(token, index);
    }

    private ClothesItem getClothesItem(String token, int index) throws AppException {
        return clothesRepository.getClothesItem(token, index);
    }

    @NonNull
    @Override
    public Completable setLikeToClothesItem(int index, boolean liked) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            ClothesItem clothesItem = getClothesItem(token, index);
            clothesLikeRepository.likeItem(token, clothesItem.getId(), liked);
            clothesIndexRepository.setLastClothesItemIndex(index + 1);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
