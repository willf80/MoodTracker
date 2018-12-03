package com.appinlab.moodtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.appinlab.moodtracker.models.Mood;
import com.appinlab.moodtracker.models.MoodManager;
import com.appinlab.moodtracker.models.MoodStore;
import com.appinlab.moodtracker.utils.Constants;
import com.appinlab.moodtracker.utils.OnSwipeTouchListener;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements CommentFragment.OnDispatcher {

    private FrameLayout mFrameLayout;
    private ImageView mImageView;

    private MoodManager mMoodManager;
    private Realm mRealm;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        mMoodManager = new MoodManager();

        mFrameLayout = findViewById(R.id.frame);
        mImageView = findViewById(R.id.smiley_image);
        ImageButton buttonAddComment = findViewById(R.id.btn_add_comment);
        ImageButton buttonHistory = findViewById(R.id.btn_history);

        //Apply last Mood screen
        applyMoodScreen();

        mFrameLayout.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeTop() {
                mMoodManager.getNextMood();
                applyMoodScreen();
            }

            @Override
            public void onSwipeBottom() {
                mMoodManager.getPreviousMood();
                applyMoodScreen();
            }
        });

        buttonAddComment.setOnClickListener(v -> {
            CommentFragment commentFragment = CommentFragment.newInstance();
            commentFragment.setOnDispatcher(MainActivity.this);
            commentFragment.show(getSupportFragmentManager(), CommentFragment.class.getName());
        });

        buttonHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        saveMood(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Closes the Realm instance and all its resources.
        if(mRealm != null) {
            mRealm.close();
        }
    }

    private void applyMoodScreen() {
        Mood mood = mMoodManager.getCurrentMood();
        mFrameLayout.setBackgroundColor(ContextCompat.getColor(this, mood.getColor()));
        mImageView.setImageResource(mood.getAvatar());
    }

    @Override
    public void onDoneClicked(String comment) {
        saveMood(comment);

        //Date save with success Toast
        Toast.makeText(this, "Commentaire enregisté avec succès !", Toast.LENGTH_LONG).show();
    }

    private void saveMood(String comment) {
        //Try to get today mood in database.
        //If not exist, we create else we updated the last one
        MoodStore moodStore = getTodayMood();//null
        Mood mood = mMoodManager.getCurrentMood();

        Calendar now = Calendar.getInstance();
//        now.add(Calendar.DAY_OF_MONTH, -7);

        mRealm.beginTransaction();
        if(moodStore == null) {
            String id = Constants.dateToStringFormat(now.getTime(), "yyyyMMdd");

            moodStore = new MoodStore();
            moodStore.setId(id);
        }

        moodStore.setIndex(mood.getIndex());
        moodStore.setColor(mood.getColor());
        moodStore.setImage(mood.getAvatar());
        if(comment == null) {
            moodStore.setComment(moodStore.getComment());
        }else {
            moodStore.setComment(comment);
        }

        moodStore.setDateAdd(now.getTime());

        //Persistence
        mRealm.copyToRealmOrUpdate(moodStore);
        mRealm.commitTransaction();
    }

    private MoodStore getTodayMood() {
        Date date = Calendar.getInstance().getTime();
        String id = Constants.dateToStringFormat(date, "yyyyMMdd");

        return mRealm.where(MoodStore.class)
                .equalTo("id", id).findFirst();
    }
}
