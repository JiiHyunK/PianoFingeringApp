package com.example.jihyunkim.afinal;

public class TestSong extends Song{

    public String Song_RP[][] = {{"_0sol","_0mi","_0mi","_0fa","_0re","_0re",
            "_0do","_0re","_0mi","_0fa","_0sol","_0sol","_0sol",
            "_0sol","_0mi","_0mi","_0mi","_0fa","_0re","_0re",
            "_0do","_0mi","_0sol","_0mi","_0do"},
            {},{},{},{},{}};

    public int Song_RT[] ={0,500,500,1000,500,500,1000,
            500,500,500,500,500,500,1000,
            500,500,500,500,500,500,1000,
            500,500,500,500};

    public String Song_LP[][] = {{"_1do","_1sol","_1sol","_1re","_1fa","_1fa",
            "_1do","_1sol","_1sol","_1sol","_1do","_1sol","_1sol",
            "_1do","_1sol","_1sol","_1sol","_1re","_1fa","_1fa",
            "_1do","_1mi","_1sol","_1mi","_1do"},{},{},{},{},{}};

    public int Song_LT[] = {0,500,500,1000,500,500,1000,
            500,500,500,500,500,500,1000,
            500,500,500,500,500,500,1000,
            500,500,500,500};


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
}