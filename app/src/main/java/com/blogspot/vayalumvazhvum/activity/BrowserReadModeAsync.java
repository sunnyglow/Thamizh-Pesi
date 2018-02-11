package com.blogspot.vayalumvazhvum.activity;

import android.os.AsyncTask;

import java.net.URL;

import org.jetwick.snacktory.HtmlFetcher;
import org.jetwick.snacktory.JResult;
import org.jetwick.snacktory.ReFormatOutput;

/**
 * Created by Sureshkumar on 11/16/2017.
 */

public class BrowserReadModeAsync extends AsyncTask<URL, String, String> {
    // Do the long-running work in here
    static String clean_result = "";
    protected String doInBackground(URL... urls)
    {
        String result = "";
        try {


            HtmlFetcher fetcher = new HtmlFetcher();
            String articleUrl = BrowseModeActivity.url;
            JResult res = fetcher.fetchAndExtract(articleUrl, 10 * 1000, true);
            String text = res.getText();
            String title = res.getTitle();
            String imageUrl = res.getImageUrl();
            //System.out.println(text);
            //System.out.println(title);
            //System.out.println(imageUrl);
            result = ReFormatOutput.formateData("", "", res.getActualContent());
            clean_result = "";
            clean_result = text;
            System.out.println(result);
           // ReadModeActivity.readWebView.loadData(result, "text/html", null);
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }

        return result;
    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(String... progress)
    {

    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(String result)
    {

    }
}