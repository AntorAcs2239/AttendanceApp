package com.example.yessir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int Version =1;
    public DataBaseHelper(@Nullable Context context) {
        super(context,"Database.db",null,Version);
    }
    //Class list table
    public static final String CLASS_TABLE_NAME="CLASS_TABLE";
    public static final String CLASS_NAME="CLASS_NAME";
    public static final String  SUBJECT_NAME_KEY="SUBJECT_NAME";
    public static final String CLASS_ID="CLASS_ID";

    public static final String CREATE_CLASS_TABLE="CREATE TABLE "+CLASS_TABLE_NAME+"("+CLASS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            CLASS_NAME+" TEXT NOT NULL,"+ SUBJECT_NAME_KEY+" TEXT NOT NULL,"+ " UNIQUE("+CLASS_NAME+","+SUBJECT_NAME_KEY+")"+");";
    public static final String DROP_CLASS_TABLE="DROP TABLE IF EXISTS "+CLASS_TABLE_NAME;
    public static final String SELECT_CLASS_TABLE="SELECT * FROM "+CLASS_TABLE_NAME;
    //Class list table
    //Student list table
    public static final String STUDENT_NAME_TABLE="STUDENT_TABLE";
    public static final String STUDENT_ID="STUDENT_ID";
    public static final String STUDET_NAME_KEY="STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY="STUDENT_ROLL";
    public static final String STATUS_DATE_KEY="STATUS_DATE";
    public static final String STATUS_KEY="STATUS";
    public static final String CREATE_STUDENT_TABLE=
            "CREATE TABLE "+STUDENT_NAME_TABLE+"("+STUDENT_ID+"  INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, "+
                    CLASS_ID+"  INTEGER NOT NULL, "+
                    STUDET_NAME_KEY+"  TEXT NOT NULL, "+
                    STUDENT_ROLL_KEY+"  INTEGER NOT NULL ," +
                    STATUS_DATE_KEY+ " DATE NOT NULL, "+
                    STATUS_KEY+ " TEXT,  "+
                    "FOREIGN KEY"+"("+CLASS_ID+")"+"REFERENCES "+CLASS_TABLE_NAME+"("+CLASS_ID+")"+
                    ");";
    public static final String DROP_STUDENT_TABLE="DROP TABLE IF EXISTS "+STUDENT_NAME_TABLE;
    public static final String SELECT_STUDENT_TABLE="SELECT * FROM "+STUDENT_NAME_TABLE;
    //Student list table

    //Status table
    public static final String STATUS_TABLE_NAME="STATUS_TABLE";
    public static final String STATUS_ID_KEY="STATUS_ID";
    public static final String CREATE_STATUS_TABLE=
            "CREATE TABLE "+STATUS_TABLE_NAME+
                    "("+ STATUS_ID_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
                    STUDENT_ID+" INTEGER NOT NULL, "+
                    CLASS_ID+" INTEGER, "+
                    STATUS_DATE_KEY+ " DATE NOT NULL, "+
                    STATUS_KEY+ " TEXT NOT NULL,  "+
                    "FOREIGN KEY"+"("+CLASS_ID+")"+"REFERENCES "+CLASS_TABLE_NAME+"("+CLASS_ID+"),"+
                    "FOREIGN KEY"+"("+STUDENT_ID+")"+"REFERENCES "+STUDENT_NAME_TABLE+"("+STUDENT_ID+")"+
                    ");";
    public static final String DROP_STATUS_TABLE="DROP TABLE IF EXISTS "+STATUS_TABLE_NAME;
    public static final String SELECT_STATUS_TABLE="SELECT * FROM "+STATUS_TABLE_NAME;
    //Status table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try
        {
            db.execSQL(DROP_CLASS_TABLE);
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    long ADD_CLASS(String classname, String subjectname) {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CLASS_NAME,classname);
        contentValues.put(SUBJECT_NAME_KEY,subjectname);
        return database.insert(CLASS_TABLE_NAME,null,contentValues);
    }
    long Add_Student(long classid,String studentname,int roll,String date)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CLASS_ID,classid);
        contentValues.put(STUDET_NAME_KEY,studentname);
        contentValues.put(STUDENT_ROLL_KEY,roll);
        contentValues.put(STATUS_DATE_KEY,date);
        contentValues.put(STATUS_KEY," ");
        return database.insert(STUDENT_NAME_TABLE,null,contentValues);
    }
    long add_status(long sid,String date,String status,long cid)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(STUDENT_ID,sid);
        contentValues.put(CLASS_ID,cid);
        contentValues.put(STATUS_DATE_KEY,date);
        contentValues.put(STATUS_KEY,status);
        return database.insert(STATUS_TABLE_NAME,null,contentValues);
    }
    Cursor Get_Class_table()
    {
        SQLiteDatabase database=this.getReadableDatabase();

        return database.rawQuery(SELECT_CLASS_TABLE, null);
    }
     Cursor Get_Student_table(long cid,String date)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Log.i("date",date+" "+cid);
        String wherecluse=STATUS_DATE_KEY+"='"+date+"' AND "+CLASS_ID +"="+cid;
        Cursor cursor= database.query(STUDENT_NAME_TABLE,null,wherecluse,null,null,null,null);
        //Log.i("student","Student "+cursor.getCount());
        return cursor;
    }
    Cursor Get_Student_table_byroll(long cid,int roll)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String wherecluse=STUDENT_ROLL_KEY+"="+roll+" AND "+CLASS_ID +"="+cid;
        Cursor cursor= database.query(STUDENT_NAME_TABLE,null,wherecluse,null,null,null,null);
        //Log.i("student","Student "+cursor.getCount());
        return cursor;
    }
    Cursor get_by_date(String date)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String wherecluse=STATUS_DATE_KEY+"='"+date+"'";
        Cursor cursor= database.query(STUDENT_NAME_TABLE,null,wherecluse,null,null,null,null);
        return cursor;
    }
    String get_status_table(long sid,String date)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String status=null;
        String wherecluse=STATUS_DATE_KEY+"='"+date+"' AND "+STUDENT_ID +"="+sid;
        Log.i("check", String.valueOf(sid)+" "+date);
        Cursor cursor=database.query(STATUS_TABLE_NAME,null,wherecluse,null,null,null,null);
        if (cursor.moveToNext())status=cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;
    }
    int deleteclass(long id)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        database.delete(STUDENT_NAME_TABLE,CLASS_ID+"=?",new String[]{String.valueOf(id)});
        return database.delete(CLASS_TABLE_NAME,CLASS_ID+"=?",new String[]{String.valueOf(id)});
    }
    int deleteclassstudent(long cid)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        return database.delete(CLASS_TABLE_NAME,CLASS_ID+"=?",new String[]{String.valueOf(cid)});
    }
    int deleteStudent(long id)
    {
        SQLiteDatabase database=this.getReadableDatabase();
        return database.delete(STUDENT_NAME_TABLE,STUDENT_ID+"=?",new String[]{String.valueOf(id)});
    }
    long Update_Class(String classname,String subjectname,long cid) {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CLASS_NAME,classname);
        contentValues.put(SUBJECT_NAME_KEY,subjectname);
        return database.update(CLASS_TABLE_NAME,contentValues,CLASS_ID+"=?",new String[]{String.valueOf(cid)});
    }
    long update_student(String studentname,int roll,long sid)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(STUDET_NAME_KEY,studentname);
        contentValues.put(STUDENT_ROLL_KEY,roll);
        return database.update(STUDENT_NAME_TABLE,contentValues,STUDENT_ID+"=?",new String[]{String.valueOf(sid)});
    }
    long update_status(long sid,String date,String status)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(STATUS_KEY,status);
        String wherecluse=STATUS_DATE_KEY+" = '"+date+"' AND "+STUDENT_ID +" = "+sid;
        return database.update(STUDENT_NAME_TABLE,contentValues,wherecluse,null);
    }
}
