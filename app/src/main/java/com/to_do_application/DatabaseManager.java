package com.to_do_application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "productDB",
                                TABLE_NAME = "productTable",
                                ITEM_ID = "id",
                                ITEM_NAME = "name",
                                ITEM_PRICE = "price";
    private static final int DATABASE_VERSION = 1;
    //constructor


    public DatabaseManager(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//end constructor
    //two required methods:onCreate and onUpgrade

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "create table " + TABLE_NAME + "( " +
                ITEM_ID + " integer primary key autoincrement, " +
                ITEM_NAME + " text, " +
                ITEM_PRICE + " real )";
        sqLiteDatabase.execSQL(sqlCreate);
    }//end of onCreate

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String sqLDrop = "drop table if exists " + TABLE_NAME;
        sqLiteDatabase.execSQL(sqLDrop);
        onCreate(sqLiteDatabase);
    }//end onUpgrade
    //method to insert a product into the database
    public void insertProduct(Product product)
    {
        String sqlInsert = "insert into " +  TABLE_NAME +
                            " values( null, '" + product.getName() + "', " +
                            "'" + product.getPrice() + "' )";
        //get the database that the product is being added to
        SQLiteDatabase database = getWritableDatabase();
        //insert the product into the database
        database.execSQL(sqlInsert);
        //close the database
        database.close();
    }//end insertProduct
    //method to retrieve all info in the database
    public ArrayList<Product> selectAll()
    {
        String sqlSelect = "select * from " + TABLE_NAME;
        //get the database
        SQLiteDatabase database = getWritableDatabase();
        //create a cursor to process the database info one row at a time
        Cursor cursor = database.rawQuery(sqlSelect, null);
        //create the array list that holds the info
        ArrayList<Product> products = new ArrayList<>();
        //loop that processes the cursor object
        while (cursor.moveToNext() )
        {
            //get the id, name and price from current cursor
            Integer currentID = cursor.getInt(0);
            String currentName = cursor.getString(1);
            Double currentPrice = cursor.getDouble(2);
            //create a product object
            Product product= new Product(currentID, currentName, currentPrice);
            //ad  the product to the array list
            products.add(product);
        }//end of while
        //close the database
        database.close();
        return products;
    }//end selectAll
    //method to delete item by id number
    public void deleteByID(int id)
    {
     //string with delete command
     String sqlDelete = " delete from " + TABLE_NAME + " where "
                        + ITEM_ID + " = " + id;

     SQLiteDatabase database = getWritableDatabase();
     database.execSQL(sqlDelete);
     database.close();

    }//end deleteByID method
    //method to update a database item
    public void updateByID(int id, String name, double price )
    {
    String sqlUpdate = "update " + TABLE_NAME + " set " +
                       ITEM_NAME + " = '" + name + "'," +
                       ITEM_PRICE + " = '" + price + "'"+
                       " where " + ITEM_ID + " = " + id;
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sqlUpdate);
        database.close();



    }//end updatebyID method
}//end DatabaseManager class
