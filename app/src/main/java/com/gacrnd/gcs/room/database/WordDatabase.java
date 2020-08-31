package com.gacrnd.gcs.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author Jack_Ou  created on 2020/8/31.
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static volatile WordDatabase mInstance;

    public static WordDatabase getInstance(Context context) {
        if (mInstance == null){
            synchronized (WordDatabase.class){
                if (mInstance == null){
                    mInstance = Room.databaseBuilder(context,WordDatabase.class,"words").build();
                }
            }
        }
        return mInstance;
    }

    public abstract WordDao getWordDao();
}
