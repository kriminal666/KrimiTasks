package com.kriminal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kriminal on 13/01/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    //Create table query
    String tableCreate = "CREATE TABLE tasks (id integer NOT NULL PRIMARY KEY," +
            "title text,description text,date text,time text,finished_date text, finished_time text)";
    //Constructor
    public SQLiteHelper(Context ctx,String database,SQLiteDatabase.CursorFactory cursor, int version){
        super(ctx,database,cursor,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execute create table
        db.execSQL(tableCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //We just delete the old table and create new
        db.execSQL("DROP TABLE IF EXITS tasks");
        //Create new one
        db.execSQL(tableCreate);

    }
}
