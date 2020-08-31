package com.gacrnd.gcs.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gacrnd.gcs.room.database.Word;
import com.gacrnd.gcs.room.database.WordDao;
import com.gacrnd.gcs.room.database.WordDatabase;

import java.util.List;

/**
 * @author Jack_Ou  created on 2020/8/31.
 */
public class WordViewModel extends AndroidViewModel {
    WordsRepository wordsRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordsRepository = new WordsRepository(application);
    }

    public void insertWords(Word... words){
        wordsRepository.insertWords(words);
    }

    public void deleteAllWords(){
        wordsRepository.deleteAllWords();
    }

    public  LiveData<List<Word>> getallWordsLive (){
        return wordsRepository.getAllWordsLive();
    }


}
