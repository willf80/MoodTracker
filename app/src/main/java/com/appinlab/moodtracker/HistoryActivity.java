package com.appinlab.moodtracker;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.appinlab.moodtracker.adapters.HistoryAdpater;
import com.appinlab.moodtracker.models.MoodStore;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class HistoryActivity extends AppCompatActivity implements HistoryAdpater.OnDispatcher {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRealm = Realm.getDefaultInstance();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<MoodStore> moodStoreList = mRealm.where(MoodStore.class)
                .sort("id", Sort.DESCENDING)
                .limit(8)
                .findAll()
                .sort("id", Sort.ASCENDING);

        HistoryAdpater historyAdpater = new HistoryAdpater(moodStoreList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdpater);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MoodStore moodStore) {
        Toast.makeText(this, moodStore.getComment(), Toast.LENGTH_LONG).show();
    }
}
