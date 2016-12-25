package com.example.edward.learningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class for word object
 * 
 * Created by edward on 7/28/16.
 */
public class Word extends Item implements Parcelable{

    private String wordName;
    private ArrayList<Integer> imgs;
    private Category category;
    private boolean completed;


    public Word(){

    }
   /*
    * Word constructor
    */
   public Word(String wordName, ArrayList<Integer> imgs){
        this.wordName = wordName;
        this.imgs = imgs;
        this.completed=false;
    }
    /*
     * Sets word as completed
     */
    public void setCompleted(){
        this.completed=true;
    }
    
    /*
     * Checks if word is compelted
     * @return true if category is completed
     */
    public boolean isCompleted(){
        return this.completed;
    }

    /*
     * Category setter
     */
    public void setCategory(Category category){
        this.category = category;
    }

    /*
     * Returns current category
     */
    public Category getCategory(){
        return this.category;
    }
    
    /*
     * Gets name of word
     * @return name of word
     */
    public String getWordName(){
        return wordName;
    }

    /*
     * Sets name of word
     */
    public void setWordName(String wordName){
        this.wordName = wordName;
    }
    /*
     * Sets list of images pertaining to word
     */
    public void setImgs(ArrayList<Integer> imgs){
        this.imgs = imgs;
    }
    /*
     * Get list of images for word
     * 
     * @return list of image ids
     */
    public ArrayList<Integer> getImgs(){
        return imgs;
    }
    /*
     * Adds an image to imagelist for word
     */
    public void addImg(Integer newImg){
        imgs.add(newImg);
    }

    //CODE FOR PARCEBLABLE OBJECT BELOW
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
