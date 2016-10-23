package com.example.edward.learningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by edward on 7/28/16.
 */
public class Word extends Item implements Parcelable{

    private String wordName;
    private ArrayList<Integer> imgs;
    private Category category;
    private boolean completed;


    public Word(){

    }

   public Word(String wordName, ArrayList<Integer> imgs){
        this.wordName = wordName;
        this.imgs = imgs;
        this.completed=false;
    }

    public void setCompleted(){
        this.completed=true;
    }

    public Boolean isCompleted(){
        return this.completed;
    }


    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return this.category;
    }

    public String getWordName(){
        return wordName;
    }

    public void setWordName(String wordName){
        this.wordName = wordName;
    }

    public void setImgs(ArrayList<Integer> imgs){
        this.imgs = imgs;
    }

    public ArrayList<Integer> getImgs(){
        return imgs;
    }

    public void addImg(Integer newImg){
        imgs.add(newImg);
    }

    private Word(Parcel in) {
        wordName = in.readString();
        if (in.readByte() == 0x01) {
            imgs = new ArrayList<Integer>();
            in.readList(imgs, String.class.getClassLoader());
        } else {
            imgs = null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wordName);
        if (imgs == null) {
            dest.writeByte((byte) (0x00));

        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imgs);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

}
