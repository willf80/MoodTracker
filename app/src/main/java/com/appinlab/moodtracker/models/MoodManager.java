package com.appinlab.moodtracker.models;

import com.appinlab.moodtracker.R;
import com.appinlab.moodtracker.utils.Constants;

import java.util.Calendar;

import io.realm.Realm;

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
        MoodStore moodStore = getTodayMoodStore();

        if(moodStore == null){
            mIndex = 3;//set default to Happy Smiley
        }else {
            mIndex = moodStore.getIndex();
        }
    }

    private MoodStore getTodayMoodStore() {
        String id = Constants.dateToStringFormat(Calendar.getInstance().getTime(), "yyyyMMdd");
        return mRealm.where(MoodStore.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void getNextMood() {
        mIndex++;
        if(mIndex > MAX) {
            mIndex = 0;
        }
    }

    public void getPreviousMood() {
        mIndex--;
        if(mIndex < 0) {
            mIndex = MAX;
        }
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
