package com.blogspot.vayalumvazhvum.activity;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetwick.snacktory.ReFormatOutput;

public class TextReadActivity extends AppCompatActivity {

    TextReadModeAsync textReadModeAsync = null;
    public WebView textReadWebView = null;
    public static String completeText = "";
    static List<String> playList = null;
    static MediaPlayer mSongPlayer = null;
    static String line[] = null;
    SeekBar seekBar = null;
    static InitiateSpeech initiateSpeech = new InitiateSpeech();
    static int index = 0;
    float globalPlaybackSpeed = 1.0f;
    boolean paused = false;
    boolean playFromBegining = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_read);
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        seekBar = (SeekBar) findViewById(R.id.textTempo);
        seekBar.setMax(10);
        seekBar.setProgress(5);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            seekBar.setVisibility(View.INVISIBLE);
        }
        playList = new ArrayList<String>();
        textReadModeAsync = new TextReadModeAsync();
        ImageView textPlay = (ImageView) findViewById(R.id.textPlay);
        textPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(completeText == null || completeText.isEmpty())
                {
                    return;
                }
                if(index >= playList.size() - 1)
                {
                    playFromBegining = true;
                }

                if(!paused && (playFromBegining || !textReadModeAsync.executed))
                {
                    playFromBegining = false;
                    index = 0;

                    textReadModeAsync.breakFlag = false;
                    if(playList.size() <= 0) {
                        line = completeText.split("\\. |\\?");
                        playList.add("line0.wav");
                        initiateSpeech.initiateSpeech(line[0], "Line" + (0) + ".wav");
                    }
                    if(mSongPlayer != null)
                    {

                        if(mSongPlayer.isPlaying()) {
                            mSongPlayer.stop();
                        }
                        mSongPlayer.reset();
                    }
                    mSongPlayer = MediaPlayer.create(view.getContext(), Uri.parse(SplashScreenActivity.GENERATED_VOICE_PATH +"/"+"Line0.wav"));
                    if(mSongPlayer == null)
                    {
                        return;
                    }
                    mSongPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            if (mp == mSongPlayer) {
                                setPlayBackParam();
                                mSongPlayer.start();
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
                                    textReadWebView.findAllAsync(line[index]);
                                    mp.prepareAsync();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mSongPlayer.start();
                    textReadWebView.findAllAsync(line[0]);
                    if(!textReadModeAsync.executed) {
                        textReadModeAsync.text = completeText;
                        textReadModeAsync.execute();
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

        ImageView textPause = (ImageView) findViewById(R.id.textPause);
        textPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSongPlayer != null && mSongPlayer.isPlaying())
                {
                    mSongPlayer.pause();
                    paused = true;
                }
            }
        });


        ImageView textPrev = (ImageView) findViewById(R.id.textPrev);
        textPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(playList != null && index > 0 && index <= playList.size() - 1)
                {
                    if(mSongPlayer != null && mSongPlayer.isPlaying()){
                        mSongPlayer.stop();
                    }
                    try
                    {
                        mSongPlayer.reset();
                        mSongPlayer.setDataSource(SplashScreenActivity.GENERATED_VOICE_PATH + "/" + playList.get(--index));
                        mSongPlayer.prepareAsync();
                        textReadWebView.findAllAsync(line[index]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ImageView textNext = (ImageView) findViewById(R.id.textNext);
        textNext.setOnClickListener(new View.OnClickListener() {
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
                        textReadWebView.findAllAsync(line[index]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                        textReadWebView.findAllAsync(line[index]);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar:"+ progress, Toast.LENGTH_SHORT).show();
            }
        });

        textReadWebView = (WebView) findViewById(R.id.textRead);
        textReadWebView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = textReadWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String html = ReFormatOutput.formateData("","",completeText);
        //textReadWebView.loadData(html, "text/html", null);
        textReadWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);


    }

    void setPlayBackParam()
    {
        if(mSongPlayer == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSongPlayer.setPlaybackParams(mSongPlayer.getPlaybackParams().setSpeed(globalPlaybackSpeed));
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
        if(textReadModeAsync != null){
            textReadModeAsync.breakFlag = true;
        }
    }
}
