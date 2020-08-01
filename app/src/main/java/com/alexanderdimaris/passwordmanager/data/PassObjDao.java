package com.alexanderdimaris.passwordmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alexanderdimaris.passwordmanager.model.PassObj;

import java.util.List;

@Dao
public interface PassObjDao {
    @Query("SELECT * FROM passObj_db ORDER BY LOWER(title)")
    LiveData<List<PassObj>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PassObj passObj);

    @Update
    void updatePass(PassObj passObj);

    @Delete
    void delete(PassObj passObj);
}
