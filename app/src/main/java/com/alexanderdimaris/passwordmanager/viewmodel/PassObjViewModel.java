package com.alexanderdimaris.passwordmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.alexanderdimaris.passwordmanager.data.PassObjRepository;

import java.util.List;

public class PassObjViewModel extends AndroidViewModel {

    private PassObjRepository repository;
    private LiveData<List<PassObj>> allPasswords;

    public PassObjViewModel(Application application) {
        super(application);
        repository = new PassObjRepository(application);
        allPasswords = repository.getAllPasswords();
    }

    public LiveData<List<PassObj>> getAllPasswords() {
        allPasswords = repository.getAllPasswords();
        return allPasswords;
    }

    public void insert(PassObj passObj) {
        repository.insert(passObj);
    }

    public void delete(PassObj passObj) {
        repository.delete(passObj);
    }

    public void update(PassObj passObj) {
        repository.update(passObj);
    }

}
