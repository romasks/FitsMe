package ru.fitsme.android.domain.interactors.clothes;

import java.util.LinkedList;

import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

class PreviousClotheInfoList {

    private LinkedList<ClotheInfo> clotheInfoList = new LinkedList<>();
    private static final int LIST_SIZE = 4;

    void add(ClotheInfo clotheInfo){
        clotheInfoList.add(clotheInfo);
        if (clotheInfoList.size() > LIST_SIZE){
            clotheInfoList.removeFirst();
        }
    }

    ClotheInfo peekLast(){
        return clotheInfoList.peekLast();
    }

    boolean hasPrevious() {
        return clotheInfoList.size() > 2;
    }
}
