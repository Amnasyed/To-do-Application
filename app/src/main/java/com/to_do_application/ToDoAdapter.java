package com.to_do_application;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<Product> mList;
    private MainActivity activity;
    private DatabaseManager myDB;

    public ToDoAdapter(DatabaseManager myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent , false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product item = mList.get(position);
        holder.mCheckBox.setText(item.getName());
        holder.mCheckBox.setChecked(toBoolean((int) item.getPrice()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myDB.updateByID(item.getId() , "amna",1);
                }else
                    myDB.updateByID(item.getId() , "hello",1);
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<Product> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

//    public void deletTask(int position){
//        Product item = mList.get(position);
//        myDB.delete(item.getId());
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void editItem(int position){
//        ToDoModel item = mList.get(position);
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("id" , item.getId());
//        bundle.putString("task" , item.getTask());
//
//        AddNewTask task = new AddNewTask();
//        task.setArguments(bundle);
//        task.show(activity.getSupportFragmentManager() , task.getTag());
//
//
//    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
