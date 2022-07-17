package com.example.register;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.register.bean.User;

import java.util.ArrayList;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "userSQLite.dp";
    private static final String TABLE_NAME_USER = "user";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_USER + " (id Integer primary key autoincrement,name text,password text,gender text,fName text,birthTime text,city text,school text,edit text);";

    public MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertData(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("password",user.getPassword());
        values.put("gender",user.getGender());
        return db.insert(TABLE_NAME_USER,null,values);
    }

    public ArrayList<User> getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<User>();
        Cursor cursor = db.query(TABLE_NAME_USER, null, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
//            int id = cursor.getInt((cursor.getColumnIndex("id")));
            String name = cursor.getString((cursor.getColumnIndex("name")));
            String password = cursor.getString((cursor.getColumnIndex("password")));
            String gender = cursor.getString((cursor.getColumnIndex("gender")));
            String fName = cursor.getString((cursor.getColumnIndex("fName")));
            String birthTime = cursor.getString((cursor.getColumnIndex("birthTime")));
            String city = cursor.getString((cursor.getColumnIndex("city")));
            String school = cursor.getString((cursor.getColumnIndex("school")));
            String edit = cursor.getString((cursor.getColumnIndex("edit")));
            userList.add(new User(name,password,gender,fName,birthTime,city,school,edit));
        }
        cursor.close();
        return userList;
    }

    public long updateData(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("birthTime",user.getBirthTime());
        values.put("school",user.getSchool());
        values.put("city",user.getCity());
        values.put("fName",user.getFakeName());
        values.put("edit",user.getEdit());
        return db.update(TABLE_NAME_USER,values,"name like ?",new String[] {user.getName()});
    }

    public ArrayList<User> queryFromDbByName(String name){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<User>();
        Cursor cursor = db.query(TABLE_NAME_USER, null, "name like ?", new String[]{name}, null, null, null);
        if (cursor != null){
            while(cursor.moveToNext()){
                int id = cursor.getInt((cursor.getColumnIndex("id")));
                String name1 = cursor.getString((cursor.getColumnIndex("name")));
                String password = cursor.getString((cursor.getColumnIndex("password")));
                String gender = cursor.getString((cursor.getColumnIndex("gender")));
                String fName = cursor.getString((cursor.getColumnIndex("fName")));
                String birthTime = cursor.getString((cursor.getColumnIndex("birthTime")));
                String city = cursor.getString((cursor.getColumnIndex("city")));
                String school = cursor.getString((cursor.getColumnIndex("school")));
                String edit = cursor.getString((cursor.getColumnIndex("edit")));

                userList.add(new User(id,name1,password,gender,fName,birthTime,city,school,edit));
            }
            cursor.close();
        }
        return userList;
    }
}
