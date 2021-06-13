package com.example.appointment0612;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class LiveData extends AndroidViewModel {
    SavedStateHandle handle;

    private MutableLiveData<Integer> number;
    public MutableLiveData<Integer> getNumber(){
        if(number==null){
            number = new MutableLiveData<>();
            number.setValue(3333);
        }
        return number;
    }

    public LiveData(@NonNull Application application) {
        super(application);
    }

    private int oldv;
    private int newv;
    public int getNumPick1(int oldv, int newv) {
        this.oldv = oldv;
        this.newv = newv;
        return newv;
    }

}
