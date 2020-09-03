package com.gacrnd.gcs.room;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private WordViewModel viewModel;

    public MyAdapter(boolean useCardView,WordViewModel viewModel) {
        this.useCardView = useCardView;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (useCardView) {
            view = layoutInflater.inflate(R.layout.cell_card_view_btn, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.cell_normal_btn, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
        holder.tvId.setText(String.valueOf(position));
        holder.tvEnglish.setText(word.getWord());
        holder.tvChinese.setText(word.getChineseMeaning());
        //setChecked会触发点击回调，在初始化的时候，先把监听器设为空
        holder.showChineseWord.setOnCheckedChangeListener(null);
        if (word.isShow()) {
            holder.tvChinese.setVisibility(View.VISIBLE);
            holder.showChineseWord.setChecked(true);
        } else {
            holder.tvChinese.setVisibility(View.GONE);
            holder.showChineseWord.setChecked(false);
        }
        //设置监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + holder.tvEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.showChineseWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.tvChinese.setVisibility(View.VISIBLE);
                } else {
                    holder.tvChinese.setVisibility(View.GONE);
                }
                word.setShow(b);
                viewModel.updateWords(word);
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
        private Switch showChineseWord;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.cell_id);
            tvEnglish = itemView.findViewById(R.id.cell_english);
            tvChinese = itemView.findViewById(R.id.cell_chinese);
            showChineseWord = itemView.findViewById(R.id.sw_show);
        }
    }
}
