package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by farzaali on 5/13/16.
 */
public class Item implements Serializable{

    Integer id;
    String name;
    String categoryId;
    String imagePath;
    Integer moveId;

    public Item( String name, String categoryId, String imagePath) {

        this.name = name;
        this.categoryId = categoryId;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
