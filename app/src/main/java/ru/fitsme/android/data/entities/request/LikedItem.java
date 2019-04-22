package ru.fitsme.android.data.entities.request;

public class LikedItem {
    private int clothe_id;
    private boolean liked;

    public LikedItem(int clothe_id, boolean liked) {
        this.clothe_id = clothe_id;
        this.liked = liked;
    }

    public int getClotheId() {
        return clothe_id;
    }

    public boolean isLiked() {
        return liked;
    }
}
