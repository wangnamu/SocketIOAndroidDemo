package com.ufo.tools;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tjpld on 2017/5/10.
 */

public class RealmConfig {

    public static void setUp(Context context, String username) {

        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(username + ".realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);


        Log.d("path->",Realm.getDefaultInstance().getPath());
    }
}
