package com.alexanderdimaris.passwordmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.alexanderdimaris.passwordmanager.model.PassObjRepository;

import java.util.List;

public class PassObjViewModel extends AndroidViewModel {

    private PassObjRepository mRepository;
    private LiveData<List<PassObj>> mAllPasswords;

    public PassObjViewModel (Application application) {
        super(application);
        mRepository = new PassObjRepository(application);
        mAllPasswords = mRepository.getAllPasswords();
    }

    public LiveData<List<PassObj>> getAllPasswords() {
        mAllPasswords = mRepository.getAllPasswords();
        return mAllPasswords;
    }

    public LiveData<List<PassObj>> search(String string) {
        mAllPasswords = mRepository.search(string);
        return mAllPasswords;
    }

    public void insert(PassObj passObj) {
        mRepository.insert(passObj);
    }

    public void delete(PassObj passObj) {
        mRepository.delete(passObj);
    }

    public void update(PassObj passObj) {
        mRepository.update(passObj);
    }

}
