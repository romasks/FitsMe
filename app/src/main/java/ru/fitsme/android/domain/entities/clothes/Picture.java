package ru.fitsme.android.domain.entities.clothes;

public class Picture {
    private long id;
    private String url;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return getId() == picture.getId() &&
                getUrl().equals(picture.getUrl());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int)(getId() ^ (getId() >>> 32));
        result = 31 * result + url.hashCode();
        return result;
    }
}
