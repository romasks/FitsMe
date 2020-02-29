package ru.fitsme.android.presentation.fragments.profile.view;

import android.util.SparseArray;

public class SizeObserver implements SizeButton.OnSizeButtonClickCallback {
    private SparseArray<SizeButton> buttons = new SparseArray<>();

    private int checkedSizeIndex = -1;
    private Callback callback;
    private int tag;

    SizeObserver(Callback callback, int tag){
        this.callback = callback;
        this.tag = tag;
    }

    @Override
    public void setState(int id, Boolean isChecked) {
        if (isChecked){
            if (checkedSizeIndex != -1) {
                SizeButton button = buttons.get(checkedSizeIndex);
                if (button != null) {
                    button.setChecked(false);
                }
            }
            checkedSizeIndex = id;
            SizeButton button = buttons.get(id);
            if (button != null) {
                button.setChecked(true);
            }
        } else {
            checkedSizeIndex = -1;
            SizeButton button = buttons.get(id);
            if (button != null) {
                buttons.get(id).setChecked(false);
            }
        }
        callback.onSizeValueSelected(tag, checkedSizeIndex);
    }

    @Override
    public void addButton(int id, SizeButton button){
        buttons.put(id, button);
    }

    public void setCheckedSizeIndex(int checkedSizeIndex) {
        this.checkedSizeIndex = checkedSizeIndex;
    }

    interface Callback{
        void onSizeValueSelected(int tag, int id);
    }
}
