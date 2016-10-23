package com.example.edward.learningapp;

/**
 * Created by edward on 8/19/16.
 *
 * Pending for database
 */
public abstract class Item {

    private String title; // All Items must have a title so we can display on a list
    private long id; // Each card will also have an ID so we can find it inside our SQLite DB
    private int color; // Optionally you might want separate cards by color.

    /**
     * Basic Constructor
     */
    protected Item() {
    }

    /**
     * @param title
     *       The title for this Item
     */
    protected Item(String title) {
        this.title = title;
    }

    // STANDARD GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}