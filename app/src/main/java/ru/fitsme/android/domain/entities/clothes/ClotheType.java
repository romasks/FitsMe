package ru.fitsme.android.domain.entities.clothes;

public class ClotheType {
    private String title;
    private String type;

    public String getTitle() {
        return title;
    }

    public Type getType() {
        switch (type){
            case "TOP":{
                return Type.TOP;
            }
            case "BOT":{
                return Type.BOTTOM;
            }
            default:
                throw new IllegalStateException("Unknown Type ClotheType state");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClotheType)) return false;
        ClotheType picture = (ClotheType) o;
        return getTitle().equals(picture.getTitle()) &&
                getType().equals(picture.getType());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + title.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public enum Type {
        TOP, BOTTOM
    }
}
