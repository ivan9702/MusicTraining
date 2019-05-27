package com.example.musictraining;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.SystemClock.sleep;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    static Context context;;
    //Bitmap bitmap;
    //String path;
    static Boolean threadflag =true;
    private ImageView ivScreenimage, imageView5;
    private TextView  tvStars,tvHalfStar,tvErrors;
    TextView tvOpDate;
    private String sharePath="no";
    Bitmap mbitmap;
    Handler  myHandler;
    static MediaPlayer mediaPlayer1;
        @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedata0 = getSharedPreferences("award", MODE_PRIVATE);
        //SharedPreferences.Editor sharedata = getSharedPreferences("award", 0).edit();

            MainActivity.context = getApplicationContext();
        ivScreenimage = findViewById(R.id.ivScreenImage);

        tvStars = findViewById(R.id.tvStars);
        tvHalfStar = findViewById(R.id.tvHalfStar);
        tvErrors = findViewById(R.id.tvErrors);

        imageView5 = findViewById(R.id.imageView5);
        tvOpDate = findViewById(R.id.tvOpDate);

        Log.d("Main onCreate", "stars:"+sharedata0.getInt("stars",0));
        askPermissionAndWriteFile();
        //askPermissionAndReadFile();

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadflag = false;
                Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
                startActivity(intent);
            }
        });
        myHandler = new Handler() {// 创建一个Handler对象
            public void handleMessage(Message msg) {// 重写接收消息的方法
                switch (msg.what) {// 判断what的值
                    case 0:// what值为0时
                        imageView5.setImageResource(R.drawable.music1);
                        break;
                    case 1:// what值为1时
                        imageView5.setImageResource(R.drawable.music);
                        break;
                }
                super.handleMessage(msg);
            }
        };

        tvOpDate.setText("上一次測試日期為：　"+sharedata0.getString("date", "0"));


    }

    @Override
    protected void onResume() {
        super.onResume();
        threadflag = true;
        new Thread() {
            public void run() {
                int i = 0;
                while (threadflag) {// 循环
                    Log.d("Main onCreate", "while loop i="+i);
                    myHandler.sendEmptyMessage((i++) % 2);// 发送消息
//                    System.out.println("handler的ID--->"
//                            + Thread.currentThread().getId());
//                    System.out.println("handler的名称--->"
//                            + Thread.currentThread().getName());
                    try {
                        Thread.sleep(800);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
        }.start();
        SharedPreferences sharedata0 = getSharedPreferences("award", MODE_PRIVATE);

        Log.d("Main onResume", "stars:"+sharedata0.getInt("stars",0));
        Log.d("Main onResume", "starHalf:"+sharedata0.getBoolean("starHalf",false));
        Log.d("Main onResume", "errorCount:"+sharedata0.getInt("errorCount",0));

        Log.d("Main onResume", "addBasic:"+sharedata0.getInt("addBasic",0));
        Log.d("Main onResume", "addMedium:"+sharedata0.getInt("addMedium",0));
        Log.d("Main onResume", "addHigh:"+sharedata0.getInt("addHigh",0));

        Log.d("Main onResume", "subtractionBasic:"+sharedata0.getInt("subtractionBasic",0));
        Log.d("Main onResume", "subtractionMedium:"+sharedata0.getInt("subtractionMedium",0));
        Log.d("Main onResume", "subtractionHigh:"+sharedata0.getInt("subtractionHigh",0));

        Log.d("Main onResume", "multiBasic:"+sharedata0.getInt("MultiBasic",0));
        Log.d("Main onResume", "multiMedium:"+sharedata0.getInt("multiMedium",0));
        Log.d("Main onResume", "multiHigh:"+sharedata0.getInt("multiHigh",0));

        Log.d("Main onResume", "date:"+sharedata0.getString("date", "0"));

        Log.d("Main onResume", "errImgNum:"+sharedata0.getInt("errImgNum",0));

        if(sharedata0.getBoolean("starHalf",false) == false)
            tvHalfStar.setText(" x 0");
        else
            tvHalfStar.setText(" x 1");

        tvStars.setText(" x "+sharedata0.getInt("stars",0));

        tvErrors.setText(" x "+sharedata0.getInt("errorCount",0)+"/3");

        tvOpDate.setText("上一次測試日期為：　"+sharedata0.getString("date", "0"));


    }

    public void gotoStore(View view) {

        threadflag = false;
        Intent it = new Intent(MainActivity.this,AwardStoreActivity.class);
        startActivity(it);
    }

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
           // Toast.makeText(getApplicationContext(), "PERMISSION_GRANTED0000", Toast.LENGTH_SHORT).show();
           // FileUtil.getInstance().storeBitmap(bitmap, path);
        }
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canRead) {
            //this.readFile();
        }
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //readFile();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       // Toast.makeText(getApplicationContext(), "PERMISSION_GRANTED11111", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    public static  void screenShot(Activity activity)
    {

        String path;
        Bitmap bitmap;

        SimpleDateFormat s = new SimpleDateFormat("MMddhhmmss");
        String format = s.format(new Date());

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        path = file.getPath();
        Log.d(">>>", "Pictures getPath path: " + path);

        File dir = new File(path+ "/MyFolder");

        if(dir.exists() && dir.isDirectory()) {
            Log.d(">>>", "MyFolder path0: " + dir.getPath());
            path =  dir.getPath();
        }
        else{
            dir.mkdirs();
            Log.d(">>>", "MyFolder path1: " + dir.getPath());
            path =  dir.getPath();
        }

        bitmap =ScreenshotUtil.getInstance().takeScreenshotForScreen(activity); // Take ScreenshotUtil
        path += "/"+format+"test.png";
        //Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
        Log.d(">>>", "Pictures Folder path: " + path);

        FileUtil.getInstance().storeBitmap(bitmap, path);
    }

    public void settingScore(View view) {

        threadflag = false;
// get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.text_inpu_password, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        Log.d("Main Dialog", "Password: "+editText.getText());
                        if(editText.getText().toString().equals("asdfghjk"))
                        {
                            Log.d("Main Dialog", "GOTO SETTING... ");




                            Intent it = new Intent(MainActivity.this,SettingActivity.class);
                            startActivity(it);

                        }else
                        {
                            dialog.cancel();
                            onResume();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                onResume();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    public static void playSoundFile(String wavPath)
    {
/*
        mediaPlayer1=new MediaPlayer();
        Uri myUri = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.q2);
       // String path1 = path+"raw/"+wavPath;


        //String path= "android.resource://com.example.musictraining/"+wavPath;
       // Uri path1 = Uri.parse(path);

            try {
                mediaPlayer1.setDataSource(context, myUri);
            } catch (IOException e) {
                e.printStackTrace();
            }



//        File file1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
//        String path1 = file1.getPath()+"/"+wavPath;
        Log.d("Main onCreate", "wav Path:"+myUri.getPath());
//        try {
//            mediaPlayer1.setDataSource(path1.getPath());
//            mediaPlayer1.prepare();
//            mediaPlayer1.setLooping(false);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        mediaPlayer1.start();
  */
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd(wavPath);
            mediaPlayer1 = new MediaPlayer();
            mediaPlayer1.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            mediaPlayer1.setLooping(false);//循环播放
            mediaPlayer1.prepare();


            mediaPlayer1.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
