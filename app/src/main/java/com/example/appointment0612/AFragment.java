package com.example.appointment0612;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.appointment0612.databinding.FragmentABinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        binding.btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_AFragment_to_BFragment);
            }
        });


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
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(),"当前值"+newVal+"   " + " 上一个值"+oldVal,Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_a, container, false);
        return binding.getRoot();
    }
}