package com.blogspot.vayalumvazhvum.activity;

import android.os.AsyncTask;

/**
 * Created by Sureshkumar on 11/18/2017.
 */

public class PrepareSpeechAsync  extends AsyncTask<String, String, String> {

    public static boolean breakFlag = false;
    boolean executed = false;
    @Override
    protected String doInBackground(String... urls)
    {
        executed = true;
        String line[] = ExtendedReadModeActivity.line;
        for(int i = 1; i < line.length ; i++)
        {
            if(breakFlag)
            {
                breakFlag = false;
                break;
            }
            ExtendedReadModeActivity.initiateSpeech.initiateSpeech(line[i], "Line"+(i)+".wav");
            ExtendedReadModeActivity.playList.add("Line"+(i)+".wav");
        }
        return "success";
    }
}
