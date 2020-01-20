package com.example.jihyunkim.afinal;

import java.io.File;

public class HungarianDance extends Song {

    int note4=467;
    int note4_half=(int) (note4*(1.5));
    int note8=(int)(note4*(0.5));
    int note8_half=(int)(note4*(0.75));
    int note16=(int)(note4*(0.25));
    int note2=note4*2;

//*/

    String Song_RP[][] = {{"_0re","_0sol","_0la_","_0sol","_0fa_","_0sol","_0la","_0sol",
            "_0re_","_0fa","_0sol","_0re",
            "_0do","_1la_","_1la_","_1la","_1la","_0re","_1sol",
            "_0re","_0sol","_0la_","$1re","_0la_","_0la","_0la_","$1do","_0la_",
            "$1re_","$1fa","$1sol","$1re_","$1re","$1re_","$1fa","$1re",
            "$1do","$1re","$1re_","$1do","_0la_","$1do","$1re","_0la_",
            "$1do","_0la_","_0la_","_0la","_0la","$1re","_0sol"},
            {" "," "," "," "," "," "," "," ",
                    " "," "," "," ",
                    " "," "," "," "," "," "," ",
                    " "," "," "," "," ","_0re_"," "," ","_0re",
                    " "," "," "," "," "," "," "," ",
                    " "," "," "," "," "," "," "," ",
                    " "," "," "," "," "," "," "},{},{},{}};
    int Song_RT[] = {0,750,250,750,250,
            750,125,125,1000,
            750,125,125,1000,
            125,125,125,125,
            250,250,1000, 750,
            125,125,750,250,
            750,125,125,1000,
            125,125,125,125,
            125,125,125,125,
            125,125,125,125,
            125,125,125,125,
            125,125,125,125,
            250,250};


/*
    int Song_RT[] = {0,500,500,500,500,500,500,500,500,
            500,500,500,500,
            500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500};

*/
    //*/

    String Song_LP[][]={{"_2sol","_1re","_2re","_1re","_2sol","_1re","_2re","_1re",
            "_2sol","_1do","_2re","_1do","_2sol","_1re","_2re","_1re",
            "_2sol","_1do","_2re","_1do","_2sol","_1re","_2re","_1re",
            "_2sol","_1re","_2re","_1re","_2sol","_1re","_2re","_1re",
            "_2sol","_1re","_2re","_1re","_2sol","_1re","_2re","_1re",
            "_2sol","_1do","_2re","_1do","_2sol","_1re","_2re","_1re",
            "_1re_","_1re","_1do","_2la_","_1do","_2sol","_2sol"},{},{},{},{}};
/*
        String Song_LP[][] = {{"_2do","_2do_","_2re","_2re_","_2mi","_2fa","_2fa_","_2sol",
            "_2sol_","_2la","_2la_","_2tea",
            "_1do","_1do_","_1re","_1re_","_1mi","_1fa","_1fa_",
            "_1sol","_1sol_","_1la","_1la_","_1tea",
            "_0do","_0do_","_0re","_0re_", "_0mi","_0fa","_0fa_","_0sol","_0sol_","_0la","_0la_","_0tea",
            "$1do","$1do_","$1re","$1re_","$1mi","$1fa","$1fa_","$1sol",
            "$1sol_","$1la","$1la_","$1tea","$2do","$2do_","$2re","$2re_","$2mi","$2fa","$2fa_","$2sol","$2sol_","$2la","$2la_","$2tea"},
            {},{},{},{}};
*/

    int Song_LT[]={0,250,250,250,250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            250, 250, 250, 250,
            500, 500, 500, 500,
            1000, 500, 250 };

    /*
    int Song_LT[] = {0,500,500,500,500,500,500,500,500,
            500,500,500,500,
            500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500,500,
            500,500,500,500,500,500,500};
    */

    //*
    int Song_Finger_R[]= {1, 3, 5, 3, 2, 3, 4, 3,
            2, 3, 4, 1, 3, 2, 3, 2, 2, 5, 1,
            1, 2, 3, 5, 3, 6, 3, 4, 7,
            2, 3, 4, 2, 1, 3, 4, 2, 1, 3, 4, 2, 1, 3, 4, 2,
            3, 2, 3, 2, 2, 5, 1};
/*

    int Song_Finger_R[]= {1, 2, 3, 4, 5, 1, 2, 3,
        4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4,
        5, 2, 3, 5, 3, 6, 3, 4, 7,
        2, 3, 4, 2, 1, 3, 4, 2, 1, 3, 4, 2, 1, 3, 4, 2,
        3, 2, 3, 2, 2, 5, 1};
    //*/
//*
    int Song_Finger_L[]={2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,5,1,2,1,2,3,2,5,5};


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

//
//try {
//        File file = new File("C:\\")
//    }
}