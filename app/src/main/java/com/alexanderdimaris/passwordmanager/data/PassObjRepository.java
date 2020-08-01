package com.alexanderdimaris.passwordmanager.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderdimaris.passwordmanager.model.PassObj;

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

    public void insert(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> mPassObjDao.insert(passObj));
    }

    public void update(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> mPassObjDao.updatePass(passObj));
    }

    public void delete(final PassObj passObj) {
        PassObjRoomDatabase.databaseWriteExecutor.execute(() -> mPassObjDao.delete(passObj));
    }
}
