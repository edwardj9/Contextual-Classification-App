package com.example.edward.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * Main activity of app, current categories menu
 */
public class MainActivity extends AppCompatActivity {
    GridView gv;
    SearchView sv;
    Context context;
    ArrayList<Category> categories;
    CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.categories==null){
            this.categories = initCategories();
        }

        gv = (GridView) findViewById(R.id.gridView);
        ImageView listBtn = (ImageView) findViewById(R.id.listBtn);
        ImageView gridBtn = (ImageView) findViewById(R.id.gridBtn);
        adapter = new CustomAdapter(this, categories, false);
        //Code for search bar
        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setisList(true);
                gv.setAdapter(adapter);
            }
        });

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setisList(false);
                gv.setAdapter(adapter);
            }
        });

        //Set activity to wordactivity on image click
        gv.setAdapter(adapter);
        ImageView settings = (ImageView) findViewById(R.id.settingsBtn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AppPreferences.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });

    }
    /*
     * Initialize all default categories for app
     */
    public ArrayList<Category> initCategories(){
        AssetManager assetManager = getAssets();
        ArrayList<Category> categories = new ArrayList<>();
        try{
            String[] catNames = assetManager.list("Categories");
            //FOR EVERY CATEGORY
            for(String category:catNames){
                ArrayList<Word> words = new ArrayList<>();
                //GET LIST OF WORDS FOR THAT CATEGORY
                String[] wordNames = assetManager.list("Categories/"+category);
                //GET CATEGORY PICTURE
                int catPic = getResources().getIdentifier(category.toLowerCase() ,"drawable",getPackageName());
                //FOR EVERY WORD IN CATEGORY
                for(int i = 0; i < wordNames.length; i++){
                    ArrayList<Integer> wordPics = new ArrayList<>();
                    String[] wordPicNames = assetManager.list("Categories/" +category+"/"+wordNames[i]);
                    for(String p:wordPicNames){
                        if( p.contains(".")){
                            p=p.substring(0, p.lastIndexOf("."));
                        }
                        int picId = getResources().getIdentifier(p.toLowerCase(),"drawable",getPackageName());
                        wordPics.add(picId);
                    }
                    Word word = new Word(wordNames[i], wordPics);
                    words.add(word);
                }
                categories.add(new Category(category, catPic, words));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return categories;
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Iterate through drawable folder and save to storage
    /*
    private void SaveDrawable(){
        Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.ic_launcher);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "ic_launcher.PNG");
        FileOutputStream outStream=null;
        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        finally{
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/


}
