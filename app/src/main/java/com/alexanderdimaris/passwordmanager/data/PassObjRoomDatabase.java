package com.alexanderdimaris.passwordmanager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alexanderdimaris.passwordmanager.model.PassObj;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PassObj.class}, version = 1)
public abstract class PassObjRoomDatabase extends RoomDatabase {

    public abstract PassObjDao passObjDoa();

    private static final String DATABASE_NAME = "passObj_database";
    private static PassObjRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PassObjRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PassObjRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PassObjRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
