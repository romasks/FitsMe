package ru.fitsme.android.domain.interactors.clothes;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

@Singleton
public class ClothesInteractor implements IClothesInteractor {

    private final IClothesRepository clothesRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    private MutableLiveData<ClotheInfo> clotheInfoMutableLiveData = new MutableLiveData<>();

    private LinkedList<ClotheInfo> clotheInfoList;
    private PreviousClotheInfoList previousItemInfoList = new PreviousClotheInfoList();

    @Inject
    ClothesInteractor(IClothesRepository clothesRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.clothesRepository = clothesRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;

        updateClothesList();
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateClothesList() {
        clothesRepository.getClotheList()
                .observeOn(mainThread)
                .subscribe(clotheInfoList -> {
                    this.clotheInfoList = (LinkedList<ClotheInfo>) clotheInfoList;
                    clotheInfoMutableLiveData.setValue(this.clotheInfoList.pollFirst());
                }, Timber::e);
    }

    public void setNextClotheInfo() {
        ClotheInfo clotheInfo = clotheInfoList.pollFirst();
        if (clotheInfo != null) {
            clotheInfoMutableLiveData.setValue(clotheInfo);
        } else {
            updateClothesList();
        }
    }

    @Override
    public void setPreviousClotheInfo(ClotheInfo current) {
        if (previousItemInfoList.hasPrevious()) {
            clotheInfoList.addFirst(current);
            ClotheInfo clotheInfo = previousItemInfoList.peekLast();
            clotheInfoMutableLiveData.setValue(clotheInfo);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void setLikeToClothesItem(ClotheInfo clotheInfo, boolean liked) {
        clothesRepository.likeItem(clotheInfo, liked)
                .observeOn(mainThread)
                .subscribe(callback -> {
                    setNextClotheInfo();
                    previousItemInfoList.add(callback);
                });
    }

    @Override
    public LiveData<ClotheInfo> getClotheInfoLiveData() {
        return clotheInfoMutableLiveData;
    }
}
