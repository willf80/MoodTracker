package com.appinlab.moodtracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.appinlab.moodtracker.models.Mood;
import com.appinlab.moodtracker.models.MoodManager;
import com.appinlab.moodtracker.utils.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private ImageView mImageView;
    private ImageButton mButtonAddComment;
    private ImageButton mButtonHistory;

    private MoodManager mMoodManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoodManager = new MoodManager();

        mFrameLayout = findViewById(R.id.frame);
        mImageView = findViewById(R.id.smiley_image);
        mButtonAddComment = findViewById(R.id.btn_add_comment);
        mButtonHistory = findViewById(R.id.btn_history);

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

        mButtonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment commentFragment = CommentFragment.newInstance();
                commentFragment.show(getSupportFragmentManager(), CommentFragment.class.getName());
            }
        });

        mButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void applyMoodScreen() {
        Mood mood = mMoodManager.getCurrentMood();
        mFrameLayout.setBackgroundColor(ContextCompat.getColor(this, mood.getColor()));
        mImageView.setImageResource(mood.getAvatar());
    }
}
