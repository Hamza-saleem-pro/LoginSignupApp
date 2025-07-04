package com.example.login_activity;

import static org.xmlpull.v1.XmlPullParser.TEXT;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class My_Db_helper extends SQLiteOpenHelper {

    private static final String DB_NAME = "USER_DB";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "USER";

    private static final String U_name = "User_Name";

    private static final String U_email = "User_Email";

    private static final String U_password = "User_Password";

    private static final String U_id = "User_ID";

    public My_Db_helper(@Nullable Context context)
    {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + U_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + U_name + " TEXT NOT NULL, " + U_email +" Text NOT NULL, " + U_password +" Text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean add_user(String name, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_name, name);
        values.put(U_email, email);
        values.put(U_password, password);

      long result =  db.insert(TABLE_NAME, null, values);
      if(result == -1)
      {
          return false;
      }
      else  return true;
    }

    public boolean check_user(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + U_name + " = ? AND " + U_password + " = ?",
                new String[]{name, password}
        );

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }



}

