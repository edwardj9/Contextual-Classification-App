package com.example.edward.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class word_activity extends AppCompatActivity implements wordSelect_fragment.onFragmentWordSelect {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Category category = (Category) getIntent().getParcelableExtra("category");
        //Make bundle of category and pass to word_fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        word_activity_fragment wActivity_fragment = new word_activity_fragment();
        wActivity_fragment.setArguments(bundle);
        //Pass bundle to word select fragment
        wordSelect_fragment wSelect_fragment = new wordSelect_fragment();
        wSelect_fragment.setArguments(bundle);

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, wActivity_fragment, "wActivity");
        transaction.add(android.R.id.content, wSelect_fragment, "wSelect");
        transaction.commit();

    }

    public void onWordSelect(Word word){
        word_activity_fragment wActivity = (word_activity_fragment) getSupportFragmentManager().
                findFragmentByTag("wActivity");
        if(wActivity != null){
            wActivity.setImages(word);
        }
    }

    public void onMenu(){
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
    }


}
