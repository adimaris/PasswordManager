package com.alexanderdimaris.passwordmanager.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PassObjRepository {

    private PassObjDao mPassObjDao;
    private LiveData<List<PassObj>> mAllPasswords;

    public PassObjRepository(Application application) {
        PassObjRoomDatabase db = PassObjRoomDatabase.getDatabase(application);
        mPassObjDao = db.passObjDoa();
        mAllPasswords = mPassObjDao.getAll();
    }

    public LiveData<List<PassObj>> getAllPasswords() {
        mAllPasswords = mPassObjDao.getAll();
        return mAllPasswords;
    }

    public LiveData<List<PassObj>> search(String string) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAllPasswords = mPassObjDao.search(string);
        });
        return mAllPasswords;
    }

    public void insert(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPassObjDao.insert(passObj);
        });
    }

    public void update(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPassObjDao.updatePass(passObj);
        });
    }

    public void delete(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPassObjDao.delete(passObj);
        });
    }
}
