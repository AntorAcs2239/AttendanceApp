package com.example.yessir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class RecordShowActivity extends AppCompatActivity {
    private ArrayList<SearchonlyStudetnModel>searchbydate;
    private RecyclerView recyclerView;
    private SearchonlyStudentAdapter adapter;
    DataBaseHelper dataBaseHelper;
    String date,subjectname;
    int tag,roll;
    long classid;
    TextView present,absent;
    int p=0,a=0;
    Toolbar toolbar;
    TextView textView1,textView2;
    ImageButton button,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_show);
        recyclerView=findViewById(R.id.recforevery);
        present=findViewById(R.id.present);
        absent=findViewById(R.id.absent);
        dataBaseHelper=new DataBaseHelper(this);
        searchbydate=new ArrayList<>();
        date=getIntent().getStringExtra("date");
        subjectname=getIntent().getStringExtra("subjectname");
        toolbar=findViewById(R.id.toolbar);
        textView1=toolbar.findViewById(R.id.maintitle);
        textView2=toolbar.findViewById(R.id.subtitle);
        button=toolbar.findViewById(R.id.save);
        back=toolbar.findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView2.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        classid=getIntent().getLongExtra("classid",-1);
        tag=getIntent().getIntExtra("tag",-1);
        roll=getIntent().getIntExtra("roll",-1);
        if (tag==1)
        {
            fetchdatabydate();
        }
        else
        {
            fetchdatabyroll();
        }
    }

    private void fetchdatabyroll() {
        Cursor cursor=dataBaseHelper.Get_Student_table_byroll(classid,roll);

        while(cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STUDET_NAME_KEY));
            int roll=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.STUDENT_ROLL_KEY));
            String status=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STATUS_KEY));
            String date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STATUS_DATE_KEY));
            if (status.equals("P"))p++;
            else a++;
            searchbydate.add(new SearchonlyStudetnModel(name,String.valueOf(roll),date,status,subjectname));
        }
        present.setText("Present="+p);
        absent.setText("Absent="+a);
        adapter=new SearchonlyStudentAdapter(searchbydate,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
    private void fetchdatabydate() {
        Cursor cursor=dataBaseHelper.Get_Student_table(classid,date);
        while(cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STUDET_NAME_KEY));
            int roll=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.STUDENT_ROLL_KEY));
            String status=cursor.getString(cursor.getColumnIndex(DataBaseHelper.STATUS_KEY));
            if (status.equals("P"))p++;
            else a++;
            searchbydate.add(new SearchonlyStudetnModel(name,String.valueOf(roll),date,status,subjectname));
        }
        present.setText("Present="+p);
        absent.setText("Absent="+a);
        adapter=new SearchonlyStudentAdapter(searchbydate,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
}