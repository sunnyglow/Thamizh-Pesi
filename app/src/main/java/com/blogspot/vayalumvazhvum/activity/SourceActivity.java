package com.blogspot.vayalumvazhvum.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.examples.HtmlToPlainText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jetwick.snacktory.ArticleTextExtractor;
import org.jetwick.snacktory.JResult;
import org.jetwick.snacktory.ReFormatOutput;

public class SourceActivity  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sourceToolbar);
        setSupportActionBar(toolbar);
 /*       Window window = this.getWindow();
        window.setStatusBarColor (10);*/
        ImageView input = (ImageView) findViewById(R.id.input);
        ImageView browseWeb = (ImageView) findViewById(R.id.browseWeb);

        ImageView html = (ImageView) findViewById(R.id.htmlView);
        ImageView txt  = (ImageView) findViewById(R.id.txt);

        ImageView share = (ImageView) findViewById(R.id.share);
        ImageView like  = (ImageView) findViewById(R.id.like);


        input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "Input", Toast.LENGTH_SHORT).show();
                Intent inputActivity = new Intent(v.getContext(), InputReadActivity.class);
                startActivity(inputActivity);

            }
        });

        browseWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "Browse Web", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), BrowseModeActivity.class);
                startActivity(intent);

            }
        });


        html.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "HTML", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/html");
                startActivityForResult(intent, 5);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "Text", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");
                startActivityForResult(intent, 6);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "Text", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Thamizh Pesi is an highquality offline Tamil Text to Speech Application. \n Download from google play store. \n http://play.google.com/store/apps/details?id=com.blogspot.vayalumvazhvum.thamizhpesi");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.getId() will give you the image id
                Toast.makeText(v.getContext(), "Like Us..!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.blogspot.vayalumvazhvum.thamizhpesi"));
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch(requestCode){



            case 4:

                if(resultCode==RESULT_OK){

                    String PathHolder = data.getData().getPath();

                    Toast.makeText(this, PathHolder , Toast.LENGTH_LONG).show();

                }
                break;

            case 5:

                if(resultCode==RESULT_OK){

                    String pathHolder = data.getData().getLastPathSegment();
                    Toast.makeText(this, pathHolder , Toast.LENGTH_LONG).show();

                    String completeLine = "";
                    BufferedReader reader = null;
                    int counter = 1;
                    try {
                        reader = new BufferedReader(new FileReader(new File(pathHolder)));
                        String currentLine = "";


                        while ((currentLine = reader.readLine()) != null)
                        {
                            completeLine += currentLine;
                        }
                    }
                    catch(Exception exp)
                    {
                        exp.printStackTrace();
                    }
                    finally {
                        pathHolder = null;
                        if(reader != null){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    JResult result = new JResult();
                    ArticleTextExtractor extractor = new ArticleTextExtractor();
                    HtmlToPlainText formatter = new HtmlToPlainText();
                    try {
                        result  = extractor.extractContent(completeLine);
                        HTMLReadActivity.completeText = result.getTitle() + result.getText();
                        HTMLReadActivity.htmlText = ReFormatOutput.formateData(result.getTitle(), result.getImageUrl(), result.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent htmlIntent = new Intent(this, HTMLReadActivity.class);
                    startActivity(htmlIntent);
                }


                break;

            case 6:

                if(resultCode==RESULT_OK){

                    String pathHolder = data.getData().getLastPathSegment();
                    Toast.makeText(this, pathHolder , Toast.LENGTH_LONG).show();
                    String completeLine = "";
                    BufferedReader reader = null;
                    int counter = 1;
                    try {
                        reader = new BufferedReader(new FileReader(new File(pathHolder)));
                        String currentLine = "";


                        while ((currentLine = reader.readLine()) != null)
                        {
                            completeLine += currentLine;
                        }
                    }
                    catch(Exception exp)
                    {
                        exp.printStackTrace();
                    }
                    finally {
                        pathHolder = null;
                        if(reader != null){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    TextReadActivity.completeText = completeLine;
                    Intent textIntent = new Intent(this, TextReadActivity.class);
                    startActivity(textIntent);

                }
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
