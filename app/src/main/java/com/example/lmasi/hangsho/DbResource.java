package com.example.lmasi.hangsho;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Vector;

public class DbResource {

    enum GENDER {MAN, WOMAN}

    public static String IPADDR = "49.142.114.16";
    public static int port = 300;

    public static DBConect conn;
    public static SQLiteDatabase db;

    public static boolean Loggin = false;

    public static String ID = "";
    public static String PartnerID = "";
    public static String PassWd ="";

    public static String UserName = "";
    public static String PartnerName = "";

    public static String Birthday = "";
    public static String PartnerBirthday = "";
    public static GENDER Gender = null;

    public static Bitmap profile = null;

    public static String myPhoneNum = "";
    public static String partnerPhoneNum = "";

    public static String Location = "";

    public static ArrayList<Coupons> couponses = new ArrayList<>();

    public static boolean iscalled = false;
    public static boolean pause = false;
    public static boolean restart = false;
    public static boolean stop = false;
    public static boolean LSI = false;
    public static boolean COUPON = false;
}
