package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by farzaali on 5/13/16.
 */
public class Box implements Serializable {

    Integer id;
    Integer moveId;
    String name;

    public Box(Integer id, Integer moveId, String name) {
        this.id = id;
        this.moveId = moveId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoveId() {
        return moveId;
    }

    public void setMoveId(Integer moveId) {
        this.moveId = moveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
