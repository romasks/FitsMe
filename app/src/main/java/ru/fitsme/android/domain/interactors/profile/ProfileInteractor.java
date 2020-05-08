package ru.fitsme.android.domain.interactors.profile;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.ReplaySubject;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
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

    private ReplaySubject<SparseArray<ClotheSize>> sizeListReplaySubject = ReplaySubject.createWithSize(1);
    private ReplaySubject<Profile> profileReplaySubject = ReplaySubject.createWithSize(1);

    //Сейчас всегда российские размеры (см. line 59). Оставил если вдруг нужно будет менять.
    private ObservableInt currentTopSizeTypeValue = new ObservableInt();
    private ObservableInt currentBottomSizeTypeValue = new ObservableInt();
    private ObservableInt currentTopSizeIndex = new ObservableInt(-1);
    private MutableLiveData<String> currentTopSize = new MutableLiveData<>("");
    private ObservableInt currentBottomSizeIndex = new ObservableInt(-1);
    private MutableLiveData<String> currentBottomSize = new MutableLiveData<>("");
    private MutableLiveData<List<String>> currentTopSizeArray = new MutableLiveData<>();
    private MutableLiveData<List<String>> currentBottomSizeArray = new MutableLiveData<>();
    private ObservableField<String> currentChestSize = new ObservableField<>();
    private ObservableField<String> currentTopWaistSize = new ObservableField<>();
    private ObservableField<String> currentTopHipsSize = new ObservableField<>();
    private ObservableField<String> currentSleeveSize = new ObservableField<>();
    private ObservableField<String> currentBottomWaistSize = new ObservableField<>();
    private ObservableField<String> currentBottomHipsSize = new ObservableField<>();
    //Сейчас не используется. Оставил на всякий случай
    private ObservableField<String> message = new ObservableField<>();

    private static final String MEASURE_UNIT = "см";
    private ClotheSizeType sizeType = ClotheSizeType.Russia;

    @Inject
    ProfileInteractor(IClothesRepository clothesRepository,
                      IProfileRepository profileRepository,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.clothesRepository = clothesRepository;
        this.profileRepository = profileRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }


    @Override
    public void updateInfo() {
        currentTopSizeTypeValue.set(sizeType.getValue());
        currentBottomSizeTypeValue.set(sizeType.getValue());
        getProfileFromRepo();
        getSizesFromRepo();
        setTopClothesSizeList();
        setBottomClothesSizeList();
        setSizeFields();
    }

    @Override
    public ReplaySubject<SparseArray<ClotheSize>> getSizes() {
        return sizeListReplaySubject;
    }

    @SuppressLint("CheckResult")
    private void getSizesFromRepo() {
        clothesRepository.getSizes()
                .observeOn(mainThread)
                .subscribe(clotheSizes ->
                        sizeListReplaySubject.onNext(clotheSizes), Timber::e);
    }

    @SuppressLint("CheckResult")
    private void getProfileFromRepo() {
        profileRepository.getProfile()
                .observeOn(mainThread)
                .subscribe(profile -> profileReplaySubject.onNext(profile), Timber::e);
    }

    @SuppressLint("CheckResult")
    private void setSizeFields() {
        profileReplaySubject.subscribe(profile -> {
            sizeListReplaySubject.subscribe(clotheSizes -> {
                Integer topSizeId = profile.getTopSize();
                if (topSizeId == null || topSizeId == 0) {
                    currentTopSizeIndex.set(-1);
                    currentTopSize.setValue(App.getInstance().getString(R.string.size_not_set));
                    message.set(App.getInstance().getString(R.string.profile_message_to_user_set_top_size));
                    currentChestSize.set("");
                    currentTopWaistSize.set("");
                    currentTopHipsSize.set("");
                    currentSleeveSize.set("");
                } else {
                    ClotheSize topClotheSize = clotheSizes.get(topSizeId);
                    int topSizeIndex = clotheSizes.indexOfKey(topSizeId);
                    currentTopSizeIndex.set(topSizeIndex);
                    String nationalSize = getNationalSize(topClotheSize, currentTopSizeTypeValue.get());
                    currentTopSize.setValue(nationalSize);
                    currentChestSize.set(topClotheSize.getChestLow() +
                            "-" + topClotheSize.getChestHigh() + " " + MEASURE_UNIT);
                    currentTopWaistSize.set(topClotheSize.getWaistLow() +
                            "-" + topClotheSize.getWaistHigh() + " " + MEASURE_UNIT);
                    currentTopHipsSize.set(topClotheSize.getHipsLow() +
                            "-" + topClotheSize.getHipsHigh() + " " + MEASURE_UNIT);
                    currentSleeveSize.set(topClotheSize.getSleeveLow() +
                            "-" + topClotheSize.getSleeveHigh() + " " + MEASURE_UNIT);
                }
                Integer bottomSizeId = profile.getBottomSize();
                if (bottomSizeId == null || bottomSizeId == 0) {
                    if (topSizeId != null && topSizeId != 0) {
                            message.set(App.getInstance().getString(R.string.profile_message_to_user_set_bottom_size));
                    }
                    currentBottomSizeIndex.set(-1);
                    currentBottomSize.setValue(App.getInstance().getString(R.string.size_not_set));
                    currentBottomWaistSize.set("");
                    currentBottomHipsSize.set("");
                } else {
                    ClotheSize bottomClotheSize = clotheSizes.get(bottomSizeId);
                    int bottomSizeIndex = clotheSizes.indexOfKey(bottomSizeId);
                    currentBottomSizeIndex.set(bottomSizeIndex);
                    String nationalSize = getNationalSize(bottomClotheSize, currentBottomSizeTypeValue.get());
                    currentBottomSize.setValue(nationalSize);
                    currentBottomWaistSize.set(bottomClotheSize.getWaistLow() +
                            "-" + bottomClotheSize.getWaistHigh() + " " + MEASURE_UNIT);
                    currentBottomHipsSize.set(bottomClotheSize.getHipsLow() +
                            "-" + bottomClotheSize.getHipsHigh() + " " + MEASURE_UNIT);
                }
            }, Timber::e);
        }, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void setTopClothesSizeList() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(
                sizeListReplaySubject.subscribe(clotheSizes -> {
                    disposable.add(
                            profileReplaySubject.subscribe(profile -> {
                                Integer topSizeId;
                                if (profile.getTopSize() == null) {
                                    topSizeId = 0;
                                } else {
                                    topSizeId = profile.getTopSize();
                                }
                                int topSizeIndex;
                                if (topSizeId == null || topSizeId == 0) {
                                    topSizeIndex = -1;
                                } else {
                                    topSizeIndex = clotheSizes.indexOfKey(topSizeId);
                                }
                                currentTopSizeIndex.set(topSizeIndex);
                                List<String> topSizeArray = makeTopSizeArray(clotheSizes);
                                currentTopSizeArray.setValue(topSizeArray);
                                disposable.dispose();
                            }, Timber::e)
                    );
                }, Timber::e)
        );
    }

    @SuppressLint("CheckResult")
    private void setBottomClothesSizeList() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(
                sizeListReplaySubject.subscribe(clotheSizes -> {
                    disposable.add(
                            profileReplaySubject.subscribe(profile -> {
                                Integer bottomSizeId;
                                if (profile.getBottomSize() == null) {
                                    bottomSizeId = 0;
                                } else {
                                    bottomSizeId = profile.getBottomSize();
                                }
                                int bottomSizeIndex;
                                if (bottomSizeId == null || bottomSizeId == 0) {
                                    bottomSizeIndex = -1;
                                } else {
                                    bottomSizeIndex = clotheSizes.indexOfKey(bottomSizeId);
                                }
                                currentBottomSizeIndex.set(bottomSizeIndex);
                                List<String> bottomSizeArray = makeBottomSizeArray(clotheSizes);
                                currentBottomSizeArray.setValue(bottomSizeArray);
                                disposable.dispose();
                            }, Timber::e)
                    );
                }, Timber::e)
        );
    }

