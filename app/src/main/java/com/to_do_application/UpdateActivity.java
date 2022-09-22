package com.to_do_application;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity
{
  private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(this);
        updateView();
    }//end on create

    private void updateView()
    {
        //get the info from database
        ArrayList<Product> products = databaseManager.selectAll();
        //if ther is info in the database
        if(products.size()>0)
        {
            ScrollView scrollView = new ScrollView(this);
            GridLayout gridLayout = new GridLayout(this);
            //set the number ofd rowas and coloumns
            gridLayout.setRowCount(products.size() + 1);
            gridLayout.setColumnCount(4);
            //create a button handler
            ButtonHandler handler = new ButtonHandler();

            //determine the size of the screen
            Point point = new Point();
            getWindowManager().getDefaultDisplay().getSize(point);
            int width = point.x;
            //proces the products in arraylist
            for(Product product : products)

            {
             //handle id number
                TextView idTV = new TextView(this);
                idTV.setText("" + product.getId());

                idTV.setGravity(Gravity.CENTER);

                //handle product name
                EditText nameET = new EditText(this);
                nameET.setText(product.getName());
                nameET.setId(product.getId() * 10 );
                //handle product price
                EditText priceET = new EditText(this);
                priceET.setText(""+product.getPrice());
                priceET.setId(product.getId()* 10 + 1);
                priceET.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                //create button to start update process
                Button button = new Button(this);
                button.setText(R.string.update_button_label);
                button.setId(product.getId());
                button.setOnClickListener(handler);
                //put 4 pieces into one gridlayout
                gridLayout.addView(idTV,(int)(width * 0.1), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(nameET,(int)(width * 0.4), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(priceET,(int)(width * 0.15), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(button,(int)(width * 0.35), ViewGroup.LayoutParams.WRAP_CONTENT);

            }//end for loop
            //generate backbutton to go back to mainactivity
            Button backbuton = new Button(this);
            backbuton.setText(R.string.back_button_label);
            backbuton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            //setup parameters to place backbutton in gridlayout
            GridLayout.Spec rowSpec = GridLayout.spec(products.size(),1),
                            colSpec = GridLayout.spec(0,4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            //attach parameters to the backbutton
            backbuton.setLayoutParams(params);
            backbuton.setWidth(width);
            backbuton.setHeight((int)(width * 0.10));
            //backbuton.setText(R.string.back_button_label);
            backbuton.setGravity(Gravity.CENTER);
            //add backbutton to gridlayout
            gridLayout.addView(backbuton);
            //add gridlayout to scrollview
            scrollView.addView(gridLayout);
            //display scrollview
            setContentView(scrollView);
        }//end if
    }//end of updateview

    //class to handle buttonclicks
    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
         //get the id of button that was clicked
         int productID = view.getId();

         //get the info to be updated
            EditText nameET = findViewById(productID * 10),
                     priceET = findViewById(productID * 10 +1);
            String nameStr = nameET.getText().toString(),
                    priceStr = priceET.getText().toString();

            //convert the price to a double and update database entry
            try
            {
             double price = Double.parseDouble(priceStr);
             databaseManager.updateByID(productID ,nameStr ,price);
             updateView();

            }
            catch(NumberFormatException nfe)
            {
               Toast.makeText(UpdateActivity.this,"Update task error",Toast.LENGTH_SHORT).show();
            }
        }//end onclick
    }//end buttonhandler


}//end updateactivty