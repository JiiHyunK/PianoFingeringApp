package com.example.jihyunkim.afinal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.TimerTask;
import java.util.Timer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.hungarian_dance);
        setContentView(R.layout.activity_main);


    }

    public void onMainClick(View view){
        Intent intent=new Intent(this, LevelListActivity.class); // 리스트뷰로 넘어가는 원래 코드
        //Intent intent=new Intent(this, PlaySongActivity.class); // 일단 플레이되는거 확인하려고 편의상 변경ㅠ
        startActivity(intent);

        /*  나중에 손가락 애니메이션 실험용
        ImageView iv=(ImageView)findViewById(R.id._finger1);
        //ImageView iv=(ImageView)findViewById(R.id.piano_main)
        //
        float x = iv.getX();
        iv.setX(x+62);
        //*/


    }

}




