package com.gacrnd.gcs.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author Jack_Ou  created on 2020/8/31.
 */
@Database(entities = {Word.class}, version = 3, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static volatile WordDatabase mInstance;
    private static final String DATABASE_NAME = "words";

    public static WordDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (WordDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context, WordDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return mInstance;
    }

    public abstract WordDao getWordDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Word ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1");
            database.execSQL("ALTER TABLE Word ADD COLUMN show_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //Caused by: java.lang.IllegalStateException: Migration didn't properly handle:
            // 创建integer的时候，需要写默认值，否则就会报以上bug
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL, english_word TEXT," +
                    "chinese_meaning TEXT, show_data INTEGER NOT NULL DEFAULT 1)");
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning,show_data) " +
                    "SELECT id,english_word,chinese_meaning,show_data FROM Word");
            database.execSQL("DROP TABLE Word");
            database.execSQL("ALTER TABLE word_temp RENAME to Word");
        }
    };
}
