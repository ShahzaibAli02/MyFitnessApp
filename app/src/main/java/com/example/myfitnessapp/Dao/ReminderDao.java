package com.example.myfitnessapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.Model.ModelReminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM modelreminder")
    List<ModelReminder> getAll();

    @Insert
    void insertAll(ModelReminder... modelReminders);

    @Delete
    void delete(ModelReminder modelReminder);

}

