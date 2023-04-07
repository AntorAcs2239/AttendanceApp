package com.example.yessir;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class DialogClass extends DialogFragment {
    public static final String CLASS_ADD_DIALOG="Add Class";
    public static final String STUDENT_ADD_DIALOG="Add Studet";
    public static final String EDIT_OR_DELETE="editordelete";
    Onclicklisten onclicklisten;
    public interface Onclicklisten
    {
        void onaddclick(String classname,String subjectname);
    }
    public void setOnclicklisten(Onclicklisten onclicklisten) {
        this.onclicklisten = onclicklisten;
    }
    public String classname,subjectname;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog=null;

        if (getTag()==CLASS_ADD_DIALOG)
        {
            dialog=getaddclassdialog();
        }
        if (getTag()==STUDENT_ADD_DIALOG)
        {
            dialog=getaddstudentdialog();
        }
        if (getTag()==EDIT_OR_DELETE)
        {
            dialog=editordelete();
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.dialog);
        return dialog;
    }
    private Dialog editordelete() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);
        EditText roll=view.findViewById(R.id.editclass);
        EditText studentname=view.findViewById(R.id.editsubject);
        Button cancle= view.findViewById(R.id.cancle);
        Button add=view.findViewById(R.id.add);
        TextView title=view.findViewById(R.id.title);
        title.setText("Edit or Delete");
        roll.setText(classname);
        studentname.setText(subjectname);
        cancle.setText("Delete");
        add.setText("UPDATE");
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add.setOnClickListener(v->{
            String Roll=roll.getText().toString();
            String Student=studentname.getText().toString();
            if(Roll.length()==0||Student.length()==0)
            {
                Toast.makeText(getActivity(),"Two field should fill up",Toast.LENGTH_LONG).show();
            }
            else
            {
                String text1,text2;
                text1=roll.getText().toString();
                text2=studentname.getText().toString();
                onclicklisten.onaddclick(text1,text2);
                dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getaddstudentdialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);
        EditText roll=view.findViewById(R.id.editclass);
        EditText studentname=view.findViewById(R.id.editsubject);
        Button cancle= view.findViewById(R.id.cancle);
        Button add=view.findViewById(R.id.add);
        TextView title=view.findViewById(R.id.title);
        title.setText("Add Student");
        roll.setHint("Roll No");
        studentname.setHint("Student Name");
        cancle.setOnClickListener(v->dismiss());
        add.setOnClickListener(v->{
            String Roll=roll.getText().toString();
            String Student=studentname.getText().toString();
            if(Roll.length()==0||Student.length()==0)
            {
                Toast.makeText(getActivity(),"Two field should fill up",Toast.LENGTH_LONG).show();
            }
            else
            {
                String text1,text2;
                text1=roll.getText().toString();
                text2=studentname.getText().toString();
                onclicklisten.onaddclick(text1,text2);
                dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getaddclassdialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);
        EditText classname=view.findViewById(R.id.editclass);
        EditText subjectname=view.findViewById(R.id.editsubject);
        Button cancle= view.findViewById(R.id.cancle);
        Button add=view.findViewById(R.id.add);
        TextView title=view.findViewById(R.id.title);
        title.setText("Add Class Name");
        classname.setHint("Class Name");
        subjectname.setHint("Subject Name");
        cancle.setOnClickListener(v->dismiss());
        add.setOnClickListener(v->{
            String cls=classname.getText().toString();
            String subj=subjectname.getText().toString();
            if(cls.length()==0||subj.length()==0)
            {
                Toast.makeText(getActivity(),"Two field should fill up",Toast.LENGTH_LONG).show();
            }
            else
            {
                String text1,text2;
                text1=classname.getText().toString();
                text2=subjectname.getText().toString();
                onclicklisten.onaddclick(text1,text2);
                dismiss();
            }
        });
        return builder.create();
    }
}
