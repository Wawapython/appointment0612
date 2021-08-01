package com.example.appointment0612;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.SavedStateViewModelFactory;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private static String calStrings="AA";




    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this, year, month, day);
    }

    public interface DatePickerListener{
        void onDateSet(DatePicker datePicker, int year, int month, int day);
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month, dayOfMonth);

    }

    public void populateSetDate(int year, int month, int day){
        TextView textView = getActivity().findViewById(R.id.tvDate);
        String s = String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(day);
        textView.setText(s);
        this.calStrings = s;

        // Initialize Cloud Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance(); // connect db
        Map<String, Object> datef = new HashMap<>();
        datef.put("date", s);
        database.collection("Appoint").document("AppointDate").set(datef).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(getActivity(),"Okay", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String calStr(){
        System.out.println(calStrings);
        return calStrings;
    }



    }




