package com.appinlab.moodtracker.models;

public class Mood {
    private int mColor;
    private int mAvatar;
    private int mIndex;

    public Mood() {
    }

    public Mood(int index, int color, int avatar) {
        mColor = color;
        mAvatar = avatar;
        mIndex = index;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int avatar) {
        mAvatar = avatar;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }
}

