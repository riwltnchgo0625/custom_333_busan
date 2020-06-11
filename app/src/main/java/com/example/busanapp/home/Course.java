package com.example.busanapp.home;

public class Course {
    private String Title;
    private String Category;
    private String Description;
    private int Thumbnail;

    /*
    public Course() {
    }
    */

    public Course(String title, String category, String description, int thumbnail) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

}
