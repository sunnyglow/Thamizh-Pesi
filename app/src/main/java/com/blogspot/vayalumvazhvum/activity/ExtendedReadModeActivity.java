package com.blogspot.vayalumvazhvum.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.tamil.thamizhpesi.constants.TamilTTSConstants;
import com.tamil.thamizhpesi.initiate.InitiateSpeech;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExtendedReadModeActivity extends AppCompatActivity {
    public WebView readWebView = null;
    static List<String> playList = null;
    static MediaPlayer mSongPlayer = null;
    static int index = 0;
    static String line[] = null;
    PrepareSpeechAsync prepareSpeechAsync = null;
    static InitiateSpeech initiateSpeech = new InitiateSpeech();
    SeekBar seekBar = null;
    float globalPlaybackSpeed = 1.0f;
    boolean paused = false;
    boolean playFromBegining = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_read_mode);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        seekBar = (SeekBar) findViewById(R.id.tempo);
        seekBar.setMax(10);
        seekBar.setProgress(5);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            seekBar.setVisibility(View.INVISIBLE);
        }
        playList = new ArrayList<String>();
        prepareSpeechAsync = new PrepareSpeechAsync();
        ImageView play = (ImageView) findViewById(R.id.play);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c = view.getContext();

                if(BrowserReadModeAsync.clean_result == null || BrowserReadModeAsync.clean_result.isEmpty())
                {
                    return;
                }
                if(index >= playList.size() - 1)
                {
                    playFromBegining = true;
                }

                if(!paused && (playFromBegining || !prepareSpeechAsync.executed))
                {
                    playFromBegining = false;
                    ///prepareSpeechAsync.executed = false;
                    index = 0;
                    prepareSpeechAsync.breakFlag = false;


                    if(playList.size() <= 0) {
                        line = BrowserReadModeAsync.clean_result.split("\\. |\\?");
                        playList.add("line0.wav");
                        initiateSpeech.initiateSpeech(line[0], "Line" + (0) + ".wav");
                    }
                    if (mSongPlayer != null) {

                        if (mSongPlayer.isPlaying()) {
                            mSongPlayer.stop();
                        }
                        mSongPlayer.reset();
                    }
                    mSongPlayer = MediaPlayer.create(view.getContext(), Uri.parse(SplashScreenActivity.GENERATED_VOICE_PATH+ "/" + "Line0.wav"));
                    if(mSongPlayer == null)
                    {
                        return;
                    }



                    mSongPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            if (mp == mSongPlayer) {
                                setPlayBackParam();
                                mp.start();
                            }
                        }
                    });

                    mSongPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();
                            mp.reset();
                            if(index < playList.size() - 1) {
                                try {
                                    mp.setDataSource(SplashScreenActivity.GENERATED_VOICE_PATH + "/" + playList.get(++index));
                                    readWebView.findAllAsync(line[index]);
                                    mp.prepareAsync();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    readWebView.findAllAsync(line[0]);
                    setPlayBackParam();
                    mSongPlayer.start();
                    if (!prepareSpeechAsync.executed) {
                        prepareSpeechAsync.executed = true;
                        prepareSpeechAsync.execute("");
                    }

                }
                else if(mSongPlayer != null && !mSongPlayer.isPlaying() && paused)
                {
                    paused = false;
                    setPlayBackParam();
                    mSongPlayer.start();
                }
            }
        });


        final ImageView pause = (ImageView) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view)
               {
                    if(mSongPlayer != null && mSongPlayer.isPlaying())
                    {
                        mSongPlayer.pause();
                        paused = true;
                    }
                    /*else if(mSongPlayer != null)
                    {
                        mSongPlayer.start();
                    }*/
               }
        });


        ImageView prev = (ImageView) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playList != null && index > 0 && index < playList.size() - 1)
                {
                    if(mSongPlayer != null && mSongPlayer.isPlaying()){
                        mSongPlayer.stop();
                    }
                    try
                    {
                        mSongPlayer.reset();
                        mSongPlayer.setDataSource(SplashScreenActivity.GENERATED_VOICE_PATH + "/" + playList.get(--index));
                        mSongPlayer.prepareAsync();
                        readWebView.findAllAsync(line[index].trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(playList != null && index >= 0 && index < playList.size() - 1 && playList != null) {
                    if(mSongPlayer != null && mSongPlayer.isPlaying())
                    {
                        mSongPlayer.stop();
                    }
                    try
                    {
                        mSongPlayer.reset();
                        mSongPlayer.setDataSource(SplashScreenActivity.GENERATED_VOICE_PATH + "/" + playList.get(++index));
                        mSongPlayer.prepareAsync();
                        readWebView.findAllAsync(line[index].trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Intent intent = getIntent();

        readWebView = (WebView) findViewById(R.id.extendedReadMode);
        readWebView.setWebViewClient(new TamilTTSWebViewClient());

        // Enable Javascript
        WebSettings webSettings = readWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 5;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                int value = progress;
                if(value == 0){
                    globalPlaybackSpeed = 0.5f;
                }
                if(value == 1){
                    globalPlaybackSpeed = 0.6f;
                }
                if(value == 2){
                    globalPlaybackSpeed = 0.7f;
                }
                if(value == 3){
                    globalPlaybackSpeed = 0.8f;
                }
                if(value == 4){
                    globalPlaybackSpeed = 0.9f;
                }
                if(value == 5){
                    globalPlaybackSpeed = 1.0f;
                }
                if(value == 6){
                    globalPlaybackSpeed = 1.1f;
                }
                if(value == 7){
                    globalPlaybackSpeed = 1.2f;
                }
                if(value == 8){
                    globalPlaybackSpeed = 1.3f;
                }
                if(value == 9){
                    globalPlaybackSpeed = 1.4f;
                }
                if(value == 10){
                    globalPlaybackSpeed = 1.5f;
                }


                if(mSongPlayer != null  && playList.size() > 0)
                {
                    if(mSongPlayer.isPlaying()){
                        mSongPlayer.stop();
                    }
                    try
                    {
                        mSongPlayer.reset();
                        mSongPlayer.setDataSource(SplashScreenActivity.GENERATED_VOICE_PATH + "/" + playList.get(index));

                        //mSongPlayer.setPlaybackParams(mSongPlayer.getPlaybackParams().setSpeed(globalPlaybackSpeed));
                        mSongPlayer.prepareAsync();
                        readWebView.findAllAsync(line[index].trim());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar:"+ progress, Toast.LENGTH_SHORT).show();
            }
        });
        // Use remote resource

        BrowserReadModeAsync rasync = new BrowserReadModeAsync();
        try {
            String result = rasync.execute().get();

            //readWebView.loadData(result, "text/html",  "UTF-8");
            readWebView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    void setPlayBackParam()
    {
        if(mSongPlayer == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(mSongPlayer.isPlaying()){
                mSongPlayer.pause();
            }
            mSongPlayer.setPlaybackParams(mSongPlayer.getPlaybackParams().setSpeed(globalPlaybackSpeed));
        }
    }
    void deleteRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public static List<String> getPlayList() {
        return playList;
    }


    class TamilTTSWebViewClient extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(mSongPlayer != null) {
                if(mSongPlayer.isPlaying())
                {
                    mSongPlayer.stop();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(prepareSpeechAsync != null){
            prepareSpeechAsync.breakFlag = true;
        }
    }
}
