package com.gacrnd.gcs.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gacrnd.gcs.room.database.Word;

import java.util.List;

/**
 * LinearLayoutManager 线性的
 * GridLayoutManager 网格的，即二位的
 */
public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter1;
    private MyAdapter myAdapter2;
    private Button add, delete;
    private Switch aSwitch;

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
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                //通知刷新界面
                myAdapter1.notifyDataSetChanged();
                myAdapter2.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        myAdapter1 = new MyAdapter(false);
        myAdapter2 = new MyAdapter(true);
        recyclerView = findViewById(R.id.words);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        aSwitch = findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    recyclerView.setAdapter(myAdapter2);
                } else {
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add:
                        String[] english = new String[]{
                                "cpx", "yxx", "oj", "oywy", "wls", "slj", "hdj", "lxp", "fwd", "crd", "zs", "hxj", "tj"
                        };
                        String[] chinese = new String[]{
                                "谌鹏翔", "杨筱筱", "欧杰", "欧阳文彧", "伍令胜", "宋灵杰", "何东健", "李晓平", "符伟达", "陈锐东", "张舜", "何小军", "汤剑"
                        };
                        for (int i = 0; i < english.length; i++) {
                            Word word = new Word(english[i], chinese[i]);
                            wordViewModel.insertWords(word, word);
                        }
                        break;
                    case R.id.delete:
                        wordViewModel.deleteAllWords();
                        break;
                    default:
                }
            }
        };
        add.setOnClickListener(listener);
        delete.setOnClickListener(listener);
    }
}
