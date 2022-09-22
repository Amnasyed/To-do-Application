package com.to_do_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.to_do_application.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private DatabaseManager myDB;
    private RecyclerView mRecyclerview;
    private ToDoAdapter adapter;
    private List<Product> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRecyclerview = findViewById(R.id.recyclerview);
        setSupportActionBar(binding.toolbar);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myDB = new DatabaseManager(getApplicationContext());
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB , MainActivity.this);
        mRecyclerview.setAdapter(adapter);
        mList = myDB.selectAll();
        Collections.reverse(mList);
        adapter.setTasks(mList);
//        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent helpIntent = new Intent(MainActivity.this,HelpActivity.class);
//                startActivity(helpIntent);
//            }//end onClick method
//        });//end setOnClickListener method
    }// end onCreate method

    @Override
    protected void onResume() {
        super.onResume();
        updateScreen();
    }//end onResume
    private void updateScreen()
    {
        String databaseContentString;
        //create the database
        myDB = new DatabaseManager(this);

        //put a title into the string
        databaseContentString = "To do items is in the database\n\n";
        //get info from database
        ArrayList<Product> products = myDB.selectAll();
        //process the arraylist
        for (Product product : products )
        {
            //append each product onto the string
            databaseContentString += product.productToString() + "\n";

        }//end of forloop
        //connect to the textview and put the strign info
//        TextView databaseTV = findViewById(R.id.databaseTextView);
//        databaseTV.setText(databaseContentString);

    }//end updateScreen

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }// end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add)
        {
            Toast.makeText(this,"ADD option", Toast.LENGTH_SHORT).show();
            Intent addIntent = new Intent(MainActivity.this,InsertActivity.class);
            startActivity(addIntent);

            return true;



        }
        else if (id == R.id.action_edit)
        {
            Toast.makeText(this,"EDIT option", Toast.LENGTH_SHORT).show();
            Intent updateIntent = new Intent(MainActivity.this,UpdateActivity.class);
            startActivity(updateIntent);
            return true;
        }
        else if (id == R.id.action_delete)
        {
            Toast.makeText(this,"DELETE option", Toast.LENGTH_SHORT).show();
            Intent deleteIntent = new Intent(MainActivity.this,DeleteActivity.class);
            startActivity(deleteIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}