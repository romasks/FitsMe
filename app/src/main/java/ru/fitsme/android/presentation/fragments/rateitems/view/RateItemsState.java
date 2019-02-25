package ru.fitsme.android.presentation.fragments.rateitems.view;

public class RateItemsState {
    private int index;
    private int count;
    private State state;

    public RateItemsState(int index, int count) {
        this.index = index;
        this.count = count;
        this.state = State.OK;
    }

    public RateItemsState(State state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public int getCount() {
        return count;
    }

    public State getState() {
        return state;
    }

    enum State {
        LOADING, ERROR, OK
    }
}
