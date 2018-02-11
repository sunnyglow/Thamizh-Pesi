package com.blogspot.vayalumvazhvum.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class InputReadActivity extends AppCompatActivity {

    static MediaPlayer mSongPlayer = null;
    InputReadAsync inputReadAsync = null;
    static int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_read);

        final ImageView inputPlay = (ImageView) findViewById(R.id.readAloud);
        inputPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputReadAsync = new InputReadAsync();
                EditText text = (EditText) findViewById(R.id.textArea);
                index = 0;
                InputReadWebviewActivity.completeText = text.getText().toString();

                Intent inputWebviewActivity = new Intent(view.getContext(), InputReadWebviewActivity.class);
                startActivity(inputWebviewActivity);
            }
        });
    }
}
