package com.example.edward.learningapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/*
 * Fragment that uses RecyclerAdapter on wordActivity screen. Manages word navigation
 */
public class wordSelect_fragment extends Fragment{
    private LinearLayout layout;
    RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    Word sendWord;
    onFragmentWordSelect mSendWord;


    public interface onFragmentWordSelect{
        public void onWordSelect(Word w);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mSendWord = (onFragmentWordSelect) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement onFragmentWordSelect");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_word_select_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvWords);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Category category = bundle.getParcelable("category");
            final ArrayList<Word> words = category.getWords();
            //set RecyclerAdapter for word selection
            mRecyclerView.setAdapter(new RecyclerAdapter(getContext(), words, new RecyclerAdapter.OnItemClickListener(){
                @Override public void onItemClick(Word item) {
                    sendWord = item;
                    mSendWord.onWordSelect(sendWord);
                }
            }));
        }

    }



}
