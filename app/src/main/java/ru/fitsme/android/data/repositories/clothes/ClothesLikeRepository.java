package ru.fitsme.android.data.repositories.clothes;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.boundaries.clothes.IClothesLikeRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class ClothesLikeRepository implements IClothesLikeRepository {

    private final WebLoader webLoader;

    @Inject
    ClothesLikeRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @Override
    public void likeItem(@NonNull String token, int id, boolean liked) throws AppException {
        webLoader.likeItem(token, id, liked);
    }
}
