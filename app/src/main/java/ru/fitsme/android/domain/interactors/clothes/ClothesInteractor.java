package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;

import java.util.ListIterator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

@Singleton
public class ClothesInteractor implements IClothesInteractor {

    private final IClothesRepository clothesRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    private ListIterator<ClotheInfo> clothesListIterator;
    private Subject<ClotheInfo> itemInfoStateSubject = PublishSubject.create();

    @Inject
    ClothesInteractor(IClothesRepository clothesRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.clothesRepository = clothesRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Subject<ClotheInfo> getItemInfoState() {
        getClothesList();
        return itemInfoStateSubject;
    }


    private void getClothesList() {
        clothesRepository.getClotheList()
                .observeOn(mainThread)
                .subscribe(clotheInfoList -> {
                    clothesListIterator = clotheInfoList.listIterator();
                    getNext();
                }, Timber::e);
    }

    @NonNull
    @Override
    public Single<ClotheInfo> setLikeToClothesItem(ClothesItem clothesItem, boolean liked) {
        return clothesRepository.likeItem(clothesItem, liked)
                .observeOn(mainThread);
    }

    @Override
    public void getNext() {
        if (clothesListIterator.hasNext()) {
            itemInfoStateSubject.onNext(clothesListIterator.next());
        } else {
            getClothesList();
        }
    }

    @Override
    public void updateList(){
        getClothesList();
    }
}
