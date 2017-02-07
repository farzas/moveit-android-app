package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by farzaali on 5/13/16.
 */
public class Category implements Serializable {

    Integer id;
    String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
