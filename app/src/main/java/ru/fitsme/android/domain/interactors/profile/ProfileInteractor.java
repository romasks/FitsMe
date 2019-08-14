package ru.fitsme.android.domain.interactors.profile;

import android.databinding.ObservableInt;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.boundaries.profile.IProfileRepository;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.profile.Profile;

@Singleton
public class ProfileInteractor implements IProfileInteractor {

    private IClothesRepository clothesRepository;
    private IProfileRepository profileRepository;
    private Scheduler workThread;
    private Scheduler mainThread;

    private ReplaySubject<List<ClotheSize>> listReplaySubject = ReplaySubject.create();

    private ObservableInt currentTopSizeType = new ObservableInt();
    private ObservableInt currentBottomSizeType = new ObservableInt();

    @Inject
    ProfileInteractor(IClothesRepository clothesRepository,
                      IProfileRepository profileRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread){
        this.clothesRepository = clothesRepository;
        this.profileRepository = profileRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;

        currentTopSizeType.set(getSettingTopClothesSize().getValue());
        currentBottomSizeType.set(getSettingBottomClothesSize().getValue());
        getSizesFromRepo();
    }

    @Override
    public ReplaySubject<List<ClotheSize>> getSizes(){
        return listReplaySubject;
    }

    private void getSizesFromRepo(){
        clothesRepository.getSizes()
                .observeOn(mainThread)
                .subscribe(clotheSizes -> listReplaySubject.onNext(clotheSizes));
    }

    @Override
    public Single<Profile> getProfile(){
        return profileRepository.getProfile();
    }

    public ClotheSizeType getSettingTopClothesSize(){
        return clothesRepository.getSettingTopClothesSizeType();
    }

    public ClotheSizeType getSettingBottomClothesSize(){
        return clothesRepository.getSettingsBottomClothesSizeType();
    }

    @Override
    public void setTopClothesSizeType(ClotheSizeType clothesSizeType){
        if (currentTopSizeType.get() != clothesSizeType.getValue()) {
            currentTopSizeType.set(clothesSizeType.getValue());
            clothesRepository.setSettingsTopClothesSizeType(clothesSizeType);
        }
    }

    @Override
    public void setBottomClotheSizeType(ClotheSizeType clothesSizeType){
        if (currentBottomSizeType.get() != clothesSizeType.getValue()) {
            currentBottomSizeType.set(clothesSizeType.getValue());
            clothesRepository.setSettingsBottomClothesSizeType(clothesSizeType);
        }
    }

    @Override
    public ObservableInt getCurrentTopSizeType(){
        return currentTopSizeType;
    }

    @Override
    public ObservableInt getCurrentBottomSizeType(){
        return currentBottomSizeType;
    }
}
