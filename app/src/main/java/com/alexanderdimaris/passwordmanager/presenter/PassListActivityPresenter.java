package com.alexanderdimaris.passwordmanager.presenter;

import android.content.Context;
import com.alexanderdimaris.passwordmanager.model.DataBaseHelper;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import java.util.ArrayList;

public class PassListActivityPresenter {
    private View view;
    private DataBaseHelper dataBaseHelper;

    public PassListActivityPresenter(View view) {
        this.view = view;
        this.dataBaseHelper = new DataBaseHelper((Context)view);
        view.updateDisplay(dataBaseHelper.getAll());
    }

    public void addToDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.addOne(passObj);
        if(!success) {
            view.displayToast("Adding an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void removeFromDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.deleteOne(passObj);
        if(!success) {
            view.displayToast("Removing an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void updateItemInDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.updateOne(passObj);
        if(!success) {
            view.displayToast("Updating an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void searchDatabase(String search) {
        view.updateDisplay(dataBaseHelper.search(search));
    }


    public interface View {
        void updateDisplay(ArrayList<PassObj> list);
        void displayToast(String operation);
    }
}
