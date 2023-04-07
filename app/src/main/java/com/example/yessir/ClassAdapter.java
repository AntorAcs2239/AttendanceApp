package com.example.yessir;

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

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.viewholde> {
    ArrayList<Class_item> list;
    Context context;
    private Onclicklisten onclicklisten;

    public interface Onclicklisten {
        void onclick(int position);
        boolean longclick(int position);
    }

    public void setOnclicklisten(Onclicklisten onclicklisten) {
        this.onclicklisten = onclicklisten;
    }

    public ClassAdapter(ArrayList<Class_item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classrow, parent, false);
        return new viewholde(view, onclicklisten);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholde holder, int position) {
        Class_item class_item = list.get(position);
        holder.subjectname.setText(class_item.getSubjectname());
        holder.classname.setText(class_item.getClassname());
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

    public class viewholde extends RecyclerView.ViewHolder {
        TextView classname, subjectname;

        public viewholde(@NonNull View itemView, Onclicklisten onclicklisten) {
            super(itemView);
            classname = itemView.findViewById(R.id.classes);
            subjectname = itemView.findViewById(R.id.suject);
            itemView.setOnClickListener(v -> onclicklisten.onclick(getAdapterPosition()));
           itemView.setOnLongClickListener(v->onclicklisten.longclick(getAdapterPosition()));
        }
    }
}
