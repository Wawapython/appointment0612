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
    private static final String KEY_NUM= "key_num";
    private static final String SAVE_SHP = "save_sharedper";

    public LiveData(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_NUM)){
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP, Context.MODE_PRIVATE);
            handle.set(KEY_NUM,shp.getInt(KEY_NUM, 0));
        }
        this.handle = handle;
    }
    private MutableLiveData<Integer> number;
    public MutableLiveData<Integer> getNumber(){
        return handle.getLiveData(KEY_NUM);
    }
    void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =shp.edit();
        editor.putInt(KEY_NUM, getNumber().getValue());
        editor.apply();
    }
}
