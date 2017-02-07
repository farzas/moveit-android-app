package com.sample.farza.moveit.data;

import java.io.Serializable;

/**
 * Created by farzaali on 5/13/16.
 */
public class BoxItem implements Serializable {

    Integer boxId;
    Integer itemId;

    public BoxItem(Integer boxId, Integer itemId) {
        this.boxId = boxId;
        this.itemId = itemId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
