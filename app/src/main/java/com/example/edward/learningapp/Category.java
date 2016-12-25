package com.example.edward.learningapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward on 7/28/16.
 *
 * Category Object, contains name, list of words, and image
 */
public class Category extends Item implements Parcelable  {

    private String catName;
    private ArrayList<Word> words;
    private int catimg;
    private int catId;
    
    
    public Category(){
    }

    public Category(String CatName, int catId){

    }

    /*
     * Create a category object
     * @param catName - the category name
     * @param catimg - the drawable image id
     * @param words - list of words belonging to category
     */
    public Category(String catName, int catimg, ArrayList<Word> words){
        this.catName = catName;
        this.words = words;
        this.catimg = catimg;
    }


    /*
     * Returns the name of the category
     * @return the category name
     */
    public String getCatName(){
        return catName;
    }
    
    /*
     * Sets the name of the category
     */
    public void setCatName(String name){
        this.catName=name;
    }
    
    /*
     * Returns the arraylist of words belonging to category
     * @return the list of words
     */
    public ArrayList<Word> getWords(){
        return words;
    }

    /*
     * Sets list of words to a category
     */
    public void setWords(ArrayList<Word> wordSet){
        this.words = wordSet;
    }

    /*
     * Get image id of category picture
     *
     * @return image id of category picture
     */
    public int getCatimg(){
        return catimg;
    }
    /*
     * Sets image id of category picture
     */
    public void setCatimg(int catimg){
        this.catimg = catimg;
    }
    /*
     * Gets word at specific index i of category
     * @return Word at index i
     */
    public Word getWord(int i){
        return words.get(i);
    }

    /*
     * Adds word to category
     */
    public void addWord(Word word){
        words.add(word);
    }

    /*
     * Gets word via word name from category
     * @return word object
     */
    public Word getWord(String wordName){
        for(int i=0; i<words.size(); i++){
            if(words.get(i).getWordName().equals(wordName)){
                return words.get(i);
            }
        }
        return null;
    }

    /*
     * Checks if category contains a certain word
     * @return true if category has word
     */
    public boolean hasWord(String wordName){
        for(int i =0; i < words.size(); i++){
            if(words.get(i).getWordName().equals(wordName)){
                return true;
            }
        }
        return false;
    }

    /*
     * Gets percentage completed of current category
     * @return percentage complete
     */
    public int getPercentage(){
        int completedAmount=0;
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).isCompleted()){
                completedAmount+=1;
            }
        }
        return (completedAmount*100)/words.size();
    }


    //Code to make object parcelable
    private Category(Parcel in) {
        catName = in.readString();
        if (in.readByte() == 0x01) {
            words = new ArrayList<Word>();
            in.readList(words, Word.class.getClassLoader());
        } else {
            words = null;
        }
        catimg = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(catName);
        if (words == null) {
            dest.writeByte((byte) (0x00));

        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(words);
        }
        dest.writeInt(catimg);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };


}
