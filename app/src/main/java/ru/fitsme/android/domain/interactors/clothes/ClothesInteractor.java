package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.boundaries.clothes.IClothesIndexRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesLikeRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ClothesInteractor implements IClothesInteractor {

    private IClothesIndexRepository clothesIndexRepository;
    private IClothesRepository clothesRepository;
    private IClothesLikeRepository clothesLikeRepository;

    @Inject
    public ClothesInteractor(@NonNull IClothesIndexRepository clothesIndexRepository,
                             @NonNull IClothesRepository clothesRepository,
                             @NonNull IClothesLikeRepository clothesLikeRepository) {
        this.clothesIndexRepository = clothesIndexRepository;
        this.clothesRepository = clothesRepository;
        this.clothesLikeRepository = clothesLikeRepository;
    }

    @NonNull
    @Override
    public Single<Integer> getLastIndexSingle() {
        return Single.create(emitter ->
                emitter.onSuccess(clothesIndexRepository.getLastClothesItemIndex()));
    }

    @NonNull
    @Override
    public Single<ClothesItem> getSingleClothesItem(int index) {
        return Single.create(emitter ->
                emitter.onSuccess(clothesRepository.getClothesItem(index)));
    }

    @NonNull
    @Override
    public Completable setLikeToClothesItem(int index, boolean liked) {
        return Completable.create(emitter -> {
                    ClothesItem clothesItem = clothesRepository.getClothesItem(index);
                    clothesLikeRepository.likeItem(clothesItem.getId(), liked);
                    clothesIndexRepository.setLastClothesItemIndex(index + 1);
                    emitter.onComplete();
                }
        );
    }
}
