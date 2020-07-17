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
    private SimpleCrypto simpleCrypto = new SimpleCrypto();

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

    public boolean addOne(PassObj passObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        try {
            passObj.setPassword(SimpleCrypto.bytesToHex(simpleCrypto.encrypt(passObj.getPassword())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cv.put(COLUMN_TITLE_NAME, passObj.getTitle());
        cv.put(COLUMN_USERNAME, passObj.getUsername());
        cv.put(COLUMN_PASSWORD_TEXT, passObj.getPassword());
        cv.put(COLUMN_COMMENTS, passObj.getComments());

        long insert = db.insert(PASSWORD_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean deleteOne(PassObj passwordObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete =  db.delete(PASSWORD_TABLE, "ID = " +passwordObject.getId(), null);
        db.close();

        return delete != -1;
    }

    public boolean updateOne(PassObj passObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        try {
            passObj.setPassword(SimpleCrypto.bytesToHex(simpleCrypto.encrypt(passObj.getPassword())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cv.put(COLUMN_TITLE_NAME, passObj.getTitle());
        cv.put(COLUMN_USERNAME, passObj.getUsername());
        cv.put(COLUMN_PASSWORD_TEXT, passObj.getPassword());
        cv.put(COLUMN_COMMENTS, passObj.getComments());
        long update = db.update(PASSWORD_TABLE, cv, "ID=" +passObj.getId(), null);
        db.close();

        return update != -1;
    }

    public ArrayList<PassObj> getAll() {
        ArrayList<PassObj> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PASSWORD_TABLE + " ORDER BY " + COLUMN_TITLE_NAME + " COLLATE NOCASE ASC";
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
                try {
                    newPassword.setPassword(new String(simpleCrypto.decrypt(newPassword.getPassword())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                returnList.add(newPassword);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<PassObj> search(String search) {
        ArrayList<PassObj> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PASSWORD_TABLE + " WHERE " + COLUMN_TITLE_NAME + " LIKE '%" + search + "%'" + " ORDER BY " + COLUMN_TITLE_NAME + " COLLATE NOCASE ASC";
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
                try {
                    newPassword.setPassword(new String(simpleCrypto.decrypt(newPassword.getPassword())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                returnList.add(newPassword);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}