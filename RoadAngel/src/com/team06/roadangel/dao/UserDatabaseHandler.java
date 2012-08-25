package com.team06.roadangel.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 11:16 AM
 */
public class UserDatabaseHandler extends SQLiteOpenHelper {
    public static final String TABLE_USER = "user";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_TOKEN = "token";
//    public static final String COLUMN_USER_NAME = "name";
//    public static final String COLUMN_USER_PW = "password";
//    public static final String COLUMN_REMEMBER = "rememberMe";

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_USER + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_USER_TOKEN + " text not null);";
//            COLUMN_USER_NAME + " text not null, " +
//            COLUMN_USER_PW + " text, " +
//            COLUMN_REMEMBER + " integer);";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserDatabaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to " +
                        newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
