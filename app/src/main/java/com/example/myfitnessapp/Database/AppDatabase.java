package com.example.myfitnessapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfitnessapp.Dao.HistoryDao;
import com.example.myfitnessapp.Dao.ReminderDao;
import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.Model.ModelReminder;

@Database(entities = {ModelHistory.class, ModelReminder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
    public abstract ReminderDao reminderDao();
}