package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by Amy on 5/24/16.
 */
public class ItemBox implements Serializable{



    Integer id;
    String name;
    String categoryId;
    String imagePath;
    Integer moveId;
    Integer boxId;

    public ItemBox(Integer id, String name, String categoryId, String imagePath) {

        this.id= id;
        this.name = name;
        this.categoryId = categoryId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getMoveId() {
        return moveId;
    }

    public void setMoveId(Integer moveId) {
        this.moveId = moveId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }
}
