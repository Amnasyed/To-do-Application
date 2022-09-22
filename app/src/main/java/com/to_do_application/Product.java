package com.to_do_application;

class Product
{
    private int id;
    private String name;
    private double price;

    public Product(int newId, String newName, double newPrice )
    {
        setId( newId );
        setName( newName );
        setPrice( newPrice );
    }//end constructor

    //Getters
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }


    //Setters
    public void setId( int newId )
    {
        id = newId;
    }

    public void setName( String newName )
    {
        name = newName;
    }

    public void setPrice( double newPrice )
    {
        price = 0.00;
        if( newPrice >= 0.00 )
            price = newPrice;
    }


    //Method to make displaying a Product object easier
    public String productToString()
    {
        return id + " " + name + " " + price;
    }
}//end Product class
