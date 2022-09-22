package com.to_do_application;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity
{
    private DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(this);
        updateView();
    }//end oncreate

    private void updateView()
    {
        ArrayList<Product> products = databaseManager.selectAll();
        ScrollView scrollView = new ScrollView(this);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RadioGroup radioGroup = new RadioGroup(this);

        //process the arraylist of products
        for(Product product : products)
        {
         //crea
            // te a radiobuttton to add radio group
            RadioButton radioButton = new RadioButton(this);

            radioButton.setId(product.getId());
            radioButton.setText(product.productToString());
            //add radio button to the radio group
            radioGroup.addView(radioButton);

        }//endforloop
    // create custom handler and attact to radio group
    RadioButtonHandler handler = new RadioButtonHandler();
     radioGroup.setOnCheckedChangeListener(handler);
     //add radio group to scroll view
     scrollView.addView(radioGroup);
     //add scrollviwe t relative layout
     relativeLayout.addView(scrollView);
     //create backbutton to get bback to mainactivity
        Button backButton = new Button(this);
        backButton.setText(R.string.back_button_label);
        backButton.setBackgroundColor(getResources().getColor(R.color.black));
        backButton.setTextColor(getResources().getColor(R.color.white));
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             finish();
            }
        });
        //format the button layout in relative layout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0,0,0,50);
        //add button to relativelayout usng parameters
        relativeLayout.addView(backButton,params);
        //display the relative layout
        setContentView(relativeLayout);



    }//end updateview method
    //custom handler for toucing a radio button
    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
         //delete the selected product
            databaseManager.deleteByID(checkId);
            Toast.makeText(DeleteActivity.this,"Task Deleted",Toast.LENGTH_SHORT).show();
            updateView();
        }//end of oncheckedchanged
    }//end of radiohandler class
}//end of deleteactivity