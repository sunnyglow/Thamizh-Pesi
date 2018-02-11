package com.blogspot.vayalumvazhvum.activity;

import android.os.AsyncTask;

import com.tamil.thamizhpesi.builder.InitializeObjects;

/**
 * Created by Sureshkumar on 11/18/2017.
 */

public class InitializeObjectsAsync extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        InitializeObjects.initializeTimeDataObjects();
        return null;
    }
}
