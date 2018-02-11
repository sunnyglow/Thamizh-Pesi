package com.blogspot.vayalumvazhvum.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.tamil.thamizhpesi.constants.TamilTTSConstants;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class SplashScreenActivity extends AppCompatActivity {
    private final static String EXP_PATH = "/Android/obb/";
    private final static String DATA_PATH = "/Android/data/";
    public static String OBB_PATH = "";
    public static ZipResourceFile expansionFile = null;
    public static Context applicationContext = null;
    public static String GENERATED_VOICE_PATH = "";
    public static final int EXPANSION_FILE_VERSION = 1;
    public static final int PATCH_FILE_VERSION = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 0 ;
    Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        applicationContext = getApplicationContext();

        context = this.getBaseContext();
        /*String filePath = Environment.getDataDirectory().getPath()+ TamilTTSConstants.GENERATED_VOICE_PATH+"/";
        File file = new File(filePath);
        if(!file.exists())
        {
            file.mkdirs();
        }*/

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
        }
        else
        {
            doIntializations();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    doIntializations();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    static String[] getAPKExpansionFiles(Context ctx, int mainVersion,
                                         int patchVersion) {
        String packageName = ctx.getPackageName();
        Vector<String> ret = new Vector<String>();
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            // Build the full path to the app's expansion files
            File root = Environment.getExternalStorageDirectory();
            File expPath = new File(root.toString() + EXP_PATH + packageName);

            // Check that expansion file path exists
            if (expPath.exists()) {
                if ( mainVersion > 0 ) {
                    String strMainPath = expPath + File.separator + "main." +
                            mainVersion + "." + packageName + ".obb";
                    File main = new File(strMainPath);
                    if ( main.isFile() ) {
                        ret.add(strMainPath);
                    }
                }
                if ( patchVersion > 0 ) {
                    String strPatchPath = expPath + File.separator + "patch." +
                            mainVersion + "." + packageName + ".obb";
                    File main = new File(strPatchPath);
                    if ( main.isFile() ) {
                        ret.add(strPatchPath);
                    }
                }
            }
        }
        String[] retArray = new String[ret.size()];
        ret.toArray(retArray);
        return retArray;
    }

    public void doIntializations()
    {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String packageName = context.getPackageName();
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))
        {
            File root = Environment.getExternalStorageDirectory();
            GENERATED_VOICE_PATH = root.toString() + DATA_PATH + packageName +TamilTTSConstants.GENERATED_VOICE_PATH;
            File expPath = new File(GENERATED_VOICE_PATH);

            // Check that expansion file path exists
            if (!expPath.exists())
            {
                Boolean status = expPath.mkdirs();
            }
        }
        if(GENERATED_VOICE_PATH != null || !GENERATED_VOICE_PATH.isEmpty()) {
            deleteRecursive(new File(GENERATED_VOICE_PATH));
        }
        // GENERATED_VOICE_PATH = filePath;
        String path[] = getAPKExpansionFiles(applicationContext,EXPANSION_FILE_VERSION,PATCH_FILE_VERSION);

        if(path.length <= 0)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Application is Missing Necessary OBB file. \n Without this file application cannot run. \n Please uninstall and re-install the app from Play Store");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else
        {
            OBB_PATH = path[0];
            try {
                expansionFile = new ZipResourceFile(OBB_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }

            View root = imageView.getRootView();
            root.setBackgroundColor(getResources().getColor(android.R.color.white));
            InitializeObjectsAsync iniAsync = new InitializeObjectsAsync();
            iniAsync.execute();
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(context, SourceActivity.class);
                    startActivity(intent);
                }

            }, 2000L);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    public void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
        {
            for (File child : fileOrDirectory.listFiles()) {
                child.delete();
            }
        }


    }
}
