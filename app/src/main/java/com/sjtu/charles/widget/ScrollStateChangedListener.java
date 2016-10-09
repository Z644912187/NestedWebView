package com.sjtu.charles.widget;


public interface ScrollStateChangedListener {
    void onChildDirectionChange(int position);

    void onChildPositionChange(ScrollState parama);

    enum ScrollState {TOP, BOTTOM, MIDDLE, NO_SCROLL}
}