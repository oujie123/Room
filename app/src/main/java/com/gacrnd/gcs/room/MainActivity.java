package com.gacrnd.gcs.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;

import com.gacrnd.gcs.room.database.Word;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    private TextView wordsShow;
    private Button add, delete, update, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //如果是AndroidViewModel用这个工厂来创建AndroidViewModelFactory
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(WordViewModel.class);
        wordViewModel.getallWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                if (words.isEmpty()) {
                    wordsShow.setText("");
                }
                final StringBuilder builder = new StringBuilder();
                for (int i = 0; i < words.size(); i++) {
                    Word word = words.get(i);
                    builder.append(word.getId() + ":" + word.getWord() + "->" + word.getChineseMeaning() + "\n");
                    wordsShow.setText(builder.toString());
                }
            }
        });
    }

    private void initView() {
        wordsShow = findViewById(R.id.words);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        query = findViewById(R.id.query);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add:
                        Word word = new Word("apple", "苹果");
                        wordViewModel.insertWords(word, word);
                        break;
                    case R.id.delete:
                        wordViewModel.deleteAllWords();
                        break;
                    case R.id.update:

                        break;
                    case R.id.query:

                        break;
                    default:
                }
            }
        };
        add.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        update.setOnClickListener(listener);
        query.setOnClickListener(listener);
    }
}
