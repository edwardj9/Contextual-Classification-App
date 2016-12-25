package com.example.edward.learningapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database helper containing categories, Words, and image ids
 *
 * Created by edward on 8/19/16.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    private  Context context;
    // Database versions are used to internally tell the Framework which version of your DB to use
    // When the database constraints are changed the database them gets updated.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LearningApp.db";

    public static final String categoryId = "Category_ID";
    public static final String wordId = "Word_ID";
    public static final String wordImgId = "WordImg_ID";
    public static final String _ID = "_id"; // we need to use _ID because ID is already used by the system.
    public static final String TITLE = "title";
    public static final String BELONGS_TO = "belongs_to";

    public static final String WORDS = "Words";


    /**
     * CATEGORIES DATABASE CONSTANTS
     */
    public static final String CATEGORIES_TABLE = "categories";
    public static final String WORDS_TABLE = "words";
    public static final String WORDIMGS_TABLE = "wordImages";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORIES_TABLE + " (" +
                categoryId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL " + ");";
        final String SQL_CREATE_WORDS_TABLE = "CREATE TABLE " + WORDS_TABLE + " (" +
                wordId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " +
                BELONGS_TO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + CATEGORIES_TABLE + " (" + categoryId + ")" +
                ");";
        final String SQL_CREATE_WORDIMG_TABLE = "CREATE TABLE " + WORDIMGS_TABLE + " (" + wordImgId + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT NOT NULL, " +
                BELONGS_TO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + WORDS_TABLE + " (" + wordId + ")" +
                ");";



        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_WORDS_TABLE);
        db.execSQL(SQL_CREATE_WORDIMG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called whenever we need to update our Database
        // Examples of updating a DB is adding new tables or new columns to a existing table
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        db.execSQL("DROP TABLE IF EXITS" + WORDIMGS_TABLE);
        onCreate(db);
    }

    /**
     * Closes the Database Connection.
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * This method is used to insert a new Category entry in the database
     *
     * @param category
     *      The category to be inserted
     * @return
     *      The inserted statement for the Database - Used internally by Android
     */
    public long createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase(); // We get an WritableDatabase so we can insert data
        ContentValues values = new ContentValues(); // Create a new content values with Key Value Pairs
        values.put(TITLE, category.getCatName()); // Insert the TITLE for the Category

        return db.insert(CATEGORIES_TABLE, null, values);
    }

    public long createWord(Word word, long categoryId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, word.getWordName());
        values.put(BELONGS_TO, categoryId); // We get the ID of the categories so we can reference through a foreign key
        return db.insert(WORDS_TABLE, null, values);
    }

    public long createWordImg(String imgName, long wordId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, imgName);
        values.put(BELONGS_TO, wordId);
        return db.insert(WORDIMGS_TABLE, null, values);
    }



    /**
     *
     * @return
     *      A list with all Decks for the specific category
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT \n" +
                "categories.title as categories_title, \n" +
                "words.title as words_title, \n" +
                "wordImages.title as wordImages_title\n" +
                "FROM categories\n" +
                "JOIN words ON categories.id=words.belongs_to\n" +
                "JOIN wordImages ON words.id=wordImages.belongs_to";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //first row not empty
        if(cursor.moveToFirst()) {
            do {
                String catName = cursor.getString(0);
                String wordName = cursor.getString(1);
                String wordImg = cursor.getString(2);
                //row contains a new category
                if (!hasCategory(categories, catName)) {
                    Category category = new Category();
                    category.setCatName(cursor.getString(0));
                    category.setCatimg(context.getResources().getIdentifier(catName, "drawable"
                            , context.getPackageName()));
                    ArrayList<Word> words = new ArrayList<Word>();
                    Word word = new Word();
                    word.setWordName(cursor.getString(1));
                    ArrayList<Integer> wordImages = new ArrayList<Integer>();
                    wordImages.add(context.getResources().getIdentifier(wordImg, "drawable",
                            context.getPackageName()));
                    word.setImgs(wordImages);
                    words.add(word);
                    category.setWords(words);
                    categories.add(category);
                }
                //row does not contain a new category
                else{
                    Category category = getCategory(categories, catName);
                    ArrayList<Word> words = category.getWords();
                    //row contains a new word
                    if(!category.hasWord(wordName)){
                        Word word = new Word();
                        word.setWordName(cursor.getString(1));
                        ArrayList<Integer> wordImages = new ArrayList<Integer>();
                        wordImages.add(context.getResources().getIdentifier(wordImg, "drawable",
                                context.getPackageName()));
                        word.setImgs(wordImages);
                        category.addWord(word);
                    }
                    //row does not contain a new word
                    else{
                        Word word = category.getWord(wordName);
                        word.addImg(context.getResources().getIdentifier(wordImg, "drawable",
                                context.getPackageName()));
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.closeDB();
        return categories;
    }


    public Category getCategory(List<Category> categories, String catName){
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getCatName().equals(catName)){
                return categories.get(i);
            }
        }
        return null;
    }

    public static boolean hasCategory(List<Category> categories, String catName){
        for(int i=0; i< categories.size(); i++){
            if(categories.get(i).getCatName().equals(catName)){
                return true;
            }
        }
        return false;
    }






}

