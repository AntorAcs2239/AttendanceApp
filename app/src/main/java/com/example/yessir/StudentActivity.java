package com.example.yessir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StudentActivity extends AppCompatActivity {
    String classname, subjectname;
    ImageButton save, backbtn;
    Toolbar toolbar;
    private  RecyclerView recyclerView;
    private  ArrayList<Student_model>studentlist=new ArrayList<>();
    private  ArrayList<StatusModel>statusModels=new ArrayList<>();
    private ArrayList<Student_model>temporary=new ArrayList<>();
    private  Student_Adapter student_adapter;
    private  DataBaseHelper dataBaseHelper;
    private long classid;
    private String subjectfrommain;
    private int day,month,year;
    TextView maintitle,subtitle;
    private PickDate pickDate;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        pickDate=new PickDate();
        dataBaseHelper=new DataBaseHelper(StudentActivity.this);
        recyclerView=findViewById(R.id.studentrec);
        student_adapter=new Student_Adapter(studentlist,this);
        recyclerView.setAdapter(student_adapter);
        classid=getIntent().getLongExtra("class_id",-1);
        subjectfrommain=getIntent().getStringExtra("subjectname");
        floatingActionButton=findViewById(R.id.addstudent);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showstudentdialog();
            }
        });
        showtoolbar();
        loadstudent();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        student_adapter.setOnclicklisten(new Student_Adapter.Onclicklisten() {
            @Override
            public void onclick(int position) {
                showstatus(position);
            }
            @Override
            public boolean longclick(int positon) {
                deleteorupdatestudent(positon);
                return true;
            }
        });
    }
    private void deleteorupdatestudent(int position) {
        String classname,subjectname;
        String studentname = studentlist.get(position).getStudent_name();
        int studentroll=studentlist.get(position).getRoll();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Button delete,edit;
        TextView studentname_edit,roll_eidt,title;
        studentname_edit=view.findViewById(R.id.editsubject);
        roll_eidt=view.findViewById(R.id.editclass);
        title=view.findViewById(R.id.title);
        title.setText("Edit or Delete");
        studentname_edit.setText(studentname);
        roll_eidt.setText(""+studentroll);
        delete=view.findViewById(R.id.cancle);
        edit=view.findViewById(R.id.add);
        delete.setText("Delete");
        edit.setText("UPDATE");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deleteStudent(studentlist.get(position).getSid());
                studentlist.remove(position);
                student_adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1,text2;
                text1=studentname_edit.getText().toString();
                text2=roll_eidt.getText().toString();
                long row=dataBaseHelper.update_student(text1,Integer.parseInt(text2),studentlist.get(position).getSid());
                studentlist.get(position).setStudent_name(text1);
                 studentlist.get(position).setRoll(Integer.parseInt(text2));
                student_adapter.notifyItemChanged(position);
                alertDialog.dismiss();
            }
        });
    }
    private void loadstudent() {
        Cursor cursor=dataBaseHelper.Get_Student_table(classid,pickDate.getdate());
        studentlist.clear();
        while (cursor.moveToNext())
        {
            long studentid=cursor.getLong(cursor.getColumnIndex(DataBaseHelper.STUDENT_ID));
            String studentname=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STUDET_NAME_KEY));
            int studentroll=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.STUDENT_ROLL_KEY));
            String date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STATUS_DATE_KEY));
            String status=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STATUS_KEY));
            studentlist.add(new Student_model(studentname,studentid,studentroll,date,status));
        }
        student_adapter.notifyDataSetChanged();
        cursor.close();
    }
    private void showstatus(int position) {
        if(studentlist.get(position).getStatus()=="P")studentlist.get(position).setStatus("A");
        else if(studentlist.get(position).getStatus()=="A")studentlist.get(position).setStatus("P");
        else studentlist.get(position).setStatus("P");
        student_adapter.notifyDataSetChanged();
    }
    private void showtoolbar() {
        classname = getIntent().getStringExtra("classname");
        subjectname = getIntent().getStringExtra("subjectname");
        toolbar = findViewById(R.id.toolbar);
        backbtn = toolbar.findViewById(R.id.backbtn);
        save = toolbar.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatestatus();
                Toast.makeText(StudentActivity.this,"Attendence Saved",Toast.LENGTH_LONG).show();
            }
        });
        maintitle=toolbar.findViewById(R.id.maintitle);
        subtitle=toolbar.findViewById(R.id.subtitle);
        maintitle.setText(classname);
        subtitle.setText(subjectname+" "+pickDate.getdate());
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(item->onmenuitemclick(item));
    }

    private void updatestatus() {
        for (Student_model student_model:studentlist)
        {
            long row=dataBaseHelper.update_status(student_model.getSid(),student_model.getDate(),student_model.getStatus());
        }
    }

    private void loadstatus()
    {

        //Log.i("studentid", String.valueOf(studentlist.size()));
        String tem=null;
        for (int i=0;i<studentlist.size();i++)
        {

            tem+=studentlist.get(i).getStatus()+" ";
            String status=dataBaseHelper.get_status_table(studentlist.get(i).getSid(),pickDate.getdate());
            tem+=status+" ";
           // Log.i("status",status);
            if (status!=null)
            {
                Toast.makeText(this,"wrongwrong",Toast.LENGTH_LONG).show();
                studentlist.get(i).setStatus(status);
            }
            else studentlist.get(i).setStatus(" ");
        }
        Toast.makeText(StudentActivity.this,"wrong "+tem,Toast.LENGTH_LONG).show();
        student_adapter.notifyDataSetChanged();
    }
    private void insertstatus() {

        for (Student_model student_model:studentlist)
        {
            String status=student_model.getStatus();
            if (status!="P")status="A";
            long row=dataBaseHelper.add_status(student_model.getSid(),pickDate.getdate(),status,classid);
            if(row==-1)
            {
                dataBaseHelper.update_status(student_model.getSid(),pickDate.getdate(),status);
            }
        }
    }
    private boolean onmenuitemclick(MenuItem item) {
        if (item.getItemId()==R.id.detailbyroll)
        {
           showbyroll();
        }
        if(item.getItemId()==R.id.changedate)
        {
            showcalendar();
        }
        if (item.getItemId()==R.id.searchbydate)
        {
            Intent intent=new Intent(StudentActivity.this,RecordShowActivity.class);
            intent.putExtra("date",pickDate.getdate());
            intent.putExtra("subjectname",subjectfrommain);
            intent.putExtra("classid",classid);
            intent.putExtra("tag",1);
            startActivity(intent);
        }
        return true;
    }
    private void showcalendar() {
        pickDate.show(getSupportFragmentManager(),"");
        pickDate.setOncalenderokclick(new PickDate.Oncalenderokclick() {
            @Override
            public void onclick(int year, int month, int day) {
                datapicker(year,month,day);
            }
        });
    }
    private void datapicker(int year, int month, int day) {
        Cursor cursor=dataBaseHelper.Get_Student_table(classid,pickDate.getdate());
        pickDate.setdate(year,month,day);
        subtitle.setText(subjectname+" "+pickDate.getdate());
        Cursor cursor1=dataBaseHelper.Get_Student_table(classid,pickDate.getdate());
        int i=0;
        temporary.clear();
        if (cursor1.getCount()==0)
        {
            Toast.makeText(this," "+studentlist.size(),Toast.LENGTH_LONG).show();
            for (Student_model student_model:studentlist)
            {
                i++;
                temporary.add(student_model);
            }
        }
        else
        {
            for (Student_model student_model:studentlist)
            {
                if (student_model.getStatus()==" ")temporary.add(student_model);
            }
        }
        addtodatabase();
        loadstudent();
        cursor.close();
        cursor1.close();
    }
    private void addtodatabase() {
        for (int i=0;i<temporary.size();i++)
        {
            long row= dataBaseHelper.Add_Student(classid,temporary.get(i).getStudent_name(),temporary.get(i).getRoll(),pickDate.getdate());
            Student_model student_model=new Student_model(temporary.get(i).getStudent_name(),row,temporary.get(i).getRoll(),pickDate.getdate());
        }
    }
    private void addstudent2(long classid, String student_name, int roll, String getdate) {
        long row= dataBaseHelper.Add_Student(classid,student_name,roll,getdate);
        Student_model student_model=new Student_model(student_name,row,roll,getdate);
        studentlist.add(student_model);
        student_adapter.notifyDataSetChanged();
    }
    private void showstudentdialog() {
        DialogClass dialogClass=new DialogClass();
        dialogClass.show(getSupportFragmentManager(),dialogClass.STUDENT_ADD_DIALOG);
        dialogClass.setOnclicklisten(new DialogClass.Onclicklisten() {
            @Override
            public void onaddclick(String roll, String studentname) {
                addstudent(roll,studentname);
            }
        });
    }
    private void showbyroll()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Button delete,edit;
        TextView studentname_edit,roll_eidt,title;
        studentname_edit=view.findViewById(R.id.editsubject);
        roll_eidt=view.findViewById(R.id.editclass);
        title=view.findViewById(R.id.title);
        title.setText("Enter Your Roll");
        studentname_edit.setVisibility(View.GONE);
        roll_eidt.setHint("Roll ");
        delete=view.findViewById(R.id.cancle);
        edit=view.findViewById(R.id.add);
        delete.setVisibility(View.GONE);
        edit.setText("Go");
        edit.setTextSize(22);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t=roll_eidt.getText().toString();
                if(t.length()==0)
                {
                    Toast.makeText(StudentActivity.this,"Enter Roll",Toast.LENGTH_LONG).show();
                }
                else
                {
                    int roll= Integer.parseInt(roll_eidt.getText().toString());
                    Intent intent=new Intent(StudentActivity.this,RecordShowActivity.class);
                    intent.putExtra("roll",roll);
                    intent.putExtra("classid",classid);
                    intent.putExtra("tag",2);
                    intent.putExtra("subjectname",subjectfrommain);
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            }
        });
    }
    private void addstudent(String roll, String studentname) {
        long row;
        try {
            row= dataBaseHelper.Add_Student(classid,studentname,Integer.parseInt(roll),pickDate.getdate());
            Student_model student_model=new Student_model(studentname,row,Integer.parseInt(roll),pickDate.getdate());
            studentlist.add(student_model);
            student_adapter.notifyDataSetChanged();
        }
        catch (Exception c)
        {
            c.printStackTrace();
            Toast.makeText(StudentActivity.this,"Wrong Roll Number",Toast.LENGTH_SHORT).show();
        }
    }
}