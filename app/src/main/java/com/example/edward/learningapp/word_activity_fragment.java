package com.example.edward.learningapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class word_activity_fragment extends Fragment {
    private GridView gv;
    TextView wordName;
    Button menuButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_activity_fragment, container, false);
        gv = (GridView) view.findViewById(R.id.wordImgs);
        wordName = (TextView) view.findViewById(R.id.word_name);
        menuButton = (Button) view.findViewById(R.id.menu_categories);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((word_activity)getActivity()).onMenu();
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            Category category = bundle.getParcelable("category");
            Word word = category.getWord(0);
            gv.setAdapter(new CustomAdapter(getActivity(), word));
            wordName.setText(word.getWordName());
            word.setCompleted();
        }

    }

    public void setImages(Word word){
        gv.setAdapter(new CustomAdapter(getActivity(), word));
        wordName.setText(word.getWordName());
        word.setCompleted();
    }


}
