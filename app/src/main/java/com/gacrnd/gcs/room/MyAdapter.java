package com.gacrnd.gcs.room;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gacrnd.gcs.room.database.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建RecyclerView Adapter
 *
 * @author Jack_Ou  created on 2020/9/1.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Word> allWords = new ArrayList<>();
    private boolean useCardView;

    public MyAdapter(boolean useCardView) {
        this.useCardView = useCardView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (useCardView){
            view = layoutInflater.inflate(R.layout.cell_card_view, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.cell_normal, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Word word = allWords.get(position);
        holder.tvId.setText(String.valueOf(position));
        holder.tvEnglish.setText(word.getWord());
        holder.tvChinese.setText(word.getChineseMeaning());
        //设置监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q="+ holder.tvEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId;
        private TextView tvEnglish;
        private TextView tvChinese;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.cell_id);
            tvEnglish = itemView.findViewById(R.id.cell_english);
            tvChinese = itemView.findViewById(R.id.cell_chinese);
        }
    }
}
