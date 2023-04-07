package com.example.yessir;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class PickDate extends DialogFragment {
    Calendar calendar=Calendar.getInstance();
    private int year=Calendar.YEAR;
    private int month=Calendar.MONTH;
    private int day=Calendar.DAY_OF_MONTH;
    public Oncalenderokclick oncalenderokclick;
public interface Oncalenderokclick
{
    void onclick(int year,int month,int day);
}

    public void setOncalenderokclick(Oncalenderokclick oncalenderokclick) {
        this.oncalenderokclick = oncalenderokclick;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),(view, year, month, dayOfMonth) ->{
            oncalenderokclick.onclick(year,month,dayOfMonth);
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    void setdate(int year,int month,int day)
    {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
    }
    String getdate()
    {
        return DateFormat.format("dd.MM.yyy",calendar).toString();
    }
}
