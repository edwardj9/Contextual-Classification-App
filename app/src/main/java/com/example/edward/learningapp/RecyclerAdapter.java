package com.example.edward.learningapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by edward on 8/18/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Word> words;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Word item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView text;
        public final ImageView image;

        public ViewHolder(View v) {
            super(v);

            text = (TextView) v.findViewById(R.id.textView1);
            image = (ImageView) v.findViewById(R.id.imageList);
        }

        public void bind(final Word item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    public RecyclerAdapter(Context context, ArrayList<Word> words, OnItemClickListener listener){
        this.context = context;
        this.words = words;
        this.listener = listener;
    }

    private Context getContext(){
        return this.context;
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ImageDownSize display = new ImageDownSize((context));

        Word word = words.get(position);
        TextView textView = holder.text;
        ImageView imageView = holder.image;
        textView.setText(word.getWordName());
        //can change this to recent context
        display.setScaledImage(imageView, word.getImgs().get(0));
        holder.bind(word, listener);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.customword, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

}
