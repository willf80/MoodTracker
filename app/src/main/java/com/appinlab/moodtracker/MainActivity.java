package com.appinlab.moodtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.appinlab.moodtracker.models.Mood;
import com.appinlab.moodtracker.models.MoodManager;
import com.appinlab.moodtracker.utils.Constants;
import com.appinlab.moodtracker.utils.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity implements CommentFragment.OnDispatcher {

    private FrameLayout mFrameLayout;
    private ImageView mImageView;

    private MoodManager mMoodManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment commentFragment = CommentFragment.newInstance();
                commentFragment.setOnDispatcher(MainActivity.this);
                commentFragment.show(getSupportFragmentManager(), CommentFragment.class.getName());
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyMoodScreen() {
        Mood mood = mMoodManager.getCurrentMood();
        mFrameLayout.setBackgroundColor(ContextCompat.getColor(this, mood.getColor()));
        mImageView.setImageResource(mood.getAvatar());
    }

    @Override
    public void onDoneClicked(String comment) {
        Log.d(Constants.TAG, "onDoneClicked: " + comment);
    }
}
