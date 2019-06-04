package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.clothes.IClothesIndexRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesLikeRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

@Singleton
public class ClothesInteractor implements IClothesInteractor {

    private final IClothesIndexRepository clothesIndexRepository;
    private final IClothesRepository clothesRepository;
    private final IClothesLikeRepository clothesLikeRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    ClothesInteractor(IClothesIndexRepository clothesIndexRepository,
                      IClothesRepository clothesRepository,
                      IClothesLikeRepository clothesLikeRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.clothesIndexRepository = clothesIndexRepository;
        this.clothesRepository = clothesRepository;
        this.clothesLikeRepository = clothesLikeRepository;
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
                emitter.onSuccess(clothesRepository.getClothesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<List<ClothesItem>> getSingleClothesItems(int firstIndex, int count) {
        return Single.create((SingleOnSubscribe<List<ClothesItem>>) emitter ->
                emitter.onSuccess(getClothesItems(firstIndex, count)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private List<ClothesItem> getClothesItems(int firstIndex, int count) throws AppException {
        List<ClothesItem> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(clothesRepository.getClothesItem(firstIndex + i));
        }
        return items;
    }

    @NonNull
    @Override
    public Completable setLikeToClothesItem(int index, boolean liked) {
        return Completable.create(emitter -> {
            ClothesItem clothesItem = clothesRepository.getClothesItem(index);
            clothesLikeRepository.likeItem(clothesItem.getId(), liked);
            clothesIndexRepository.setLastClothesItemIndex(index + 1);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
