package com.kriminal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kriminal on 13/01/2016.
 *  ~ *******************************************************************************
 ~   Copyright (c) 2016 kriminal666.
 ~
 ~   Licensed under the Apache License, Version 2.0 (the "License");
 ~   you may not use this file except in compliance with the License.
 ~   You may obtain a copy of the License at
 ~
 ~   http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~   Unless required by applicable law or agreed to in writing, software
 ~   distributed under the License is distributed on an "AS IS" BASIS,
 ~   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~   See the License for the specific language governing permissions and
 ~   limitations under the License.
 ~  *****************************************************************************
 **/
public class SQLiteHelper extends SQLiteOpenHelper {

    //Create table query
    String tableCreate = "CREATE TABLE tasks (id integer NOT NULL PRIMARY KEY," +
            "title text,description text,date text,time text,finish_date text, finish_time text)";
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
