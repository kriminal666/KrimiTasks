package com.kriminal.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.kriminal.helpers.Utils;
import com.kriminal.main_activity.R;
import com.kriminal.sweet_alert.SweetAlert;

import java.util.ArrayList;

/**
 * Created by Kriminal on 13/01/2016.
 *  * *******************************************************************************
 * Copyright (c) 2016 kriminal666.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
public class TasksDAO {

    private SQLiteHelper sqlHelper;
    private SQLiteDatabase sqliteDatabase;
    private static final String DATABASE = "DBTasks";
    private static final int VERSION = 1;


    private Context ctx;

    private String[] columns = {"id","title","description","date","time","finish_date","finish_time"};

    private ArrayList<Task> result;


    //Constructor
    public TasksDAO(Context ctx){

        this.ctx = ctx;

        //get database
        if (sqlHelper == null){
            try{
                sqlHelper = new SQLiteHelper(ctx,this.DATABASE,null,this.VERSION);


            }catch(Exception e){

                SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();

            }

        }

        result = new ArrayList<Task>();


    }

    ////ACCESS METHODS////

    /**
     * Select one or all tasks
     * @param id
     * id = 0 all to do tasks
     * id = -1 finished tasks
     * id = -2 all tasks
     * @return
     * ArrayList<Task> or null if empty
     */
    public ArrayList<Task> select(int id){

        //Open to read
        sqliteDatabase = sqlHelper.getReadableDatabase();
        String selectAllTodo ="select * from tasks where finish_date ='' and finish_time='' order by date, time desc";
        String selectOne = "select * from tasks where id ="+id;
        String selectFinished ="select * from tasks where finish_date!='' and finish_time!='' order by finish_date,finish_time desc";
        String selectAll = "select * from tasks";


        switch (id){

            //Select all tasks
            case Utils.SELECT_ALL_TODO :

                if (!execSelect(selectAllTodo)) return null;
                break;

            //Select all finished tasks
            case Utils.SELECT_ALL_FINISHED:

                if (! execSelect(selectFinished)) return null;
                break;
            //Select to do + finished
            case Utils.SELECT_ALL_TASKS:
                if (! execSelect(selectAll))return null;
                break;
            //Select one task
            default:
                int [] arrayId = {id};
                if (!execSelect(selectOne,arrayId))return null;

            }

            return result;


        }

    //Execute Select query
    private boolean execSelect(String select, int ...taskId) {
        Cursor cursor = null;
        String[] values ={null,null};

        if(result == null){
            result = new ArrayList<>();
        }
        if (!result.isEmpty()){

            result.clear();
        }


        if(taskId.length != 0) {
            int id = taskId[0];
        }

        //sqliteDatabase.query("tasks", columns,"finish_date=null and finish_time=null",null,null,null,null);
        try {
            Log.d(Utils.TAG,select);
            cursor = sqliteDatabase.rawQuery(select, null);

        }catch(Exception e){
            SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();
            return false;
        }

        //Check if we have rows
        if (cursor.getCount()==0){

            sqliteDatabase.close();

            return false;
        }
        //Move to first row
        cursor.moveToFirst();
        //get data from cursor to array list
        do
        {

          //Add task to array

            result.add(setTask(cursor));


        } while(cursor.moveToNext());
        sqliteDatabase.close();
        return true;
    }


    /**
     * Set Task object with database value
     * @param cursor
     * @return
     */

    private Task setTask (Cursor cursor){

        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTitle(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setDate(cursor.getString(3));
        task.setTime(cursor.getString(4));
        task.setFinished_date(cursor.getString(5));
        task.setFinished_time(cursor.getString(6));

        return task;
    }

    /**
     * Insert new task
     * @param task Task object
     * @return boolean
     */
    public boolean insertTask(Task task){
        String title;
        String description;
        String date;
        String time;

        if (task ==null) return false;

        title = task.getTitle();
        description = task.getDescription();
        date = task.getDate();
        time = task.getTime();

        String insert = "INSERT INTO tasks VALUES(NULL, '"+title+"','"+description+"','"+date+"'," +
                "'"+time+"', '', '')";

        try{
            //open to write database
            sqliteDatabase = sqlHelper.getWritableDatabase();
            //Execute the query
            sqliteDatabase.execSQL(insert);
            //Close database
            sqliteDatabase.close();
        }catch(Exception e){
            SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();
            return false;
        }


        return true;
    }

    /**
     * Update task
     * @param task Task object
     * @return boolean
     */
    public boolean updateTask(Task task){
        int id;
        String title;
        String description;
        String date;
        String time;
        String finish_date;
        String finish_time;
        if (task == null) return false;
        //Set values
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        date = task.getDate();
        time = task.getTime();
        finish_date = "";
        finish_time = "";
        //Update string
        String update = "UPDATE tasks SET title='"+title+"',description='"+description+"',date = '"+date+"'," +
                "time = '"+time+"',finish_date = '"+finish_date+"',finish_time = '"+finish_time+"' WHERE id="+id;

        try {
            //Open the database to write
            sqliteDatabase = sqlHelper.getWritableDatabase();
            //Execute query
            sqliteDatabase.execSQL(update);
            //close database
            sqliteDatabase.close();
        }catch(Exception e){
            SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();
            return false;
        }
        //If we don't have exception
        return true;

    }

    /**
     * Mark task as finished or undo
     * @param id
     * @param finish_date
     * @param finish_time
     * @return boolean
     */
    public boolean taskFinished(int id, String finish_date, String finish_time){

        String update = "UPDATE tasks SET finish_date = '"+finish_date+"',finish_time = '"+finish_time+"' WHERE id="+id;
        try {
            //Open the database to write
            sqliteDatabase = sqlHelper.getWritableDatabase();
            //Execute query
            sqliteDatabase.execSQL(update);
            //close database
            sqliteDatabase.close();
        }catch(Exception e){
            SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();
            return false;
        }
        //If we don't have exception show message
        return true;

    }


    public boolean deleteTask (int id){

        String delete = "DELETE FROM tasks WHERE id = "+id;
        try{
            //open the database to write(delete)
            sqliteDatabase = sqlHelper.getWritableDatabase();
            //Execute query
            sqliteDatabase.execSQL(delete);
            //Close the database
            sqliteDatabase.close();

        }catch(Exception e){
            SweetAlert.errorMessage(ctx,ctx.getResources().getString(R.string.error_title),e.getLocalizedMessage()).show();
            return false;
        }

        return true;


    }


    }


