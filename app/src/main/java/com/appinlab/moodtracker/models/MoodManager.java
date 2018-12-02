package com.appinlab.moodtracker.models;

import com.appinlab.moodtracker.R;

public class MoodManager {
    private int mIndex;
    private final int MAX = 4;

    private int[] mColors = new int[]{
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow
    };

    private int[] mImages = new int[]{
            R.drawable.smiley_sad,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,
            R.drawable.smiley_happy,
            R.drawable.smiley_super_happy,
    };

    public MoodManager() {
        mIndex = 2;
    }

    public Mood getNextMood() {
        mIndex++;
        if(mIndex > MAX) {
            mIndex = 0;
        }
        return generateMood();
    }

    public Mood getPreviousMood() {
        mIndex--;
        if(mIndex < 0) {
            mIndex = MAX;
        }
        return generateMood();
    }

    public Mood getCurrentMood() {
        return generateMood();
    }

    private Mood generateMood() {
        return new Mood(mColors[mIndex], mImages[mIndex]);
    }
}
