package com.appinlab.moodtracker.models;

import com.appinlab.moodtracker.R;

import io.realm.Realm;
import io.realm.Sort;

public class MoodManager {
    private int mIndex;
    private final int MAX = 4;
    private Realm mRealm;

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

    private String[] mDescriptions = new String[]{
            "Très mauvaise humeur",
            "Mauvaise humeur",
            "Humeur normale",
            "Bonne humeur",
            "Super bonne humeur",
    };

    public MoodManager() {
        mRealm = Realm.getDefaultInstance();
        MoodStore moodStore = getLastMoodStore();

        if(moodStore == null){
            mIndex = 2;
        }else {
            mIndex = moodStore.getIndex();
        }
    }

    private MoodStore getLastMoodStore() {
        return mRealm.where(MoodStore.class).sort("id", Sort.DESCENDING)
                .findFirst();
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

    public String getCurrentDescription() {
        return mDescriptions[mIndex];
    }

    public Mood getCurrentMood() {
        return generateMood();
    }

    private Mood generateMood() {
        return new Mood(mIndex, mColors[mIndex], mImages[mIndex]);
    }
}
