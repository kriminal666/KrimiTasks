package com.kriminal.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.kriminal.api.Utils;
import com.kriminal.main.R;

import java.util.ArrayList;

/**
 * Created by Kriminal on 13/01/2016.
 */
public class TasksDAO {

    private SQLiteHelper sqlHelper;
    private SQLiteDatabase sqliteDatabase;
    private static final String DATABASE = "DBTasks";
    private static final int VERSION = 1;
    private Context ctx;




    //Constructor
    public TasksDAO(Context ctx){

        this.ctx = ctx;

        //get database
        if (sqlHelper == null){
            try{
                sqlHelper = new SQLiteHelper(ctx,this.DATABASE,null,this.VERSION);

            }catch(Exception e){

                Log.d(Utils.TAG,e.getLocalizedMessage());

            }

        }

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
        String selectAllTodo ="select * from tasks where finish_date = null and finish_time = null order by date, time asc";
        String selectOne = "select * from tasks where id ="+id;
        String selectFinished ="select * from tasks where finish_time is not null and finish_time is not null order by finish_date,finish_time";
        String selectAll = "select * from tasks";
        ArrayList<Task> result = new ArrayList<Task>();
        Task task;
        Cursor cursor=null;

        switch (id){

            //Select all tasks
            case 0:

                if (!execSelect(selectAllTodo, result, cursor)) return null;
                break;

            //Select all finished tasks
            case -1:

                if (! execSelect(selectFinished,result,cursor)) return null;
                break;
            case -2:
                if (! execSelect(selectAll,result,cursor));
            //Select one task
            default:
                if (!execSelect(selectOne,result,cursor,id))return null;

            }

            return result;


        }

    //Execute Select query
    private boolean execSelect(String select, ArrayList<Task> result, Cursor cursor, int ...taskId) {
        Task task;

        int id = taskId[0];

        try {
            cursor = sqliteDatabase.rawQuery(select, null);
        }catch(Exception e){
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Check if we have rows
        if (cursor.getCount()==0){

            Toast.makeText(ctx, R.string.noTasks,Toast.LENGTH_SHORT).show();
            sqliteDatabase.close();

            return false;
        }
        //Move to first row
        cursor.moveToFirst();
        task = new Task();
        //get data from cursor to array list
        do
        {

          setTask(task,cursor);
          //Add task to array

            result.add(task);

        } while(cursor.moveToNext());
        sqliteDatabase.close();
        return true;
    }


    /**
     * Set Task object with database value
     * @param task
     * @param cursor
     * @return
     */

    private Task setTask (Task task, Cursor cursor){

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
                "'"+time+"',NULL,NULL)";

        try{
            //open to write database
            sqliteDatabase = sqlHelper.getWritableDatabase();
            //Execute the query
            sqliteDatabase.execSQL(insert);

        }catch(Exception e){
            Toast.makeText(this.ctx, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this.ctx,R.string.inserted, Toast.LENGTH_LONG).show();
        sqliteDatabase.close();
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
        finish_date = task.getFinished_date();
        finish_time = task.getFinished_time();
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
            Toast.makeText(this.ctx,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.d(Utils.TAG, update + "\n" + e.getMessage());
            return false;
        }
        //If we don't have exception show message
        Toast.makeText(this.ctx,R.string.updated,Toast.LENGTH_LONG).show();
        return true;



    }

    /**
     * Mark task as finished
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
            Toast.makeText(this.ctx,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.d(Utils.TAG, update + "\n" + e.getMessage());
            return false;
        }
        //If we don't have exception show message
        Toast.makeText(this.ctx,R.string.finished,Toast.LENGTH_LONG).show();
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
            Toast.makeText(this.ctx,e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this.ctx,R.string.deleted,Toast.LENGTH_LONG).show();

        return true;


    }


    }


