package com.example.jihyunkim.afinal;

public class SchoolSong extends Song {

    public String Song_RP[][] = {{"_0sol","_0sol","_0la","_0la","_0sol","_0sol","_0mi",
            "_0sol","_0sol","_0mi","_0mi","_0re",
            "_0sol","_0sol","_0la","_0la","_0sol","_0sol","_0mi",
            "_0sol","_0mi","_0re","_0mi","_0do"},{},{},{},{},{}};
/*
    public String Song_RP[] = {"_0sol","_0sol","_0la","_0la","_0sol"};

    public long Song_RT[] ={100,100,100,100,100};
//*/


    public int Song_RT[] ={0,500,500,500,500,500,500,1000,
            500,500,500,500,2000,
            500,500,500,500,500,500,1000,
            500,500,500,500};

    public String Song_LP[][] = {{"_1do","_1sol","_1sol","_1do","_1sol","_1sol",
            "_1do","_1sol","_1sol","_1re","_1fa","_1fa",
            "_1do","_1sol","_1sol","_1do","_1sol","_1sol",
            "_1do","_1mi","_1sol","_1mi","_1do"},
        {" ","_1mi","_1mi"," ","_1mi","_1mi",
            " ","_1mi","_1mi"," ","_1re","_1re",
            " ","_1mi","_1mi"," ","_1mi","_1mi",
            " "," "," "," "," "},{},{},{},{}};


    public int Song_LT[] = {0,500,500,1000,500,500,1000,
            500,500,1000,500,500,1000,
            500,500,1000,500,500,1000,
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