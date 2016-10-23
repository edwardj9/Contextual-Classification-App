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
    //private Bitmap catimg;

    public Category(){

    }

    public Category(String CatName, int catId){

    }

    public Category(String catName, int catimg, ArrayList<Word> words){
        this.catName = catName;
        this.words = words;
        this.catimg = catimg;
    }




    public String getCatName(){
        return catName;
    }

    public void setCatName(String name){
        this.catName=name;
    }

    public ArrayList<Word> getWords(){
        return words;
    }

    public void setWords(ArrayList<Word> wordSet){
        this.words = wordSet;
    }

    public int getCatimg(){
        return catimg;
    }

    public void setCatimg(int catimg){
        this.catimg = catimg;
    }

    public Word getWord(int i){
        return words.get(i);
    }

    public void addWord(Word word){
        words.add(word);
    }

    public Word getWord(String wordName){
        for(int i=0; i<words.size(); i++){
            if(words.get(i).getWordName().equals(wordName)){
                return words.get(i);
            }
        }
        return null;
    }

    public boolean hasWord(String wordName){
        for(int i =0; i < words.size(); i++){
            if(words.get(i).getWordName().equals(wordName)){
                return true;
            }
        }
        return false;
    }

    public int getPercentage(){
        int completedAmount=0;
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).isCompleted()){
                completedAmount+=1;
            }
        }
        return (completedAmount*100)/words.size();
    }





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
