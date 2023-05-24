package com.example.brainpilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.brainpilot.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    FloatingActionButton buttonAddEvent;
    int year;
    int month;
    int dayOfMonth;

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public CalendarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initObjects(container, view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int yearSelected, int monthSelected, int dayOfMonthSelected) {
                year = yearSelected;
                month = monthSelected;
                dayOfMonth = dayOfMonthSelected;
                String date =  yearSelected + "/" + monthSelected + "/" + dayOfMonthSelected;
                Log.wtf("wtf","onSelectedDayChange" + date);
            }
        });

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEventAddPage();
            }
        });
        return view;
    }

    private void initObjects(ViewGroup container, View view){
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        buttonAddEvent = (FloatingActionButton) view.findViewById(R.id.addEvent);
    }

    private void moveToEventAddPage(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,new EventAddFragment(year,month,dayOfMonth));
        fragmentTransaction.commit();
    }
}