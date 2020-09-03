package com.gacrnd.gcs.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gacrnd.gcs.room.database.Word;
import com.gacrnd.gcs.room.database.WordDao;
import com.gacrnd.gcs.room.database.WordDatabase;

import java.util.List;

/**
 * @author Jack_Ou  created on 2020/8/31.
 */
public class WordsRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLive;

    public WordsRepository(Context context) {
        wordDao = WordDatabase.getInstance(context.getApplicationContext()).getWordDao();
        allWordsLive = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public void insertWords(Word... words){
        new InsertAsycTask(wordDao).execute(words);
    }

    public void deleteAllWords(){
        new DeleteAllAsycTask(wordDao).execute();
    }

    public void updateWords(Word... words){
        new UpdateAsycTask(wordDao).execute(words);
    }

    //第二个参数是报告进度，第三个是报告结果
    static class InsertAsycTask extends AsyncTask<Word,Void,Void> {
        private WordDao wordDao;

        public InsertAsycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsycTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public UpdateAsycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsycTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public DeleteAsycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsycTask extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;

        public DeleteAllAsycTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
