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

import org.jetwick.snacktory.ReFormatOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReadWebviewActivity extends AppCompatActivity {

    float globalPlaybackSpeed = 1.0f;
    WebView inputWebView = null;
    public static String completeText = "";

    static List<String> playList = null;
    static MediaPlayer mSongPlayer = null;
    static InitiateSpeech initiateSpeech = new InitiateSpeech();
    InputReadAsync inputReadAsync = null;
    SeekBar seekBar = null;
    static String line[] = null;
    static int index = 0;
    boolean paused = false;
    boolean playFromBegining = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_read_webview);
        seekBar = (SeekBar) findViewById(R.id.inputTempo);
        seekBar.setMax(10);
        seekBar.setProgress(5);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            seekBar.setVisibility(View.INVISIBLE);
        }
        playList = new ArrayList<String>();
        inputReadAsync = new InputReadAsync();
        ImageView htmlPlay = (ImageView) findViewById(R.id.inputPlay);
        htmlPlay.setOnClickListener(new View.OnClickListener() {
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
                if(!paused && (playFromBegining || !inputReadAsync.executed))
                {
                    playFromBegining = false;
                    index = 0;

                    inputReadAsync.breakFlag = false;

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
                                    inputWebView.findAllAsync(line[index]);
                                    mp.prepareAsync();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mSongPlayer.start();
                    inputWebView.findAllAsync(line[0]);
                    if(!inputReadAsync.executed) {
                        inputReadAsync.text = completeText;
                        inputReadAsync.executed = true;
                        inputReadAsync.execute();
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

        ImageView htmlPause = (ImageView) findViewById(R.id.inputPause);
        htmlPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSongPlayer != null && mSongPlayer.isPlaying())
                {
                    mSongPlayer.pause();
                    paused = true;
                }
            }
        });


        ImageView htmlPrev = (ImageView) findViewById(R.id.inputPrev);
        htmlPrev.setOnClickListener(new View.OnClickListener() {
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
                        inputWebView.findAllAsync(line[index]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ImageView htmlNext = (ImageView) findViewById(R.id.inputNext);
        htmlNext.setOnClickListener(new View.OnClickListener() {
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
                        inputWebView.findAllAsync(line[index]);
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
                        inputWebView.findAllAsync(line[index]);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar:"+ progress, Toast.LENGTH_SHORT).show();
            }
        });

        inputWebView = (WebView) findViewById(R.id.inputRead);
        inputWebView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = inputWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String html = ReFormatOutput.formateData("","",completeText);
        inputWebView.loadData(html, "text/html", null);
        inputWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

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
    protected void onPause() {
        super.onPause();
        if(inputReadAsync != null){
            inputReadAsync.breakFlag = true;
        }
    }

}
