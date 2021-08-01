package com.example.appointment0612;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class LiveData extends AndroidViewModel {
    SavedStateHandle handle;
    private static final String KEY_NUM1= "KEY_NUM1"; //numpick1
    private static final String KEY_NUM2= "KEY_NUM2"; //numpick2
    private static final String KEY_CALENDAR= "KEY_CALENDAR"; //calendar
    private static final String KEY_TIME= "KEY_TIME"; //Time

    private static final String SAVE_SHP = "save_sharedper";

    public LiveData(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_NUM1)){
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP, Context.MODE_PRIVATE);
            handle.set(KEY_NUM1,shp.getInt(KEY_NUM1, 0));
            handle.set(KEY_CALENDAR,shp.getString(KEY_CALENDAR, null));
            handle.set(KEY_TIME,shp.getString(KEY_TIME, null));
        }
        this.handle = handle;
    }
    private MutableLiveData<Integer> number;
    public MutableLiveData<Integer> getNumber(){
        return handle.getLiveData(KEY_NUM1);
    }
    private MutableLiveData<String> calendar;
    public MutableLiveData<String> getCalendar(){
        return handle.getLiveData(KEY_CALENDAR);
    }
    private MutableLiveData<String> time;
    public MutableLiveData<String> getTime(){
        return handle.getLiveData(KEY_TIME);
    }

    void save(){ // press navigation and then will do this function
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =shp.edit();
        editor.putInt(KEY_NUM1, getNumber().getValue());
        editor.putString(KEY_CALENDAR, getCalendar().getValue());
        editor.putString(KEY_TIME, getTime().getValue());
        editor.apply();
    }


}
