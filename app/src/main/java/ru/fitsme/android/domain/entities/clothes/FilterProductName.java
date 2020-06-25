package ru.fitsme.android.domain.entities.clothes;

import ru.fitsme.android.data.frameworks.room.RoomProductName;

public class FilterProductName  implements ClotheFilter{

    private int id;
    private String title;
    private String type;
    private boolean isChecked;

    public FilterProductName(RoomProductName productName){
        this.id = productName.getId();
        this.title = productName.getTitle();
        this.type = productName.getType();
        this.isChecked = productName.isChecked();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getColorName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
