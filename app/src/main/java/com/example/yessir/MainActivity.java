package com.example.yessir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Class_item> class_items = new ArrayList<>();
    Toolbar toolbar;
    ImageButton backbtn, save;
    TextView textView1, textView2;
    SaveData saveData;
    private DataBaseHelper dataBaseHelper;
    private String institutionname;
    private static String CHECK;
    SharedPreferences sharepef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.addclass);
        sharepef = getPreferences(MODE_PRIVATE);
        CHECK = sharepef.getString("check", null);
        if (CHECK == null) showdialogfirst();
        else showtoolbar();
        fab.setOnClickListener(v -> showdialog());
        recyclerView = findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
//        saveData=new SaveData(this);
//        class_items=saveData.gettask();
        dataBaseHelper = new DataBaseHelper(this);
        loadexistsdata();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        classAdapter = new ClassAdapter(class_items, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(classAdapter);
//        classAdapter.setOnclicklisten(new ClassAdapter.Onclicklisten() {
//            @Override
//            public void onclick(int position) {
//                gotoitemactivity(position);
//            }
//        });
        classAdapter.setOnclicklisten(new ClassAdapter.Onclicklisten() {
            @Override
            public void onclick(int position) {
                gotoitemactivity(position);
            }

            @Override
            public boolean longclick(int position) {
                editordelete(position);
                return true;
            }
        });
    }

    private void showdialogfirst() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Button delete, edit;
        TextView class_edit, subject_edit, title;
        class_edit = view.findViewById(R.id.editclass);
        class_edit.setHint("institution Name");
        subject_edit = view.findViewById(R.id.editsubject);
        subject_edit.setVisibility(View.GONE);
        title = view.findViewById(R.id.title);
        title.setText("Add Your Institution Name");
        delete = view.findViewById(R.id.cancle);
        edit = view.findViewById(R.id.add);
        delete.setVisibility(View.GONE);
        edit.setText("Submit");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1, text2;
                text1 = class_edit.getText().toString();
                CHECK = text1;
                saveme(CHECK);
                showtoolbar();
                alertDialog.dismiss();
            }
        });
    }

    public void saveme(String name) {
        SharedPreferences.Editor editor = sharepef.edit();
        editor.putString("check", name);
        editor.apply();
    }

    private void loadexistsdata() {
        Cursor cursor = dataBaseHelper.Get_Class_table();
        class_items.clear();
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.CLASS_ID));
            String classname = cursor.getString(cursor.getColumnIndex(DataBaseHelper.CLASS_NAME));
            String subjectname = cursor.getString(cursor.getColumnIndex(DataBaseHelper.SUBJECT_NAME_KEY));
            class_items.add(new Class_item(classname, subjectname, id));
        }
    }

    private void editordelete(int position) {
        String classname, subjectname;
        classname = class_items.get(position).getClassname();
        subjectname = class_items.get(position).getSubjectname();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Button delete, edit;
        TextView class_edit, subject_edit, title;
        class_edit = view.findViewById(R.id.editclass);
        subject_edit = view.findViewById(R.id.editsubject);
        title = view.findViewById(R.id.title);
        title.setText("Update Class");
        class_edit.setText(classname);
        subject_edit.setText(subjectname);
        delete = view.findViewById(R.id.cancle);
        edit = view.findViewById(R.id.add);
        delete.setText("Delete");
        delete.setVisibility(View.GONE);
        edit.setText("UPDATE");
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataBaseHelper.deleteclass(class_items.get(position).getID());
//                class_items.remove(position);
//                classAdapter.notifyDataSetChanged();
//                alertDialog.dismiss();
//            }
//        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1, text2;
                text1 = class_edit.getText().toString();
                text2 = subject_edit.getText().toString();
                long row = dataBaseHelper.Update_Class(text1, text2, class_items.get(position).getID());
                class_items.get(position).setSubjectname(text2);
                class_items.get(position).setClassname(text1);
                classAdapter.notifyItemChanged(position);
                alertDialog.dismiss();
            }
        });
    }

    private void showtoolbar() {
        toolbar = findViewById(R.id.toolbar);
        backbtn = toolbar.findViewById(R.id.backbtn);
        save = toolbar.findViewById(R.id.save);
        textView1 = toolbar.findViewById(R.id.maintitle);
        textView2 = toolbar.findViewById(R.id.subtitle);
        textView1.setText(CHECK + "");
        textView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showdialogfirst();
                return true;
            }
        });
        save.setVisibility(View.GONE);
        backbtn.setVisibility(View.GONE);
    }

    private void gotoitemactivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("classname", class_items.get(position).getClassname());
        intent.putExtra("subjectname", class_items.get(position).getSubjectname());
        intent.putExtra("class_id", class_items.get(position).getID());
        startActivity(intent);
    }

    private void showdialog() {
        DialogClass dialogClass = new DialogClass();
        dialogClass.show(getSupportFragmentManager(), DialogClass.CLASS_ADD_DIALOG);
        dialogClass.setOnclicklisten(new DialogClass.Onclicklisten() {
            @Override
            public void onaddclick(String classname, String subjectname) {
                addclass(classname, subjectname);
            }
        });
    }

    private void addclass(String classname, String subjectname) {
        long row = dataBaseHelper.ADD_CLASS(classname, subjectname);
        Class_item class_item = new Class_item(classname, subjectname, row);
        class_items.add(class_item);
        classAdapter.notifyDataSetChanged();
        //saveData.save_class_task(class_items);
    }
}