package com.example.myfitnessapp.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myfitnessapp.Model.ModelHistory;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM modelhistory")
    List<ModelHistory> getAll();

    @Query("SELECT * FROM modelhistory WHERE title LIKE :title LIMIT 1")
    ModelHistory findByTitle(String title);


    @Insert
    void insertAll(ModelHistory... modelHistories);



    @Delete
    void delete(ModelHistory modelHistory);

}

