package com.example.se1815_net_demoapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.se1815_net_demoapplication.bean.UserBean;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE_NAME = "Users";
    private static final String ID_FIELD = "id";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String CAMPUS_FIELD = "campus";
    private static final String ROLE_FIELD = "role";

    private static final String CREATE_USER_TABLE = "CREATE TABLE  " + USER_TABLE_NAME + " (" +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERNAME_FIELD + " TEXT NOT NULL UNIQUE, " +
            PASSWORD_FIELD + " TEXT NOT NULL, " +
            FIRST_NAME_FIELD + " TEXT NOT NULL, " +
            LAST_NAME_FIELD + " TEXT NOT NULL, " +
            EMAIL_FIELD + " TEXT NOT NULL, " +
            PHONE_FIELD + " TEXT, " +
            CAMPUS_FIELD + " TEXT NOT NULL, " +
            ROLE_FIELD + " TEXT NOT NULL" +
            ");";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertUser(UserBean user) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_FIELD, user.getUsername());
        values.put(PASSWORD_FIELD, user.getPassword());
        values.put(FIRST_NAME_FIELD, user.getFirstName());
        values.put(LAST_NAME_FIELD, user.getLastName());
        values.put(EMAIL_FIELD, user.getEmail());
        values.put(PHONE_FIELD, user.getPhone());
        values.put(CAMPUS_FIELD, user.getCampus());
        values.put(ROLE_FIELD, user.getRole());
        db.insert(USER_TABLE_NAME, null, values);
        // db.close();

    }

    public UserBean getUserByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_FIELD + " = ?";
        String[] args = { username };
        Cursor cursor = db.rawQuery(query, args);
        UserBean user = null;
        if (cursor.moveToFirst()) {
            user = new UserBean();
            int index = cursor.getColumnIndexOrThrow(ID_FIELD);
            user.setId(cursor.getInt(index));
            index = cursor.getColumnIndexOrThrow(USERNAME_FIELD);
            user.setUsername(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(PASSWORD_FIELD);
            user.setPassword(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(FIRST_NAME_FIELD);
            user.setFirstName(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(LAST_NAME_FIELD);
            user.setLastName(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(EMAIL_FIELD);
            user.setEmail(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(PHONE_FIELD);
            user.setPhone(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(CAMPUS_FIELD);
            user.setCampus(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(ROLE_FIELD);
            user.setRole(cursor.getString(index));
        }
        cursor.close();
        return user;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
    }

    public UserBean login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_FIELD + " = ? AND " + PASSWORD_FIELD
                + " = ?";
        String[] args = { username, password };
        Cursor cursor = db.rawQuery(query, args);
        UserBean user = null;
        if (cursor.moveToFirst()) {
            user = new UserBean();
            int index = cursor.getColumnIndexOrThrow(ID_FIELD);
            user.setId(cursor.getInt(index));
            index = cursor.getColumnIndexOrThrow(USERNAME_FIELD);
            user.setUsername(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(PASSWORD_FIELD);
            user.setPassword(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(FIRST_NAME_FIELD);
            user.setFirstName(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(LAST_NAME_FIELD);
            user.setLastName(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(EMAIL_FIELD);
            user.setEmail(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(PHONE_FIELD);
            user.setPhone(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(CAMPUS_FIELD);
            user.setCampus(cursor.getString(index));
            index = cursor.getColumnIndexOrThrow(ROLE_FIELD);
            user.setRole(cursor.getString(index));
        }
        cursor.close();
        return user;
    }

}
