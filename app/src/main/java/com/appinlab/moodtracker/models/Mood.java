package com.appinlab.moodtracker.models;

public class Mood {
    private int mColor;
    private int mAvatar;

    public Mood() {
    }

    public Mood(int color, int avatar) {
        mColor = color;
        mAvatar = avatar;
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
}

