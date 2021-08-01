package com.example.appointment0612;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// Ref : https://www.tutorialandexample.com/android-time-picker-example/
public class TimePickerFragment  extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Time time=new Time();
        time.setHourOfDay(hourOfDay);
        time.setMinute(minute);

        Time dv = new Time();
        String timeS = new String();
        timeS = "";
        timeS = dv.getHourOfDay()+ " : " + dv.getMinute();
        TextView txtTime = getActivity().findViewById(R.id.tvTime);
        txtTime.setText(timeS);

        // Initialize Cloud Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance(); // connect db
        Map<String, Object> timef = new HashMap<>();
        timef.put("time", timeS);
        database.collection("Appoint").document("AppointTime").set(timef).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(getActivity(),"Okay", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}