package com.example.jihyunkim.afinal;

public class Song {


    String Song_RP[][];

    int Song_RT[];

    String Song_LP[][];

    int Song_LT[];

    int Song_Finger_R[];

    int Song_Finger_L[];


    public String[][] getSong_RP() {
        return Song_RP;
    }

    public int[] getSong_RT() {
        return Song_RT;
    }

    public String[][] getSong_LP() {
        return Song_LP;
    }

    public int[] getSong_LT() {
        return Song_LT;
    }

    public int[] getSong_Finger_R(){return Song_Finger_R;}

    public int[] getSong_Finger_L(){return Song_Finger_L;}

}