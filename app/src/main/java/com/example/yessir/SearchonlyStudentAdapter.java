package com.example.yessir;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchonlyStudentAdapter extends RecyclerView.Adapter<SearchonlyStudentAdapter.viewholder> {
    ArrayList<SearchonlyStudetnModel>list;
    Context context;
    public SearchonlyStudentAdapter(ArrayList<SearchonlyStudetnModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.studentrecordrow,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        SearchonlyStudetnModel studetnModel=list.get(position);
        holder.status.setText(studetnModel.getStatus());
        holder.date.setText(studetnModel.getDate());
        holder.subject_name.setText(studetnModel.getSubject_name());
        holder.student_roll.setText(studetnModel.getStudent_roll());
        holder.student_name.setText(studetnModel.getStudent_name());
        animation(holder.itemView,position);
    }
    private void animation(View itemView, int position) {
        Animation animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.setAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView student_name,student_roll,subject_name,date,status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            student_name=itemView.findViewById(R.id.sname);
            student_roll=itemView.findViewById(R.id.sroll);
            subject_name=itemView.findViewById(R.id.subject_name);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
        }
    }
}
