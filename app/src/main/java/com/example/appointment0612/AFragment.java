package com.example.appointment0612;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointment0612.databinding.FragmentABinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AFragment extends Fragment implements DatePickerFragment.DatePickerListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentABinding binding;
    String d;

    public AFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AFragment newInstance(String param1, String param2) {
        AFragment fragment = new AFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LiveData liveData;
        liveData = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(LiveData.class);

        FragmentABinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a, container, false);
        binding.setData(liveData);
        binding.setLifecycleOwner(requireActivity());


        // Button to Fragment B
        binding.btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_AFragment_to_BFragment);
                liveData.save();
            }
        });
        // numPicker1
        binding.numPicker1.setMinValue(0); //設定最小值
        binding.numPicker1.setMaxValue(2); //設定最大值
        binding.numPicker1.setValue(0); //設定現值
        int nowValue = binding.numPicker1.getValue();
        String[] pickerVals = new String[] {"Taiwan", "England", "Lao"};
        binding.numPicker1.setDisplayedValues(pickerVals);

        binding.numPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { //設定數字變化監聽事件
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                liveData.getNumber().setValue(newVal);

                String s = liveData.getNumber().getValue().toString();
//                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();


                // Initialize Cloud Firestore
                FirebaseFirestore database = FirebaseFirestore.getInstance(); // connect db
                Map<String, Object> countries = new HashMap<>();
                countries.put("country", s);
                database.collection("Appoint").document("Country").set(countries).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //Toast.makeText(getActivity(),"Okay", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
/*
        // get Calendar date from DatePickerFragment and set value in livedata
        DatePickerFragment datePicker = new DatePickerFragment();
        d = datePicker.calStr();
        Toast.makeText(getActivity(),d, Toast.LENGTH_SHORT).show();
        liveData.getCalendar().setValue(d);
*/

        // Choose Date
        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setCancelable(false);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                datePickerFragment.show(fm,"date Picker");
            }
        });

        //Choose Time
        binding.btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragmentFragment = new TimePickerFragment();
                FragmentManager d = getActivity().getSupportFragmentManager();
                timePickerFragmentFragment.show(d, "timePicker");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);
        String dateS = DateFormat.getDateInstance().format(cal.getTime());
        binding.tvDate.setText(dateS);
    }

}