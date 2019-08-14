package ru.fitsme.android.domain.interactors.profile;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.boundaries.profile.IProfileRepository;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.profile.Profile;
import timber.log.Timber;

@Singleton
public class ProfileInteractor implements IProfileInteractor {

    private IClothesRepository clothesRepository;
    private IProfileRepository profileRepository;
    private Scheduler workThread;
    private Scheduler mainThread;

    private ReplaySubject<SparseArray<ClotheSize>> listReplaySubject = ReplaySubject.createWithSize(1);
    private ReplaySubject<Profile> profileReplaySubject = ReplaySubject.createWithSize(1);

    private ObservableInt currentTopSizeTypeValue = new ObservableInt();
    private ObservableInt currentBottomSizeTypeValue = new ObservableInt();
    private ObservableInt currentTopSizeIndex = new ObservableInt();
    private ObservableInt currentBottomSizeIndex = new ObservableInt();
    private MutableLiveData<List<String>> currentTopSizeArray = new MutableLiveData<>();
    private MutableLiveData<List<String>> currentBottomSizeArray  = new MutableLiveData<>();
    private ObservableField<String> currentChestSize = new ObservableField<>();
    private ObservableField<String> currentTopWaistSize = new ObservableField<>();
    private ObservableField<String> currentTopHipsSize = new ObservableField<>();
    private ObservableField<String> currentSleeveSize = new ObservableField<>();
    private ObservableField<String> currentBottomWeistSize = new ObservableField<>();
    private ObservableField<String> currentBottomHipsSize = new ObservableField<>();

    private static final String MEASURE_UNIT = "см";

    @Inject
    ProfileInteractor(IClothesRepository clothesRepository,
                      IProfileRepository profileRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread){
        this.clothesRepository = clothesRepository;
        this.profileRepository = profileRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;

        currentTopSizeTypeValue.set(getSettingTopClothesSize().getValue());
        currentBottomSizeTypeValue.set(getSettingBottomClothesSize().getValue());
        getProfileFromRepo();
        getSizesFromRepo();
        setTopClothesSize();
        setBottomClothesSize();
        setSizeFields();
    }

    @Override
    public ReplaySubject<SparseArray<ClotheSize>> getSizes(){
        return listReplaySubject;
    }

    @SuppressLint("CheckResult")
    private void getSizesFromRepo(){
        clothesRepository.getSizes()
                .observeOn(mainThread)
                .subscribe(clotheSizes ->
                        listReplaySubject.onNext(clotheSizes), Timber::e);
    }

