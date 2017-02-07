package com.sample.farza.moveit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sample.farza.moveit.data.Box;
import com.sample.farza.moveit.data.Item;
import com.sample.farza.moveit.data.ItemBox;
import com.sample.farza.moveit.data.Move;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farzaali on 5/10/16.
 */
public class DataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    boolean moveExists;

    private String[] moveColumns = { DatabaseHelper.MOVE_COL_0,
            DatabaseHelper.MOVE_COL_1,
            DatabaseHelper.MOVE_COL_2 };

    private String[] ITEMColumns = { DatabaseHelper.ITEM_COL_0,
            DatabaseHelper.ITEM_COL_1,
            DatabaseHelper.ITEM_COL_2,
            DatabaseHelper.ITEM_COL_3,
            DatabaseHelper.ITEM_COL_4
    };

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    //------------------MOVE TABLE OPERATIONS-------------------

    public void createMove(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MOVE_COL_1, name);
        values.put(DatabaseHelper.MOVE_COL_2, "active");

        database.insert(DatabaseHelper.MOVE_TABLE_NAME, null, values);
    }


    /*get move id when move name is passed
    public Integer getMoveId(String moveName){
        final Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.MOVE_TABLE_NAME+" WHERE "+DatabaseHelper.MOVE_COL_1+"='" + moveName + "'", null);
        int moveId = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    moveId = cursor.getInt(cursor.getColumnIndex("ID"));
                }
            } finally {
                cursor.close();
            }
        }
        return moveId;
    }*/

    //---------------ITEM TABLE OPERATIONS----------------------

    public void createItem(String name, String category, String imagePath, Integer moveId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ITEM_COL_1, name);
        values.put(DatabaseHelper.ITEM_COL_2, category);
        values.put(DatabaseHelper.ITEM_COL_3, imagePath);
        values.put(DatabaseHelper.ITEM_COL_4, moveId);

        Long insertFlag = database.insert(DatabaseHelper.ITEM_TABLE_NAME, null, values);

        if(insertFlag == -1){
            Log.i("farza","Data not inserted into itemtable");
        }
    }



    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        // create Cursor in order to parse our sqlite results

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_TABLE_NAME,  null);

        // if Cursor is contains results

        if (cursor.getCount() > 0) {

            // move cursor to first row
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){

                    Item item = new Item(cursor.getString(cursor.getColumnIndex("NAME")),cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")), cursor.getString(cursor.getColumnIndex("ITEM_IMAGE_PATH")) );
                    items.add(item);
                    cursor.moveToNext();
                    // move to next row
                }//while (cursor.moveToNext());
            }
            //while (!cursor.isAfterLast()) {

        }
        return items;
        // make sure to close the cursor
        // cursor.close();
    }

    public List<Move> getAllPassiveMoveNames() {
        List<Move> moves = new ArrayList<>();

        // create Cursor in order to parse our sqlite results

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.MOVE_TABLE_NAME+ " WHERE STATUS = 'passive'",  null);

        // if Cursor is contains results

        if (cursor.getCount() > 0) {

            // move cursor to first row
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){

                    Move move = new Move(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("NAME")));
                    cursor.moveToNext();
                    moves.add(move);
                    // move to next row
                }//while (cursor.moveToNext());
            }
            //while (!cursor.isAfterLast()) {

        }
        return moves;
        // make sure to close the cursor
        // cursor.close();
    }

    public void setStatusPassive(){
        Integer moveId = getLatestMoveId();
        database.execSQL("UPDATE "+DatabaseHelper.MOVE_TABLE_NAME+" SET STATUS = 'passive' WHERE ID = "+moveId);

    }

    public Integer getLatestMoveId(){
        //select ID from move order by ID desc limit 1;
        final Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.MOVE_TABLE_NAME+" ORDER BY ID DESC LIMIT 1", null);
        int moveId = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    moveId = cursor.getInt(cursor.getColumnIndex("ID"));
                }
            } finally {
                cursor.close();
            }
        }
        return moveId;
    }
    public List<Item> getItemsForAMove(Integer moveId) {
        List<Item> items = new ArrayList<>();

        // create Cursor in order to parse our sqlite results
      //  moveId = 60;

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_TABLE_NAME+ " WHERE MOVE_ID = " +moveId+ ";",  null);

        // if Cursor is contains results

        if (cursor.getCount() > 0) {

            // move cursor to first row
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){

                    Item item = new Item(cursor.getString(cursor.getColumnIndex("NAME")),cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")), cursor.getString(cursor.getColumnIndex("ITEM_IMAGE_PATH")) );
                    items.add(item);
                    cursor.moveToNext();
                    // move to next row
                }//while (cursor.moveToNext());
            }
            //while (!cursor.isAfterLast()) {

        }
        return items;
        // make sure to close the cursor
        // cursor.close();
    }



    public List<String> getItemNameToPrint() {
        List<String> items = new ArrayList<>();

        // create Cursor in order to parse our sqlite results

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_TABLE_NAME,  null);

        // if Cursor is contains results

        if (cursor.getCount() > 0) {

            // move cursor to first row
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){

                    Item item = new Item(cursor.getString(cursor.getColumnIndex("NAME")),cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")), cursor.getString(cursor.getColumnIndex("ITEM_IMAGE_PATH")) );
                    items.add(item.getName());
                    cursor.moveToNext();
                    // move to next row
                }//while (cursor.moveToNext());
            }
            //while (!cursor.isAfterLast()) {

        }
        return items;
        // make sure to close the cursor
        // cursor.close();
    }

    public Integer getMoveId(String moveName){
        //select ID from move order by ID desc limit 1;
        final Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.MOVE_TABLE_NAME+" WHERE NAME = '"+moveName+"'", null);
        int moveId = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    moveId = cursor.getInt(cursor.getColumnIndex("ID"));
                }
            } finally {
                cursor.close();
            }
        }
        return moveId;
    }




    // **Amy

    //---------------BOX TABLE OPERATIONS----------------------

    public void createBox(Integer moveId, String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.BOX_COL_1, moveId);
        values.put(DatabaseHelper.BOX_COL_2, name);


        Long insertFlag = database.insert(DatabaseHelper.BOX_TABLE_NAME, null, values);

        if(insertFlag == -1){
            Log.i("AMY","Data not inserted into Box table");
        }

    }

    //** Amy insert into itembox table

    public void createItemBox(String name, String category, String imagePath, Integer moveId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ITEM_BOX_COL_1, name);
        values.put(DatabaseHelper.ITEM_BOX_COL_2, category);
        values.put(DatabaseHelper.ITEM_BOX_COL_3, imagePath);
        values.put(DatabaseHelper.ITEM_BOX_COL_4, moveId);

        Long insertFlag = database.insert(DatabaseHelper.ITEM_BOX_TABLE_NAME, null, values);
        Log.i("AMY","In createItemBox");

        if(insertFlag == -1){
            Log.i("AMY","Data not inserted into itemboxtable");
        }
    }

    public List<ItemBox> getAllItemsNotInBoxes(Integer moveId) {
        List<ItemBox> items = new ArrayList<>();

        // create Cursor in order to parse our sqlite results
        int zero = 0;
        String testString = "SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_5+"=" + zero+" and "+DatabaseHelper.ITEM_BOX_COL_4+"="+moveId;
        Log.i("AMY", testString);

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_5+"=" + zero+" and "+DatabaseHelper.ITEM_BOX_COL_4+"="+moveId,  null);

        // if Cursor is contains results
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            // move cursor to first row
            while(!cursor.isAfterLast()) {

                ItemBox item = new ItemBox(cursor.getInt(cursor.getColumnIndex("ID")),cursor.getString(cursor.getColumnIndex("NAME")),cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")), cursor.getString(cursor.getColumnIndex("ITEM_IMAGE_PATH")) );
                items.add(item);
                cursor.moveToNext();
                // move to next row
            }
            cursor.close();
            //while (!cursor.isAfterLast()) {

        }
        return items;
        // make sure to close the cursor
        // cursor.close();
    }

    public List<Box> getBoxesForThisMove(Integer moveId) {
        List<Box> boxes = new ArrayList<>();

        // create Cursor in order to parse our sqlite results

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.BOX_TABLE_NAME+" WHERE "+DatabaseHelper.BOX_COL_1+"=" + moveId ,  null);

        // if Cursor is contains results
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            // move cursor to first row
            while(!cursor.isAfterLast()){


                Box box = new Box(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getInt(cursor.getColumnIndex("MOVE_ID")),cursor.getString(cursor.getColumnIndex("NAME")));
                boxes.add(box);
                // move to next row
                cursor.moveToNext();
            }
            cursor.close();
        }
        return boxes;
        // make sure to close the cursor
        // cursor.close();
    }

    public void deleteexistingBoxes(Integer moveId){

        database.delete(DatabaseHelper.BOX_TABLE_NAME, DatabaseHelper.BOX_COL_1 + "=" + moveId, null);
    }


    public void updateItemBox(Integer itemId, Integer boxId){
        ContentValues cv = new ContentValues();
        cv.put("BOX_ID",boxId);
        database.update(DatabaseHelper.ITEM_BOX_TABLE_NAME, cv, "ID="+itemId, null);
    }

    public Integer getItemId(String itemName){
        String moveidQuery = "SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_1+"='" + itemName + "'";
        Log.i("AMY", moveidQuery);
        final Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_1+"='" + itemName + "'", null);
        int itemId = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    itemId = cursor.getInt(cursor.getColumnIndex("ID"));
                }
            } finally {
                cursor.close();
            }
        }
        return itemId;
    }

    public List<ItemBox> getAllItemsInThisBox(Integer boxId){

        List<ItemBox> itemsInBox = new ArrayList<>();
        String testString = "SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_5+"=" + boxId;
        Log.i("AMY", testString);

        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.ITEM_BOX_TABLE_NAME+" WHERE "+DatabaseHelper.ITEM_BOX_COL_5+"=" + boxId,  null);

        // if Cursor is contains results
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            // move cursor to first row
            while(!cursor.isAfterLast()) {

                ItemBox item = new ItemBox(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("NAME")),cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")), cursor.getString(cursor.getColumnIndex("ITEM_IMAGE_PATH")) );
                itemsInBox.add(item);
                cursor.moveToNext();
                // move to next row
            }
            cursor.close();
            //while (!cursor.isAfterLast()) {

        }
        return itemsInBox;
        // make sure to close the cursor
        // cursor.close();
    }

    public boolean moveAlreadyExists(Integer moveId){

        SQLiteStatement s = database.compileStatement( "SELECT count(*) FROM "+DatabaseHelper.BOX_TABLE_NAME+" WHERE "+DatabaseHelper.BOX_COL_1+"=" + moveId+";" );

        long count = s.simpleQueryForLong();
//        String testString = "SELECT count(*) FROM "+DatabaseHelper.BOX_TABLE_NAME+" WHERE "+DatabaseHelper.BOX_COL_1+"=" + moveId;
//        Log.i("AMY", testString);
//
//        Cursor cursor = database.rawQuery("SELECT count(*) FROM "+DatabaseHelper.BOX_TABLE_NAME+" WHERE "+DatabaseHelper.BOX_COL_1+"=" + moveId,  null);
//
//        // if Cursor is contains results
//        //cursor.moveToFirst();
//
//        if (cursor.getCount() > 0 && !cursor.isAfterLast()) {
//
//           moveExists = true;
//
//            //while (!cursor.isAfterLast()) {
//
//
//        }
//        else{
//            moveExists = false;
//        }
//        cursor.close();

        if(count >0){
            moveExists = true;
        }
        else{
            moveExists = false;
        }
        return moveExists;

    }



    // ** Amy

}

