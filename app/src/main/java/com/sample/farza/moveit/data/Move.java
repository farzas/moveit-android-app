package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by farzaali on 5/13/16.
 */
public class Move implements Serializable {

    Integer id;
    String name;
    String status;

    public Move(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Move( Integer id, String name){
        this.name = name;
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
