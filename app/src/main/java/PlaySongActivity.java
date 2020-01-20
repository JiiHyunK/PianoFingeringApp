package com.example.jihyunkim.afinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class PlaySongActivity extends Activity {
    public static final int PITCHES_COUNT = 60;
    public static final int MAX_FINGER = 5;
    public static final String[] PITCHES = {"do", "do_", "re", "re_", "mi", "fa", "fa_", "sol", "sol_", "la", "la_", "tea"};
    public static final String[] OCTAVE = {"_2", "_1", "_0", "$1", "$2"};
    public static  String nowPlay = "";
    public static String hand = "";

    public SoundPool SoundPools;
    public int SoundID[] = new int[PITCHES_COUNT]; // 60개의 음계를 담을 수 있는 배열
    public boolean mIsloaded = false; // SoundPool의 onLoadComplete이 됐을 때 활용
    public Map<String, Integer> soundMap = new HashMap<String, Integer>(); // 문자열 옥타브+계이름을 key로, SoundID[n]의 n을 value로 매칭시키는 함수

    public int Play_Song_RP[][]; // 오른손 음계의 이름의 value값을 담는 함수
    public int Play_Song_RT[]; // 오른손 음계의 박자를 담은 함수
    public int Play_Song_LP[][]; // 왼손 음계의 이름의 value값을 담는 함수
    public int Play_Song_LT[]; // 왼손 음계의 박자를 담은 함수
    public int Play_Song_FingerR[]; // 오른 손가락을 담은 함수
    public int Play_Song_FingerL[]; // 왼 손가락을 담은 함수
    public Song Play_Song; // Song 클래스. 다른 곡 클래스들이 이를 상속받아 다운캐스팅하여 활용하기 위한 부모 클래스
    public float volume;
    public int sharp_arr[] = new int[5 * OCTAVE.length];

    public SparseArray<ImageView> fingerArray = new SparseArray<ImageView>();
    public SparseArray<ImageView> fingerArray2 = new SparseArray<ImageView>();
    public SparseArray<ImageView> keyboardArray = new SparseArray<ImageView>();
    public ImageView handler_finger;
    public ImageView playing_finger;

    RightHandPlay rightHandPlay;
    LeftHandplay leftHandplay;
    private final MyHandler mHandler = new MyHandler(this);
    int piano_keyboard[] = new int[MAX_FINGER];

    ArrayAdapter adapter;
    ArrayAdapter barAdapter;

    public Button playbutton;
    public Button playLbutton;
    int num = 0;
    int startOfBar = 0;
    int endOfBar = 1;
    long speed = 1;
    long value = 0;
    String play_song;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.hungarian_dance);
        super.onCreate(savedInstanceState);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        playbutton = findViewById(R.id.hungarian);
        playLbutton = findViewById(R.id.left);

        fingerArrayMatch(); // 손가락에 해당하는 이미지 리소스들 리스트로 옮김
        keyboardArrayMatch(); // 피아노 건반에 해당하는 이미지 리소스들 리스트로 옮김
        select_Song();
        resetSoundPool();
        speedCreate();
        PartCreate();

    }


    public void resetSoundPool() {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("조금만 기다려주세요...");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        float actualVolume;
                        float maxVolume;

                        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

                        actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        volume = actualVolume / maxVolume;

                        SoundPools = new SoundPool(MAX_FINGER, AudioManager.STREAM_MUSIC, 0);
                        int pitch_num = 0;
                        int l = 0;

                        AssetFileDescriptor assetFileDescriptor = null;

                        for (int i = 0; i < OCTAVE.length; i++) {
                            for (int j = 0; j < PITCHES.length; j++) {
                                try {
                                    assetFileDescriptor = getAssets().openFd(OCTAVE[i] + PITCHES[j] + ".wav"); // asset 폴더 내의 사운드풀 로드

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                pitch_num = (i * 12) + j;

                                SoundID[pitch_num] = SoundPools.load(assetFileDescriptor, 1);

                                soundMap.put(OCTAVE[i] + PITCHES[j], pitch_num); // 옥타브+계이름과 pitch_num값을 매핑

                                if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10) { // #에 해당하는 계이름 모아서 배열에 넣기
                                    sharp_arr[l] = pitch_num;
                                    l++;
                                }
                            }
                        }
                        soundMap.put(" ", -1);

                        SoundPools.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // 한 음계가 로드가 완료되면 실행되는 리스너
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                num++; // 한 음계가 로드가 완료될 때 마다 num 개수 증가
                                if (num == 60) { // 60개의 음계가 전부 로드가 완료되면 플레이 버튼 enable=true로 바꿈
                                    playbutton.setEnabled(true);
                                    playLbutton.setEnabled(true);
                                    mIsloaded = true;
                                }
                            }
                        });

                        progress.dismiss();
                    }
                });
            }
        });
        progress.show();
        thread.start();
        progress.setCanceledOnTouchOutside(false);
    }

    // 핸들러를 통한 백그라운드 레이아웃 제어
    private static class MyHandler extends Handler {
        private final WeakReference<PlaySongActivity> mActivity;

        public MyHandler(PlaySongActivity activity) {
            mActivity = new WeakReference<PlaySongActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PlaySongActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {

        for (int k = 1; k <= fingerArray.size(); k++) {  // 모든 손가락을 INVISIBLE로 초기화
            fingerArray.get(k).setVisibility(View.INVISIBLE);
        }

        for (int k = 1; k <= fingerArray2.size(); k++) {
            fingerArray2.get(k).setVisibility(View.INVISIBLE);
        }

        for (int k = 0; k < keyboardArray.size(); k++) { // 피아노 건반 기본 이미지 초기화
            for (int j = 0; j < sharp_arr.length; j++) {
                if (k == sharp_arr[j]) { // #에 해당하는 검은건반
                    keyboardArray.get(k).setImageResource(R.drawable.ic_black);
                    break;
                }
                else if(k!=sharp_arr[j]){ // 흰건반
                    keyboardArray.get(k).setImageResource(R.drawable.ic_white);
                }
            }
        }

        // RightHandPlay에서 받아온 음계(piano_keyboard)에 해당하는 건반이 눌릴 때 이미지 설정
        for(int i=0;i<piano_keyboard.length;i++) {
            int pitch_key = piano_keyboard[i];
            if (pitch_key >= 0) {
                for(int j=0;j<sharp_arr.length;j++) {
                    if (pitch_key == sharp_arr[j]) {
                        keyboardArray.get(pitch_key).setImageResource(R.drawable.black_down);
                        break;
                    }
                    else keyboardArray.get(pitch_key).setImageResource(R.drawable.white_down);
                }
            }
        }
        handler_finger.setVisibility(View.VISIBLE); // RightHandPlay에서 받아온 음계에 해당하는 손가락 VISIBLE 표시

    }


    public class RightHandPlay extends Thread {

        @Override
        public void run() {

            // 음계가 로드가 완료되면 실행
            if (mIsloaded) {
                // 박자를 맞추기 위한 시간 초기화
                long saveTime = System.currentTimeMillis();
                long currTime = 0;

                // matchSong 함수에서 받아온 오른손 음계, 박자, 손가락번호
                int tmp_Song_RP[][] = Play_Song_RP;
                int tmp_Song_RT[] = Play_Song_RT;
                int finger_list[] = Play_Song_FingerR;

                int rate = 0; // 박자
                int pitch[] = new int[MAX_FINGER]; // 음계

                int[] startKey = {1920, 1535, 1150, 765, 380, 30};


                int curr_finger; // 현재 해당하는 손가락 번호
                float finger_location; // 각 손가락마다 위치 조정을 위한 변수
                float location_x = 0; // 손가락 이미지 위치의 x 좌표 초기화
                float location_y = 280; // 손가락 이미지 위치의 y 좌표 초기화
                float sharp_y; // #이 있을 때 마다 y 좌표의 변동을 위한 변수
                int piano_pitch; // 현재의 음계
                float piano_term = 239.3f; // 건반의 넓이에 따른 손가락 이동 거리

                // 연주 과정 시작
                for (int i = startOfBar; i < endOfBar; i++) {
                    for (int j = 0; j < tmp_Song_RP.length; j++) {
                        pitch[j] = tmp_Song_RP[j][i]; // 각 순서에 해당하는 음계(해당 순서에 중복으로 눌리는 건반까지) 받아옴
                    }

                    /*  중복으로 눌리는 건반 중 제일 낮은 위치에 있는 건반음계 찾아서 piano_pitch에 넣는다.
                        piano_pitch는 피아노 건반이 눌리기 위한 변수인데, 중복으로 눌리는 손 이미지가 하나의 이미지이기 때문에
                        가장 낮은 위치의 건반음계를 기준으로 이미지 위치를 불러오도록 설정함 */
                    piano_pitch = pitch[0];

                    for (int j = 1; j < tmp_Song_RP.length; j++) {
                        if (piano_pitch > pitch[j] && pitch[j] >= 0) piano_pitch = pitch[j];
                    }

                    rate = tmp_Song_RT[i]; // 박자 불러옴

                    while (currTime - saveTime < rate * speed) { // speed(배속)
                        currTime = System.currentTimeMillis(); // currTime이 현재 시간을 받아들이면서 박자에 해당하는 시간이 지나면 루프 빠져나옴
                    }
                    saveTime = System.currentTimeMillis();
                    currTime = 0;

                    if (finger_list.length != 0 && finger_list[i] != 0) {
                        curr_finger = finger_list[i]; // 현재 음계에 해당하는 손가락 번호
                        playing_finger = fingerArray.get(curr_finger); // 위의 손가락 번호를 통해(key) 해당 이미지 리소스(value)
                    }


                    if (playing_finger != null) {
                        // 각 손가락에 해당하는 위치 조정
                        if (playing_finger == fingerArray.get(1)) finger_location = -70;
                        else if (playing_finger == fingerArray.get(2)) finger_location = 150;
                        else if (playing_finger == fingerArray.get(3)) finger_location = 320;
                        else if (playing_finger == fingerArray.get(4)) finger_location = 480;
                        else if (playing_finger == fingerArray.get(5)) finger_location = 620;
                        else finger_location = 0;


                        for (int n = 0; n < OCTAVE.length; n++) { //옥타브의 길이만큼
                            if (piano_pitch >= n * 12 && piano_pitch < (n + 1) * 12) { // 손가락배열이 이 옥타브 안에 들어가면

                                // 음계에 해당하는 손가락 위치 조정
                                if (piano_pitch < ((n * 12) + 5))
                                    location_x = piano_pitch * (piano_term / 2) + (piano_term * n) - finger_location;
                                else
                                    location_x = (piano_pitch + 1) * (piano_term / 2) + (piano_term * n) - finger_location;


                                for (int j = 0; j < tmp_Song_RP.length; j++) {
                                    if(nowPlay == "hungarian_dance"){
                                        if ((i == 0||i == 8||i == 12||i == 19
                                                ||i==24||i == 28||i == 36||i == 44) == true) {
                                            HorizontalScrollView p = findViewById(R.id.scrollview2);
                                            p.smoothScrollTo((int) location_x - 800, 0);
                                            HorizontalScrollView s = findViewById(R.id.positionscroll);
                                            s.smoothScrollTo((int) (startKey[n]-finger_location), 0);
                                        }
                                    }

                                    if(nowPlay == "reminiscence"){
                                        if((i==0|| i==9|| i==17 || i==24|| i==34 || i==40 ) ==true){
                                            HorizontalScrollView p = findViewById(R.id.scrollview2);
                                            p.smoothScrollTo((int) location_x - 800, 0);
                                            HorizontalScrollView s = findViewById(R.id.positionscroll);
                                            s.smoothScrollTo((int) (startKey[n]-finger_location), 0);
                                        }
                                    }

                               }
                            }
                        }

                        playing_finger.setX(location_x);
                        playing_finger.setY(location_y);

                        for (int j = 0; j < sharp_arr.length; j++) {

                            if(playing_finger==fingerArray.get(6)) playing_finger.setY(playing_finger.getY()+7);
                            if(playing_finger==fingerArray.get(7)) playing_finger.setY(playing_finger.getY()-10);


                            if (piano_pitch == sharp_arr[j]) { // #에 해당하면 y좌표의 위치를 #건반만큼 올린다
                                sharp_y = playing_finger.getY();
                                playing_finger.setY(sharp_y - 230);
                            }
                        }

                        // handler에서 활용하기 위한 finger, piano_keyboard
                        handler_finger = playing_finger;
                        System.arraycopy(pitch,0,piano_keyboard,0,pitch.length);
                        mHandler.sendMessage(mHandler.obtainMessage()); // 핸들러 실행
                    }

                    // SoundPool 음계 소리 실행
                    for (int l = 0; l < MAX_FINGER; l++) {
                        if (pitch[l] >= 0 && pitch[l] < PITCHES_COUNT) {
                            SoundPools.play(SoundID[pitch[l]], volume, volume, 1, 0, 1f);
                        }
                    }
                }
            }
        }
    }


    public class LeftHandplay extends Thread {
        @Override
        public void run() {
            if (mIsloaded) { // onLoadComplete이 되어 mlsloaded가 true가 되면 실행

                long saveTime = System.currentTimeMillis();
                long currTime = 0;

                int tmp_Song_LP[][] = Play_Song_LP;
                int tmp_Song_LT[] = Play_Song_LT;
                int finger_list[] = Play_Song_FingerL;

                int rate = 0;
                int pitch[] = new int[MAX_FINGER];


                int[] startKey = {1920, 1535, 1150, 765, 380, 30};
                fingerArrayMatch2();
                keyboardArrayMatch();

                int curr_finger;
                float finger_location;
                float location_x = 0;
                float location_y = 270;
                float sharp_y;
                int piano_pitch;
                float piano_term = 239.4f;


                for (int i = startOfBar; i < endOfBar; i++) {
                    for (int j = 0; j < tmp_Song_LP.length; j++) {
                        pitch[j] = tmp_Song_LP[j][i];
                    }

                    piano_pitch = pitch[0];

                    for (int j = 1; j < tmp_Song_LP.length; j++) {
                        if (piano_pitch > pitch[j] && pitch[j] >= 0) piano_pitch = pitch[j];
                    }

                    rate = tmp_Song_LT[i];

                    while (currTime - saveTime < rate * speed) {
                        currTime = System.currentTimeMillis();

                    }
                    saveTime = System.currentTimeMillis();
                    currTime = 0;

                    if (finger_list.length != 0 && finger_list[i] != 0) {
                        curr_finger = finger_list[i];
                        playing_finger = fingerArray2.get(curr_finger);
                    }

                    if (playing_finger != null) {
                        if (playing_finger == fingerArray2.get(1)) finger_location = 645;
                        else if (playing_finger == fingerArray2.get(2)) finger_location = 410;
                        else if (playing_finger == fingerArray2.get(3)) finger_location = 230;
                        else if (playing_finger == fingerArray2.get(4)) finger_location = 80;
                        else if (playing_finger == fingerArray2.get(5)) finger_location = -70;

                        else finger_location = 0;

                        for (int n = 0; n < OCTAVE.length; n++) { //옥타브의 길이만큼
                            if (piano_pitch >= n * 12 && piano_pitch < (n + 1) * 12) { //    손가락배열이 이 옥타브 안에 들어가면

                                if (piano_pitch < ((n * 12) + 5))
                                    location_x = piano_pitch * (piano_term / 2) + (piano_term * n) - finger_location;
                                else
                                    location_x = (piano_pitch + 1) * (piano_term / 2) + (piano_term * n) - finger_location;

                                for (int j = 0; j < tmp_Song_LP.length; j++) {
                                    if ((i == 0 ) == true) {
                                        HorizontalScrollView p = findViewById(R.id.scrollview2);
                                        p.smoothScrollTo((int) location_x - 700, 0);
                                        HorizontalScrollView s = findViewById(R.id.positionscroll);
                                        s.smoothScrollTo((int) (startKey[n]), 0);
                                    }
                                }

                                if(nowPlay == "reminiscence"){
                                    if((i==0|| i==20 ||i==23||i==28||i==31||i==36||i==39) ==true){
                                        HorizontalScrollView p = findViewById(R.id.scrollview2);
                                        p.smoothScrollTo((int) location_x - 1500, 0);
                                        HorizontalScrollView s = findViewById(R.id.positionscroll);
                                        s.smoothScrollTo((int) (startKey[n]-finger_location), 0);
                                    }
                                }

                            }
                        }


                        playing_finger.setX(location_x);
                        playing_finger.setY(location_y);


                        for (int j = 0; j < sharp_arr.length; j++) {
                            if (piano_pitch == sharp_arr[j]) {
                                sharp_y = playing_finger.getY();
                                playing_finger.setY(sharp_y - 210);
                            }
                        }
                        handler_finger = playing_finger;
                        System.arraycopy(pitch,0,piano_keyboard,0,pitch.length);
                        mHandler.sendMessage(mHandler.obtainMessage());
                    }

                    for (int l = 0; l < MAX_FINGER; l++) {
                        if (pitch[l] >= 0 && pitch[l] < PITCHES_COUNT) {
                            SoundPools.play(SoundID[pitch[l]], volume, volume, 1, 0, 1f);
                        }
                    }
                }
            }

        }
    }


    public void onSongClick(View view){ // 오른손 연주 버튼
        matchSong();
        playSong();
    }

    public void onSongClick2(View view){ // 왼손 연주 버튼
        matchSong2();
        playSong2();
    }

    public void matchSong() { // 오른손 곡 매치

        String strPitches_R[][] = Play_Song.getSong_RP();
        Play_Song_RP = new int[strPitches_R.length][strPitches_R[0].length];
        Play_Song_RT = Play_Song.getSong_RT();
        Play_Song_FingerR = Play_Song.getSong_Finger_R();

        for (int i = 0; i < strPitches_R.length; i++) {

            if (strPitches_R[i].length == 0) {
                for (int l = 0; l < strPitches_R[0].length; l++)
                    Play_Song_RP[i][l] = -1;
            } else {
                for (int j = 0; j < strPitches_R[i].length; j++) {
                    String key = strPitches_R[i][j]; // 0부터 배열이 돌면서 옥타브+계이름 값을 key에 할당
                    Play_Song_RP[i][j] = soundMap.get(key); // soundMap에서 key로 불러온 음계의 SoundID[value]의 value값을 Play_Song_RP[i]에 저장
                }
            }
        }
    }

    public void matchSong2() { // 왼손 곡 매치

        String strPitches_L[][] = Play_Song.getSong_LP();
        Play_Song_LP = new int[strPitches_L.length][strPitches_L[0].length];
        Play_Song_LT = Play_Song.getSong_LT();
        Play_Song_FingerL = Play_Song.getSong_Finger_L();

        for (int i = 0; i < strPitches_L.length; i++) {
            if (strPitches_L[i].length == 0) {
                for (int l = 0; l < strPitches_L[0].length; l++)
                    Play_Song_LP[i][l] = -1;
            } else {
                for (int j = 0; j < strPitches_L[i].length; j++) {
                    String key = strPitches_L[i][j];
                    Play_Song_LP[i][j] = soundMap.get(key);
                }
            }
        }
    }


    public void playSong() { // 오른손 연주
        rightHandPlay = new RightHandPlay();
        hand ="r";
        rightHandPlay.start();
    }

    public void playSong2() { // 왼손 연주
        leftHandplay= new LeftHandplay();
        hand = "l";
        leftHandplay.start();
    }


    public void select_Song() { //곡 선택
        Intent intent = getIntent();
        play_song = intent.getStringExtra("selected_song");
        switch (play_song) {
            case "hungarian_dance":
                Play_Song = new HungarianDance();
                nowPlay = "hungarian_dance";
                break;

            case "reminiscence":
                Play_Song = new Reminiscence();
                nowPlay = "reminiscence";
                break;
        }
    }

    public void speedCreate() {
        final Spinner speedSpinner = (Spinner) findViewById(R.id.spinner1);

        //스피너 이벤트 설정
        adapter = ArrayAdapter.createFromResource(this, R.array.speed, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speedSpinner.setAdapter(adapter);

        //스피너 이벤트 발생
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long a){
                switch (position) {
                    case 0:
                        speed = 1;
                        break;
                    case 1:
                        speed = 2;
                        break;
                    case 2:
                        speed = 3;
                        break;
                    case 3:
                        speed = 4;
                        break;
                    case 4:
                        speed = 5;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    protected void PartCreate() {

        final Spinner partSpinner = (Spinner) findViewById(R.id.spinner2);

        //스피너 이벤트 설정
        barAdapter = ArrayAdapter.createFromResource(this, R.array.bar, android.R.layout.simple_spinner_dropdown_item);
        barAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partSpinner.setAdapter(barAdapter);

        //스피너 이벤트 발생
        partSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long value) {
                switch (position) {
                    case 0:
                        startOfBar = 0;                   //1. 전체 재생인 경우
                        endOfBar = Play_Song.getSong_RP()[0].length;
                        break;
                    case 1:
                        startOfBar = 0;                   //2. 구간1 재생인 경우
                        endOfBar = 8;
                        break;

                    case 2:
                        startOfBar = 8;                   //3. 구간2 재생인 경우
                        endOfBar = 19;
                        break;

                    case 3:
                        startOfBar = 19;                   //4. 구간3 재생인 경우
                        endOfBar = 28;
                        break;

                    case 4:
                        startOfBar = 28;                   //5. 구간4 재생인 경우
                        endOfBar = 36;
                        break;

                    case 5:
                        startOfBar = 36;                   //6. 구간5 재생인 경우
                        endOfBar = 44;
                        break;

                    case 6:
                        startOfBar = 44;                   //7. 구간6 재생인 경우
                        endOfBar = 51;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void keyboardArrayMatch() {
        ImageView do2 = findViewById(R.id._white1);
        ImageView do_2 = findViewById(R.id._black1);
        ImageView re2 = findViewById(R.id._white2);
        ImageView re_2 = findViewById(R.id._black2);
        ImageView mi2 = findViewById(R.id._white3);
        ImageView fa2 = findViewById(R.id._white4);
        ImageView fa_2 = findViewById(R.id._black3);
        ImageView sol2 = findViewById(R.id._white5);
        ImageView sol_2 = findViewById(R.id._black4);
        ImageView la2 = findViewById(R.id._white6);
        ImageView la_2 = findViewById(R.id._black5);
        ImageView tea2 = findViewById(R.id._white7);
        ImageView do1 = findViewById(R.id._white8);
        ImageView do_1 = findViewById(R.id._black6);
        ImageView re1 = findViewById(R.id._white9);
        ImageView re_1 = findViewById(R.id._black7);
        ImageView mi1 = findViewById(R.id._white10);
        ImageView fa1 = findViewById(R.id._white11);
        ImageView fa_1 = findViewById(R.id._black8);
        ImageView sol1 = findViewById(R.id._white12);
        ImageView sol_1 = findViewById(R.id._black9);
        ImageView la1 = findViewById(R.id._white13);
        ImageView la_1 = findViewById(R.id._black10);
        ImageView tea1 = findViewById(R.id._white14);
        ImageView do0 = findViewById(R.id._white15);
        ImageView do_0 = findViewById(R.id._black11);
        ImageView re0 = findViewById(R.id._white16);
        ImageView re_0 = findViewById(R.id._black12);
        ImageView mi0 = findViewById(R.id._white17);
        ImageView fa0 = findViewById(R.id._white18);
        ImageView fa_0 = findViewById(R.id._black13);
        ImageView sol0 = findViewById(R.id._white19);
        ImageView sol_0 = findViewById(R.id._black14);
        ImageView la0 = findViewById(R.id._white20);
        ImageView la_0 = findViewById(R.id._black15);
        ImageView tea0 = findViewById(R.id._white21);
        ImageView do11 = findViewById(R.id._white22);
        ImageView do_11 = findViewById(R.id._black16);
        ImageView re11 = findViewById(R.id._white23);
        ImageView re_11 = findViewById(R.id._black17);
        ImageView mi11 = findViewById(R.id._white24);
        ImageView fa11 = findViewById(R.id._white25);
        ImageView fa_11 = findViewById(R.id._black18);
        ImageView sol11 = findViewById(R.id._white26);
        ImageView sol_11 = findViewById(R.id._black19);
        ImageView la11 = findViewById(R.id._white27);
        ImageView la_11 = findViewById(R.id._black20);
        ImageView tea11 = findViewById(R.id._white28);
        ImageView do22 = findViewById(R.id._white29);
        ImageView do_22 = findViewById(R.id._black21);
        ImageView re22 = findViewById(R.id._white30);
        ImageView re_22 = findViewById(R.id._black22);
        ImageView mi22 = findViewById(R.id._white31);
        ImageView fa22 = findViewById(R.id._white32);
        ImageView fa_22 = findViewById(R.id._black23);
        ImageView sol22 = findViewById(R.id._white33);
        ImageView sol_22 = findViewById(R.id._black24);
        ImageView la22 = findViewById(R.id._white34);
        ImageView la_22 = findViewById(R.id._black25);
        ImageView tea22 = findViewById(R.id._white35);

        keyboardArray.put(0, do2);
        keyboardArray.put(1, do_2);
        keyboardArray.put(2, re2);
        keyboardArray.put(3, re_2);
        keyboardArray.put(4, mi2);
        keyboardArray.put(5, fa2);
        keyboardArray.put(6, fa_2);
        keyboardArray.put(7, sol2);
        keyboardArray.put(8, sol_2);
        keyboardArray.put(9, la2);
        keyboardArray.put(10, la_2);
        keyboardArray.put(11, tea2);
        keyboardArray.put(12, do1);
        keyboardArray.put(13, do_1);
        keyboardArray.put(14, re1);
        keyboardArray.put(15, re_1);
        keyboardArray.put(16, mi1);
        keyboardArray.put(17, fa1);
        keyboardArray.put(18, fa_1);
        keyboardArray.put(19, sol1);
        keyboardArray.put(20, sol_1);
        keyboardArray.put(21, la1);
        keyboardArray.put(22, la_1);
        keyboardArray.put(23, tea1);
        keyboardArray.put(24, do0);
        keyboardArray.put(25, do_0);
        keyboardArray.put(26, re0);
        keyboardArray.put(27, re_0);
        keyboardArray.put(28, mi0);
        keyboardArray.put(29, fa0);
        keyboardArray.put(30, fa_0);
        keyboardArray.put(31, sol0);
        keyboardArray.put(32, sol_0);
        keyboardArray.put(33, la0);
        keyboardArray.put(34, la_0);
        keyboardArray.put(35, tea0);
        keyboardArray.put(36, do11);
        keyboardArray.put(37, do_11);
        keyboardArray.put(38, re11);
        keyboardArray.put(39, re_11);
        keyboardArray.put(40, mi11);
        keyboardArray.put(41, fa11);
        keyboardArray.put(42, fa_11);
        keyboardArray.put(43, sol11);
        keyboardArray.put(44, sol_11);
        keyboardArray.put(45, la11);
        keyboardArray.put(46, la_11);
        keyboardArray.put(47, tea11);
        keyboardArray.put(48, do22);
        keyboardArray.put(49, do_22);
        keyboardArray.put(50, re22);
        keyboardArray.put(51, re_22);
        keyboardArray.put(52, mi22);
        keyboardArray.put(53, fa22);
        keyboardArray.put(54, fa_22);
        keyboardArray.put(55, sol22);
        keyboardArray.put(56, sol_22);
        keyboardArray.put(57, la22);
        keyboardArray.put(58, la_22);
        keyboardArray.put(59, tea22);
    }

    public void fingerArrayMatch() {
        ImageView f1 = (ImageView) findViewById(R.id._finger1);
        ImageView f2 = (ImageView) findViewById(R.id._finger2);
        ImageView f3 = (ImageView) findViewById(R.id._finger3);
        ImageView f4 = (ImageView) findViewById(R.id._finger4);
        ImageView f5 = (ImageView) findViewById(R.id._finger5);
        ImageView f103 = (ImageView) findViewById(R.id._finger103);
        ImageView f140 = (ImageView) findViewById(R.id._finger140);

        fingerArray.put(1, f1);
        fingerArray.put(2, f2);
        fingerArray.put(3, f3);
        fingerArray.put(4, f4);
        fingerArray.put(5, f5);
        fingerArray.put(6, f103);
        fingerArray.put(7, f140);
    }

    public void fingerArrayMatch2() {
        ImageView f1 = (ImageView) findViewById(R.id._l1);
        ImageView f2 = (ImageView) findViewById(R.id._l2);
        ImageView f3 = (ImageView) findViewById(R.id._l3);
        ImageView f4 = (ImageView) findViewById(R.id._l4);
        ImageView f5 = (ImageView) findViewById(R.id._l5);



        fingerArray2.put(1, f1);
        fingerArray2.put(2, f2);
        fingerArray2.put(3, f3);
        fingerArray2.put(4, f4);
        fingerArray2.put(5, f5);
    }
}