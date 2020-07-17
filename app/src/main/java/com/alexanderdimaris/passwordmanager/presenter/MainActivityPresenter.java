package com.alexanderdimaris.passwordmanager.presenter;

import android.content.Context;
import com.alexanderdimaris.passwordmanager.model.DataBaseHelper;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import java.util.ArrayList;

public class MainActivityPresenter {
    private View view;
    private DataBaseHelper dataBaseHelper;

    public MainActivityPresenter(View view, Context context) {
        this.view = view;
        this.dataBaseHelper = new DataBaseHelper(context);
        view.updateDisplay(dataBaseHelper.getAll());
    }

    // TODO store the boolean for all these database methods and do a Toast if they fail?
    public void addToDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.addOne(passObj);
        if(success == false) {
            view.displayToast("Adding an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void removeFromDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.deleteOne(passObj);
        if(success == false) {
            view.displayToast("Removing an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void updateItemInDatabase(PassObj passObj) {
        boolean success = dataBaseHelper.updateOne(passObj);
        if(success == false) {
            view.displayToast("Updating an Entry Failed");
        } else {
            view.updateDisplay(dataBaseHelper.getAll());
        }
    }

    public void searchDatabase(String search) {
        view.updateDisplay(dataBaseHelper.search(search));
    }

    // todo A BUNCH OF TOAST STUFF
    public interface View {
        void updateDisplay(ArrayList<PassObj> list);
        void displayToast(String operation);
    }
}
