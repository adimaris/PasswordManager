package com.alexanderdimaris.passwordmanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String PASSWORD_TABLE = "PASSWORD_TABLE";
    private static final String COLUMN_TITLE_NAME = "COLUMN_TITLE_NAME";
    private static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    private static final String COLUMN_PASSWORD_TEXT = "COLUMN_PASSWORD_TEXT";
    private static final String COLUMN_COMMENTS = "COLUMN_COMMENTS";
    private static final String COLUMN_ID = "ID";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "password.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PASSWORD_TABLE
                                    + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                                    + COLUMN_TITLE_NAME + " TEXT, "
                                    + COLUMN_USERNAME + " TEXT, "
                                    + COLUMN_PASSWORD_TEXT + " TEXT, "
                                    + COLUMN_COMMENTS + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // intentionally left blank
    }

    public boolean addOne(PassObj passwordObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE_NAME, passwordObject.getTitle());
        cv.put(COLUMN_USERNAME, passwordObject.getUsername());
        cv.put(COLUMN_PASSWORD_TEXT, passwordObject.getPassword());
        cv.put(COLUMN_COMMENTS, passwordObject.getComments());

        long insert = db.insert(PASSWORD_TABLE, null, cv);
        db.close(); // TODO does this fix the delete issues I'm having?
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOne(PassObj passwordObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + PASSWORD_TABLE + " WHERE " + COLUMN_ID + " = " + passwordObject.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        if(success) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateOne(PassObj passObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE_NAME, passObj.getTitle());
        cv.put(COLUMN_USERNAME, passObj.getUsername());
        cv.put(COLUMN_PASSWORD_TEXT, passObj.getPassword());
        cv.put(COLUMN_COMMENTS, passObj.getComments());
        long update = db.update(PASSWORD_TABLE, cv, "ID=" +passObj.getId(), null);

        if(update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<PassObj> getAll() {
        ArrayList<PassObj> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PASSWORD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int passwordId = cursor.getInt(0);
                String passwordTitle = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String comments = cursor.getString(4);

                PassObj newPassword = new PassObj(passwordId, passwordTitle, username, password, comments);
                returnList.add(newPassword);
            } while(cursor.moveToNext());
        } else {
            // intentionally left blank
        }
        cursor.close();
        db.close();
        return returnList;
    }
}