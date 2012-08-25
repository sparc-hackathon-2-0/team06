package com.team06.roadangel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.team06.roadangel.model.User;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 11:15 AM
 */
public class UserDao {
    // Database fields
    private SQLiteDatabase database = null;
    private UserDatabaseHandler dbHelper= null;
    private String[] allColumns = {UserDatabaseHandler.COLUMN_ID,
            UserDatabaseHandler.COLUMN_USER_NAME,
            UserDatabaseHandler.COLUMN_USER_PW,
            UserDatabaseHandler.COLUMN_REMEMBER};

    public UserDao(Context context) {
        dbHelper = new UserDatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean createUser(String userName, String password, boolean rememberMe) {
        boolean success = false;
        int remember = 0;

        if(rememberMe) {
            remember = 1;
        }

        try {
            open();

            ContentValues values = new ContentValues();

            // insert values
            values.put(UserDatabaseHandler.COLUMN_USER_NAME, userName);
            values.put(UserDatabaseHandler.COLUMN_USER_PW, password);
            values.put(UserDatabaseHandler.COLUMN_REMEMBER, remember);


            long insertId = database.insert(UserDatabaseHandler.TABLE_USER, null, values);

            if(insertId > 0) {
                success = true;
            }
        }
        catch (SQLException ex) {
            Log.e("RoadAngel: ", Log.getStackTraceString(ex));
        }
        finally {
            close();
        }

        return success;
    }

    public boolean updateUser(int userId, String userName, String password, boolean rememberMe) {
        boolean success = false;
        int remember = 0;

        if(rememberMe) {
            remember = 1;
        }

        try {
            open();

            ContentValues values = new ContentValues();

            // insert values
            values.put(UserDatabaseHandler.COLUMN_ID, userId);
            values.put(UserDatabaseHandler.COLUMN_USER_NAME, userName);
            values.put(UserDatabaseHandler.COLUMN_USER_PW, password);
            values.put(UserDatabaseHandler.COLUMN_REMEMBER, remember);

            long insertId = database.update(UserDatabaseHandler.TABLE_USER, values,
                    UserDatabaseHandler.COLUMN_ID + "='" + userId + "'", null);

            if(insertId > 0) {
                success = true;
            }
        }
        catch (SQLException ex) {
            Log.e("RoadAngel: ", Log.getStackTraceString(ex));
        }
        finally {
            close();
        }

        return success;
    }

    public User getUser() {
        User user = null;
        // To show how to query
        try {
            open();

            String selectQuery = "SELECT * FROM " + UserDatabaseHandler.TABLE_USER;
            Cursor cursor = database.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            if(cursor.getCount() > 0) {
                user = cursorToUser(cursor);
            }
        }
        catch (SQLException ex) {
            Log.e("RoadAngel: ", Log.getStackTraceString(ex));
        }
        finally {
            close();
        }

        return user;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();

        if(cursor != null) {
            user.setUserId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setUserPw(cursor.getString(2));
            user.setRemember(cursor.getInt(3));
        }

        return user;
    }

    private boolean userExists(int userId) {
        boolean success = false;

        try {
            Cursor cursor = database.query(UserDatabaseHandler.TABLE_USER,
                    allColumns, UserDatabaseHandler.COLUMN_ID + " = '" + userId + "'",
                    null, null, null, null);

            if(cursor.getCount() > 0) {
                success = true;
            }

            cursor.close();
        }
        catch(SQLException ex) {
            Log.e("RoadAngel: ", Log.getStackTraceString(ex));
        }

        return success;
    }
}
