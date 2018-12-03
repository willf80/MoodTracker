package com.appinlab.moodtracker.utils;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()//For Dev Mode
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
