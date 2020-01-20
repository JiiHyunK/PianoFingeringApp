package com.example.jihyunkim.afinal;

public class Reminiscence extends Song {


    String Song_RP[][]={{"$2do","$1la_","$2re_","$2do","$1la_","$1sol_",
            "$1sol","$1sol_","$1sol","$1re_","$1re_","$1sol_","$1la_",
            "$2do","$1la_","$2re_","$2do","$2sol","$2sol_",
            "$2sol","$2re_","$2do","$1la_","$1sol_","$2do","$1la_","$2re_","$2do","$1la_","$1sol_",
            "$1sol","$1sol_","$1la_","$1re_","$1do","$1re_","$1fa","$1fa","$2do","$1sol_","$1sol",
            "$1fa"},
            {},{},{},{}};

    int Song_RT[]={0,700,325,487,650,325,325,450,487,450,700,163,163,163,650,325,487,650,325,325, //650저부분
            487,487,325,650,650,650,325,487,650,325,325,487,325,487,650,325,325,
            487,487,1625,1300,1300,1300,};


    String Song_LP[][]={{"$1do_","_0la_","_0la_","_0sol_",
            "_0sol_","_0sol","_0fa","$1do","$1re_","_0la_","_0fa_",
            "_0sol_","_0la_","_0la_","_0la_",
            "_0do_","_0sol_","$1do_",
            "_0fa","_0sol",
            "_2fa","_1do","_1fa","_1sol","_1sol_","_0re_","_1sol","_1sol_",
            "_2fa","_1do","_1fa","_1sol","_1sol_","_0re_","_1sol","_1sol_",
            "_2fa","_1do","_1fa","_1sol","_1sol_","_0re_","_1sol","_1sol_",
            "_2fa","_1do","_1fa","_1sol","_1sol_","_0re_","_1sol","_1sol_",
            "_2fa"},
            {},{},{},{}};
//            "_1do", "_1fa","_1sol","_1sol_","_0re_","_1sol","_1sol_",

    int Song_LT[]={0,1300,1300,1300,1300,
            1300,1300, 325,325,650,650,650,
            1300,1300, 1300,1300,
            325,325,1950,
            1300,1300,
        200,200,200,200,200,200,200,200,
        200,200,200,200,200,200,200,200,
        200,200,200,200,200,200,200,200,
            200,200,200,200,200,200,200,200,
        1300};

    int Song_Finger_R[]= {3,2,5,3,2,1,2,1,2,1,1,3,4,5,4,5,3,4,5,4,3,2,1,2,3,2,5,3,2,1,2,1,2,1,1,3,4,4,5,4,3,2};

    int Song_Finger_L[]={2,3,3,4,
            3,4,5,2,1,3,4,
            2,1,1,1,
            5,2,1,
            3,2,
            5,2,1,4,3,1,3,4,
            5,2,1,4,3,1,3,4,
            5,2,1,4,3,1,3,4,
            5,2,1,4,3,1,3,4,
            5,2,1};

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