    @SuppressLint("CheckResult")
    private void getProfileFromRepo(){
        profileRepository.getProfile()
                .observeOn(mainThread)
                .subscribe(profile -> {
                    profileReplaySubject.onNext(profile);
                }, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void setSizeFields(){
        profileReplaySubject.subscribe(profile -> {
            listReplaySubject.subscribe(clotheSizes -> {
                int topSizeId = profile.getTopSize();
                ClotheSize topClotheSize = clotheSizes.get(topSizeId);
                currentChestSize.set(topClotheSize.getChestLow() +
                        "-" + topClotheSize.getChestHigh() + " " + MEASURE_UNIT);
                currentTopWaistSize.set(topClotheSize.getWaistLow() +
                        "-" + topClotheSize.getWaistHigh() + " " + MEASURE_UNIT);
                currentTopHipsSize.set(topClotheSize.getHipsLow() +
                        "-" + topClotheSize.getHipsHigh() + " " + MEASURE_UNIT);
                currentSleeveSize.set(topClotheSize.getSleeveLow() +
                        "-" + topClotheSize.getSleeveHigh() + " " + MEASURE_UNIT);

                int bottomSizeId = profile.getBottomSize();
                ClotheSize bottomClotheSize = clotheSizes.get(bottomSizeId);
                int bottomSizeIndex = clotheSizes.indexOfKey(bottomSizeId);
                currentBottomSizeIndex.set(bottomSizeIndex);
                currentBottomWeistSize.set(bottomClotheSize.getWaistLow() +
                        "-" + bottomClotheSize.getWaistHigh() + " " + MEASURE_UNIT);
                currentBottomHipsSize.set(bottomClotheSize.getHipsLow() +
                        "-" + bottomClotheSize.getHipsHigh() + " " + MEASURE_UNIT);
            }, Timber::e);
        }, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void setTopClothesSize() {
        profileReplaySubject.subscribe(profile -> {
            listReplaySubject.subscribe(clotheSizes -> {
                int topSizeId = profile.getTopSize();
                int topSizeIndex = clotheSizes.indexOfKey(topSizeId);
                currentTopSizeIndex.set(topSizeIndex);
                List<String> topSizeArray = makeTopSizeArray(clotheSizes);
                currentTopSizeArray.setValue(topSizeArray);
            }, Timber::e);
        }, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void setBottomClothesSize() {
        profileReplaySubject.subscribe(profile -> {
            listReplaySubject.subscribe(clotheSizes -> {
                int bottomSizeId = profile.getBottomSize();
                int bottomSizeIndex = clotheSizes.indexOfKey(bottomSizeId);
                currentBottomSizeIndex.set(bottomSizeIndex);
                List<String> bottomSizeArray = makeBottomSizeArray(clotheSizes);
                currentBottomSizeArray.setValue(bottomSizeArray);
            }, Timber::e);
        }, Timber::e);
    }

    private ClotheSizeType getSettingTopClothesSize(){
        return clothesRepository.getSettingTopClothesSizeType();
    }

    private ClotheSizeType getSettingBottomClothesSize(){
        return clothesRepository.getSettingsBottomClothesSizeType();
    }

    @Override
    public void setTopClothesSizeType(ClotheSizeType clothesSizeType){
        if (currentTopSizeTypeValue.get() != clothesSizeType.getValue()) {
            currentTopSizeTypeValue.set(clothesSizeType.getValue());
            clothesRepository.setSettingsTopClothesSizeType(clothesSizeType);
            setTopClothesSize();
        }
    }

    @Override
    public void setBottomClotheSizeType(ClotheSizeType clothesSizeType){
        if (currentBottomSizeTypeValue.get() != clothesSizeType.getValue()) {
            currentBottomSizeTypeValue.set(clothesSizeType.getValue());
            clothesRepository.setSettingsBottomClothesSizeType(clothesSizeType);
            setBottomClothesSize();
        }
    }

    @Override
    public ObservableInt getCurrentTopSizeTypeValue(){
        return currentTopSizeTypeValue;
    }

    @Override
    public ObservableInt getCurrentBottomSizeTypeValue(){
        return currentBottomSizeTypeValue;
    }

    private List<String> makeTopSizeArray(SparseArray<ClotheSize> clotheSizeArray){
        List<String> topSizeArray = new ArrayList<String>();
        for (int i = 0; i < clotheSizeArray.size(); i++) {
            ClotheSize clotheSize = clotheSizeArray.valueAt(i);
            String size = getNationalSize(clotheSize, currentTopSizeTypeValue.get());
            topSizeArray.add(size);
        }
        return topSizeArray;
    }

    private List<String> makeBottomSizeArray(SparseArray<ClotheSize> clotheSizeArray){
        List<String> bottomSizeArray = new ArrayList<String>();
        for (int i = 0; i < clotheSizeArray.size(); i++) {
            ClotheSize clotheSize = clotheSizeArray.valueAt(i);
            String size = getNationalSize(clotheSize, currentBottomSizeTypeValue.get());
            bottomSizeArray.add(size);
        }
        return bottomSizeArray;
    }

    private String getNationalSize(ClotheSize clotheSize, int clotheTypeValue) {
        switch (getClotheSizeType(clotheTypeValue)){
            case Undefined:
                return "";
            case International:
                return clotheSize.getInternational();
            case Russia:
                return Integer.toString(clotheSize.getRu());
            case Europe:
                return Integer.toString(clotheSize.getEu());
            case France:
                return Integer.toString(clotheSize.getFr());
            case Italy:
                return Integer.toString(clotheSize.getIt());
            case USA:
                return Integer.toString(clotheSize.getUs());
            case UK:
                return Integer.toString(clotheSize.getUk());
            default:
                throw new NoSuchElementException("Unsupported ClotheSize");
        }
    }

    private ClotheSizeType getClotheSizeType(int value) {
        for (int i = 0; i < ClotheSizeType.values().length; i++) {
            if (ClotheSizeType.values()[i].getValue() == value) {
                return ClotheSizeType.values()[i];
            }
        }
        throw new IndexOutOfBoundsException("Value out of ClotheSizeType bounds");
    }

    @Override
    public LiveData<List<String>> getCurrentTopSizeArray() {
        return currentTopSizeArray;
    }

    @Override
    public LiveData<List<String>> getCurrentBottomSizeArray() {
        return currentBottomSizeArray;
    }

    @Override
    public ObservableField<String> getCurrentChestSize() {
        return currentChestSize;
    }

    @Override
    public ObservableField<String> getCurrentTopWaistSize() {
        return currentTopWaistSize;
    }

    @Override
    public ObservableField<String> getCurrentTopHipsSize() {
        return currentTopHipsSize;
    }

    @Override
    public ObservableField<String> getCurrentSleeveSize() {
        return currentSleeveSize;
    }

    @Override
    public ObservableField<String> getCurrentBottomWaistSize() {
        return currentBottomWeistSize;
    }

    @Override
    public ObservableField<String> getCurrentBottomHipsSize() {
        return currentBottomHipsSize;
    }

    @Override
    public ObservableInt getCurrentTopSizeIndex() {
        return currentTopSizeIndex;
    }

    @Override
    public ObservableInt getCurrentBottomSizeIndex() {
        return currentBottomSizeIndex;
    }

    @SuppressLint("CheckResult")
    @Override
    public void setCurrentTopSizeIndex(int position) {
        Profile oldProfile = profileReplaySubject.getValue();
        SparseArray<ClotheSize> array = listReplaySubject.getValue();
        profileRepository.setProfile(
                new Profile(
                        oldProfile.getTel(),
                        oldProfile.getStreet(),
                        oldProfile.getHouseNumber(),
                        oldProfile.getApartment(),
                        array.keyAt(position),
                        oldProfile.getBottomSize()
                        ))
                .observeOn(mainThread)
                .subscribe(
                        newProfile -> profileReplaySubject.onNext(newProfile), Timber::e);
    }

    @SuppressLint("CheckResult")
    @Override
    public void setCurrentBottomSizeIndex(int position) {
        Profile oldProfile = profileReplaySubject.getValue();
        SparseArray<ClotheSize> array = listReplaySubject.getValue();
        profileRepository.setProfile(
                new Profile(
                        oldProfile.getTel(),
                        oldProfile.getStreet(),
                        oldProfile.getHouseNumber(),
                        oldProfile.getApartment(),
                        oldProfile.getTopSize(),
                        array.keyAt(position)
                        ))
                .observeOn(mainThread)
                .subscribe(
                        newProfile -> profileReplaySubject.onNext(newProfile), Timber::e);

    }
}
