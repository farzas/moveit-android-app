package com.sample.farza.moveit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by farzaali on 5/9/16.
 */

//To create and upgrade a database in your Android application you create a subclass of the SQLiteOpenHelper class.
public class DatabaseHelper extends SQLiteOpenHelper {

    //Java representation of the database.
    //SQLiteDatabase is the base class for working with a SQLite database in Android
    // and provides methods to open, query, update and close the database.
    SQLiteDatabase sqLiteDatabase;


    public static final String DATABASE_NAME = "moveAppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String MOVE_TABLE_NAME = "MOVE";
    public static final String MOVE_COL_0 = "ID";
    public static final String MOVE_COL_1 = "NAME";
    public static final String MOVE_COL_2 = "STATUS";

    private static final String CREATE_MOVE_TABLE = "create table "
            + MOVE_TABLE_NAME + "(" + MOVE_COL_0
            + " integer primary key autoincrement, " + MOVE_COL_1
            + " text , " + MOVE_COL_2
            + " text) ;";

    public static final String BOX_TABLE_NAME = "BOX";
    public static final String BOX_COL_0 = "ID";
    public static final String BOX_COL_1 = "MOVE_ID";
    public static final String BOX_COL_2 = "NAME";

    private static final String CREATE_BOX_TABLE = "create table "
            + BOX_TABLE_NAME + "(" + BOX_COL_0
            + " integer primary key autoincrement, " + BOX_COL_1
            + " integer , " + BOX_COL_2
            + " text) ;";

    public static final String ITEM_TABLE_NAME = "ITEM";
    public static final String ITEM_COL_0 = "ID";
    public static final String ITEM_COL_1 = "NAME";
    public static final String ITEM_COL_2 = "CATEGORY_NAME";
    public static final String ITEM_COL_3 = "ITEM_IMAGE_PATH";
    public static final String ITEM_COL_4 = "MOVE_ID";


    private static final String CREATE_ITEM_TABLE = "create table "
            + ITEM_TABLE_NAME + "(" + ITEM_COL_0
            + " integer primary key autoincrement, " + ITEM_COL_1
            + " text , " + ITEM_COL_2
            + " text, " + ITEM_COL_3
            + " text, " + ITEM_COL_4
            + " integer) ;";

    //Amy - Item box table

    public static final String ITEM_BOX_TABLE_NAME = "ITEMBOX";
    public static final String ITEM_BOX_COL_0 = "ID";
    public static final String ITEM_BOX_COL_1 = "NAME";
    public static final String ITEM_BOX_COL_2 = "CATEGORY_NAME";
    public static final String ITEM_BOX_COL_3 = "ITEM_IMAGE_PATH";
    public static final String ITEM_BOX_COL_4 = "MOVE_ID";
    public static final String ITEM_BOX_COL_5 = "BOX_ID";


    private static final String CREATE_ITEM_BOX_TABLE = "create table "
            + ITEM_BOX_TABLE_NAME + "(" + ITEM_BOX_COL_0
            + " integer primary key autoincrement, " + ITEM_BOX_COL_1
            + " text , " + ITEM_BOX_COL_2
            + " text, " + ITEM_BOX_COL_3
            + " text, " + ITEM_BOX_COL_4
            + " integer, " + ITEM_BOX_COL_5
            + " integer default 0) ;";



    public static final String CATEGORY_TABLE_NAME = "CATEGORY";
    public static final String CATEGORY_COL_0 = "ID";
    public static final String CATEGORY_COL_1 = "NAME";

    private static final String CREATE_CATEGORY_TABLE = "create table "
            + CATEGORY_TABLE_NAME + "(" + CATEGORY_COL_0
            + " integer primary key autoincrement, " + CATEGORY_COL_1
            + " text)  ;";

    public static final String BOX_ITEM_TABLE_NAME= "BOX_ITEM";
    public static final String BOX_ITEM_COL_0 = "BOX_ID";
    public static final String BOX_ITEM_COL_1 = "ITEM_ID";

    private static final String CREATE_BOX_ITEM_TABLE = "create table "
            + BOX_ITEM_TABLE_NAME + "(" + BOX_ITEM_COL_0
            + " integer , " + BOX_ITEM_COL_1
            + " integer)  ;";


    //database will be created whenever this constructor is called
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //is called by the framework, if the database is accessed but not yet created
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_MOVE_TABLE);
        db.execSQL(CREATE_BOX_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_BOX_ITEM_TABLE);
        db.execSQL(CREATE_ITEM_BOX_TABLE);


    }

    //called, if the database version is increased in your application code.
    //This method allows you to update an existing database schema or to drop the existing database and recreate it via the onCreate() method.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IS EXISTS "+MOVE_TABLE_NAME);
        db.execSQL("DROP TABLE IS EXISTS "+BOX_TABLE_NAME);
        db.execSQL("DROP TABLE IS EXISTS "+ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IS EXISTS "+CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IS EXISTS "+BOX_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ITEM_BOX_TABLE_NAME);
        onCreate(db);


    }



}