//    private ClotheSizeType getSettingTopClothesSize() {
//        return clothesRepository.getSettingTopClothesSizeType();
//    }
//
//    private ClotheSizeType getSettingBottomClothesSize() {
//        return clothesRepository.getSettingsBottomClothesSizeType();
//    }

//    @Override
//    public void setTopClothesSizeType(ClotheSizeType clothesSizeType) {
//        if (currentTopSizeTypeValue.get() != clothesSizeType.getValue()) {
//            currentTopSizeTypeValue.set(sizeType.getValue());
//            clothesRepository.setSettingsTopClothesSizeType(clothesSizeType);
//            setTopClothesSizeList();
//        }
//    }
//
//    @Override
//    public void setBottomClotheSizeType(ClotheSizeType clothesSizeType) {
//        if (currentBottomSizeTypeValue.get() != clothesSizeType.getValue()) {
//            currentBottomSizeTypeValue.set(sizeType.getValue());
//            clothesRepository.setSettingsBottomClothesSizeType(clothesSizeType);
//            setBottomClothesSizeList();
//        }
//    }

//    @Override
//    public ObservableInt getCurrentTopSizeTypeValue() {
//        return currentTopSizeTypeValue;
//    }
//
//    @Override
//    public ObservableInt getCurrentBottomSizeTypeValue() {
//        return currentBottomSizeTypeValue;
//    }

    private List<String> makeTopSizeArray(SparseArray<ClotheSize> clotheSizeArray) {
        List<String> topSizeArray = new ArrayList<String>();
        for (int i = 0; i < clotheSizeArray.size(); i++) {
            ClotheSize clotheSize = clotheSizeArray.valueAt(i);
            String size = getNationalSize(clotheSize, currentTopSizeTypeValue.get());
            if (size != null) {
                topSizeArray.add(size);
            }
        }
        return topSizeArray;
    }

    private List<String> makeBottomSizeArray(SparseArray<ClotheSize> clotheSizeArray) {
        List<String> bottomSizeArray = new ArrayList<String>();
        for (int i = 0; i < clotheSizeArray.size(); i++) {
            ClotheSize clotheSize = clotheSizeArray.valueAt(i);
            String size = getNationalSize(clotheSize, currentBottomSizeTypeValue.get());
            if (size != null) {
                bottomSizeArray.add(size);
            }
        }
        return bottomSizeArray;
    }

    private String getNationalSize(ClotheSize clotheSize, int clotheTypeValue) {
        switch (getClotheSizeType(clotheTypeValue)) {
            case Undefined:
                return null;
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
        return currentBottomWaistSize;
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

    @Override
    public ObservableField<String> getMessage() {
        return message;
    }

    @SuppressLint("CheckResult")
    @Override
    public void setCurrentTopSizeIndex(int position) {
        if (currentTopSizeIndex.get() != position) {
            Profile oldProfile = profileReplaySubject.getValue();
            SparseArray<ClotheSize> sizeArray = sizeListReplaySubject.getValue();
            String newTel = oldProfile.getTel();
            String newStreet = oldProfile.getStreet();
            String newHouseNumber = oldProfile.getHouseNumber();
            String newApartment = oldProfile.getApartment();
            Integer newTopSize;
            if (position == -1){
                newTopSize = null;
            } else {
                newTopSize = sizeArray.keyAt(position);
            }
            Integer newBottomSize = oldProfile.getBottomSize();
            Profile newProfile = new Profile(newTel, newStreet, newHouseNumber, newApartment, newTopSize, newBottomSize);
            message.set(App.getInstance().getString(R.string.profile_message_to_user_saving));

            //сначала устанавливаю профиль внутри приложения, потом отправляю на сервер.
            // Если ошибка, то устанавливаю старый профиль. Сделал так, потому что, если быстро поставить
            // верхний и нижний размеры, то новый профиль не успевает прийти с сервера и отправляется второй
            // запрос с пустым первым размером
            profileReplaySubject.onNext(newProfile);
            profileRepository.setProfile(newProfile)
                    .observeOn(mainThread)
                    .subscribe(
                            updatedProfile -> {
                                message.set(App.getInstance().getString(R.string.profile_message_to_user_saving_complete));
                            }, error -> {
                                Timber.e(error);
                                message.set(App.getInstance().getString(R.string.profile_message_to_user_error));
                                profileRepository.setProfile(oldProfile);
                            });
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void setCurrentBottomSizeIndex(int position) {
        if (currentBottomSizeIndex.get() != position) {
            Profile oldProfile = profileReplaySubject.getValue();
            SparseArray<ClotheSize> sizeArray = sizeListReplaySubject.getValue();
            String newTel = oldProfile.getTel();
            String newStreet = oldProfile.getStreet();
            String newHouseNumber = oldProfile.getHouseNumber();
            String newApartment = oldProfile.getApartment();
            Integer newBottomSize;
            if (position == -1){
                newBottomSize = null;
            } else {
                newBottomSize = sizeArray.keyAt(position);
            }
            Integer newTopSize = oldProfile.getTopSize();
            Profile newProfile = new Profile(newTel, newStreet, newHouseNumber, newApartment, newTopSize, newBottomSize);
            message.set(App.getInstance().getString(R.string.profile_message_to_user_saving));
            profileReplaySubject.onNext(newProfile);
            profileRepository.setProfile(newProfile)
                    .observeOn(mainThread)
                    .subscribe(
                            updatedProfile -> {
                                message.set(App.getInstance().getString(R.string.profile_message_to_user_saving_complete));
                            }, error -> {
                                Timber.e(error);
                                message.set(App.getInstance().getString(R.string.profile_message_to_user_error));
                                profileRepository.setProfile(oldProfile);
                            });
        }
    }

    @Override
    public LiveData<String> getCurrentTopSize() {
        return currentTopSize;
    }

    @Override
    public LiveData<String> getCurrentBottomSize() {
        return currentBottomSize;
    }

}
