 package com.example.yessir;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.studentviewholder> {
    ArrayList<Student_model>list;
    Context context;

    public Student_Adapter(ArrayList<Student_model> list, Context context) {
        this.list = list;
        this.context = context;
    }
    Onclicklisten onclicklisten;
    public interface Onclicklisten
    {
        void onclick(int position);
        boolean longclick(int positon);
    }
    public void setOnclicklisten(Onclicklisten onclicklisten) {
        this.onclicklisten = onclicklisten;
    }
    @NonNull
    @Override
    public studentviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.student_row,parent,false);
        return new studentviewholder(view,onclicklisten);
    }
    @Override
    public void onBindViewHolder(@NonNull studentviewholder holder, int position) {
        Student_model student_model=list.get(position);
        holder.status.setText(student_model.getStatus());
        holder.student_name.setText(student_model.getStudent_name());
        holder.roll_num.setText(""+student_model.getRoll());
        holder.cardView.setCardBackgroundColor(getcolor(position));
    }
    private int getcolor(int position) {
        String status=list.get(position).getStatus();
        if(status.equals("P"))
        {
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        }
        else if(status.equals("A"))
        {
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.apsent)));
        }
        else
        {
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class studentviewholder extends RecyclerView.ViewHolder {
        TextView roll_num,student_name,status;
        CardView cardView;
        public studentviewholder(@NonNull View itemView,Onclicklisten onclicklisten) {
            super(itemView);
            roll_num=itemView.findViewById(R.id.roll_num);
            student_name=itemView.findViewById(R.id.student_name);
            status=itemView.findViewById(R.id.status);
            cardView=itemView.findViewById(R.id.cardview);
           itemView.setOnClickListener(v->onclicklisten.onclick(getAdapterPosition()));
           itemView.setOnLongClickListener(v->onclicklisten.longclick(getAdapterPosition()));
        }
    }
}
