package com.projectHandClap.youruniv.ViewPager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.projectHandClap.youruniv.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link second#newInstance} factory method to
 * create an instance of this fragment.
 */
public class second extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String title;
    private int page;

    // TODO: Rename and change types of parameters

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page Parameter 1.
     * @param title Parameter 2.
     * @return A new instance of fragment second.
     */
    // TODO: Rename and change types and number of parameters

    public static second newInstance(int page, String title){
        second fragment = new second();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        EditText etxtLabel =  (EditText)view.findViewById(R.id.EditText);
        etxtLabel.setText(page + "--" + title);
        // Inflate the layout for this fragment
        return view;
    }
}
