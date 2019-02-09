package ru.fitsme.android.domain.interactors.clothes;

import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.signinup.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.domain.entities.clothes.LastItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class ClothesInteractor implements IClothesInteractor {

    private List<ClothesPage> clothesPages = new LinkedList<>();

    private SparseIntArray idToPage = new SparseIntArray();

    private IClothesRepository clothesRepository;
    private Scheduler workScheduler;

    public ClothesInteractor(IClothesRepository clothesRepository,
                             @Named("work") Scheduler workScheduler) {
        this.clothesRepository = clothesRepository;
        this.workScheduler = workScheduler;
    }

    @Override
    @NonNull
    public Single<ClothesItem> getNextClothesItem() {
        return Single.create((SingleOnSubscribe<ClothesItem>) emitter -> {
            try {
                updateIfNeeded();
                ClothesPage page = clothesPages.get(0);
                ClothesItem item = page.getItems().remove(0);
                idToPage.put(item.getId(), page.getNext() - 1);
                emitter.onSuccess(item);
            } catch (AppException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        }).subscribeOn(workScheduler);
    }

    @Override
    @NonNull
    public Completable rateClothesItem(@NonNull ClothesItem clothesItem, boolean like) {
        return Completable.create(emitter -> {
            clothesRepository.rateItem(clothesItem, like);
            int page = idToPage.get(clothesItem.getId());
            clothesRepository.setLastItem(page);
        }).subscribeOn(workScheduler);
    }

    private void nextLoad() throws AppException {
        ClothesPage clothesPage = clothesPages.get(clothesPages.size() - 1);
        clothesPages.add(clothesRepository.loadNextPage(clothesPage.getNext()));
    }

    private void initLoad() throws AppException {
        LastItem lastItem = clothesRepository.getLastPage();
        int index = lastItem.getIndex();

        ClothesPage clothesPage = clothesRepository.loadNextPage(lastItem.getPage());
        while (index > 0) {
            clothesPage.getItems().remove(index--);
        }
        if (clothesPage.getItems().size() > 0) {
            clothesPages.add(clothesPage);
        }
        if (clothesPage.getItems().size() < 2) {
            clothesPages.add(clothesRepository.loadNextPage(lastItem.getPage()));
        }
    }

    private void updateIfNeeded() throws AppException {
        if (clothesPages.size() == 0) {
            initLoad();
        } else if (clothesPages.size() == 1 && clothesPages.get(0).getItems().size() < 2) {
            nextLoad();
        }
    }
}
