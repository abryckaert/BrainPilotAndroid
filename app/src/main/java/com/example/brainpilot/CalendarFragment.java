package com.example.brainpilot;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brainpilot.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    FloatingActionButton buttonAddEvent;
    int year;
    int month;
    int dayOfMonth;
    String queryResult;
    Context thisContext;
    MyDatabaseHelper myDB;

    LinearLayout eventLavout;
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

        thisContext = container.getContext();
        myDB = new MyDatabaseHelper(thisContext);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int yearSelected, int monthSelected, int dayOfMonthSelected) {
                eventLavout.removeAllViews();
                year = yearSelected;
                month = monthSelected;
                dayOfMonth = dayOfMonthSelected;
                String date = yearSelected + "/" + monthSelected + "/" + dayOfMonthSelected;
                Log.wtf("date changed", "onSelectedDayChange" + date);
                int numberOfEvent = myDB.countResult("SELECT * FROM event WHERE day = " + dayOfMonth + " AND month = " + month);
                Log.wtf("number of event for the day", "" + numberOfEvent);
                if (numberOfEvent != 0) {
                    ArrayList<TextView> textViewsArray = new ArrayList<TextView>();
                    ArrayList<String> namesEvent = myDB.executeQuery("SELECT name FROM event WHERE day = " + dayOfMonth + " AND month = " + month);
                    ArrayList<String> hourEvent = myDB.executeQuery("SELECT hour FROM event WHERE day = " + dayOfMonth + " AND month = " + month);
                    ArrayList<String> minEvent = myDB.executeQuery("SELECT min FROM event WHERE day = " + dayOfMonth + " AND month = " + month);
                    Log.wtf("name of the event", namesEvent.get(0).toString());
                    for (int i = 0; i <= numberOfEvent -1; i++) {
                        Log.wtf("Event number", Integer.toString(i));
                        textViewsArray.add(new TextView(thisContext));
                        textViewsArray.get(i).setText(hourEvent.get(i) + "h" + minEvent.get(i) + " " + namesEvent.get(i));
                        textViewsArray.get(i).setTextSize(30);
                        eventLavout.addView(textViewsArray.get(i));
                    }
                }
                else {
                    eventLavout.removeAllViews();
                    TextView noEvent = new TextView(thisContext);
                    noEvent.setTextSize(30);
                    noEvent.setText("No event today");
                    eventLavout.addView(noEvent);
                }
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
        eventLavout = (LinearLayout) view.findViewById(R.id.eventLavout);
    }

    private void moveToEventAddPage(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,new EventAddFragment(year,month,dayOfMonth));
        fragmentTransaction.commit();
    }
}