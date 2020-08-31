package com.gacrnd.gcs.room.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao: Database access object
 * @author Jack_Ou  created on 2020/8/31.
 */
@Dao
public interface WordDao{
    @Insert
    void insertWords(Word... words);

    @Update
    int updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWords();
}
