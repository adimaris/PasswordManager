package com.alexanderdimaris.passwordmanager.data;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alexanderdimaris.passwordmanager.model.PassObj;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private PassObjDao passObjDao;
    private PassObjRoomDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PassObjRoomDatabase.class).build();
        passObjDao = db.passObjDoa();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        PassObj passObj = new PassObj( "Amazon", "mark123", "abc123", "Amazon Prime trial ends 8/20");
        passObjDao.insert(passObj);

        List<PassObj> byTitle = passObjDao.searchByTitle("Amazon");
        assertThat(byTitle.get(0).getTitle(), equalTo(passObj.getTitle()));
    }

    @Test
    public void writeUserDeleteUserAndReadInList() throws Exception {
        PassObj passObj = new PassObj( "Amazon", "mark123", "abc123", "Amazon Prime trial ends 8/20");
        passObjDao.insert(passObj);
        passObjDao.delete(passObj);

        List<PassObj> byTitle = passObjDao.searchByTitle("Amazon");
        assert(byTitle.size() == 0);
    }

    @Test
    public void writeUserUpdateUserAndReadInList() throws Exception {
        PassObj passObj = new PassObj( "Amazon", "mark123", "abc123", "Amazon Prime trial ends 8/20");
        passObjDao.insert(passObj);
        passObj.setTitle("Facebook");
        passObjDao.updatePass(passObj);

        List<PassObj> byTitle = passObjDao.searchByTitle("Amazon");
        assert(byTitle.size() == 0);

        byTitle = passObjDao.searchByTitle("Facebook");
        assert(byTitle.size() == 1);
    }
}

