package com.example.lmasi.hangsho;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConect extends SQLiteOpenHelper {

    public DBConect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("create table if not exists resources(attr text, numb integer, PRIMARY KEY(attr));");
//        db.execSQL("create table if not exists product(name text, timer integer, image integer, PRIMARY KEY(name));");
//        db.execSQL("create table if not exists idp(id text, product_name text , entry_num integer, PRIMARY KEY(id, product_name));");
//        db.execSQL("create table if not exists account(id text , phone text, email text, addr text, PRIMARY KEY(id));");
//        db.execSQL("create table if not exists point(x integer , y integer, item_index integer, main integer);");
//        db.execSQL("create table if not exists character(id text, c_index integer, PRIMARY KEY(id));");
//        db.execSQL("create table if not exists c_table(id text, c_index integer, buy integer, PRIMARY KEY(id, c_index));");

        db.execSQL("create table if not exists PersonalData(ID text, PartnerID text, UserName text, PartnerName text, Birthday text, PartnerBirthday text, Gender int, profile text, myPhoneNum text, partnerPhoneNum text, PRIMARY KEY(ID));");
        db.execSQL("create table if not exists Login_info(ID text, PassWd text, isLogged integer, PRIMARY KEY(ID));");
        db.execSQL("create table if not exists HistoryCard(idx integer, good integer,  title text, dateDay text, courseNum integer, timer text, PRIMARY KEY(idx));");
        db.execSQL("create table if not exists History(idx integer, place text, man integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /////flow
    //Activity_Start    Activity_Main   Activity_Local  Activity_chooseStore    Activity_ShopList   Activity_ChooseCoupons  Activity_TimeLine   Activity_Result
}
