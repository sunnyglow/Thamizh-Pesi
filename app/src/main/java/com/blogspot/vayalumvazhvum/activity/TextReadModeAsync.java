package com.blogspot.vayalumvazhvum.activity;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Sureshkumar on 11/21/2017.
 */

public class TextReadModeAsync extends AsyncTask<URL, String, String> {
    public String text = null;
    public boolean breakFlag = false;
    boolean executed = false;
    @Override
    protected String doInBackground(URL... urls) {
        executed = true;
        String line[] = TextReadActivity.line;
        for(int i = 1; i < line.length ; i++)
        {
            if(breakFlag)
            {
                breakFlag = false;
                break;
            }
            TextReadActivity.initiateSpeech.initiateSpeech(line[i], "Line"+(i)+".wav");
            TextReadActivity.playList.add("Line"+(i)+".wav");
        }
        return "success";
    }

}
