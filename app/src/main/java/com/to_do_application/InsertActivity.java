package com.to_do_application;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity
  {
      private DatabaseManager myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //create the database
      myDB = new DatabaseManager(this);
    }// end oncreate
   //add an item to the database
      public void addItem(View view)
      {
          EditText  nameET = findViewById(R.id.itemNameEditText) ,
                    priceTE = findViewById(R.id.itemPriceEditText);
          String nameStr = nameET.getText().toString(),
                  priceStr =priceTE.getText().toString();
//          myDB.insertProduct(1);
          //put the ninfo into the database
          try
          {
           double price = Double.parseDouble(priceStr);
           Product newProduct = new Product(0,nameStr,price);
           myDB.insertProduct(newProduct);


          }
          catch (NumberFormatException nfe)
          {
              Toast.makeText(this, "Complete All Required Fields", Toast.LENGTH_SHORT).show();
          }
      //clear the information fields and put the focus on the name field
          nameET.setText("");
          priceTE.setText("");
          nameET.requestFocus();


      }//end of addditem
      //button to go to mainactivity
      public void goBack(View view)
      {
       finish();
      }








  }
