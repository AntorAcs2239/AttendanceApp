package com.example.yessir;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveData{
    private SharedPreferences sharedPreferences;
    private Gson gson;
   public  SaveData(Context context)
  {
      sharedPreferences=context.getSharedPreferences("tasksave",Context.MODE_PRIVATE);
      gson=new Gson();
  }
    public void save_class_task(ArrayList<Class_item>list)
  {
      SharedPreferences.Editor editor=sharedPreferences.edit();
      editor.putString("task",gson.toJson(list));
      editor.apply();
  }
  public ArrayList<Class_item>gettask()
  {
      String tem=sharedPreferences.getString("task",null);
      Type tasklist=new TypeToken<ArrayList<Class_item>>(){}.getType();
      ArrayList<Class_item>class_items=gson.fromJson(tem,tasklist);
      if(class_items!=null)return class_items;
      else return new ArrayList<>();
  }
}
