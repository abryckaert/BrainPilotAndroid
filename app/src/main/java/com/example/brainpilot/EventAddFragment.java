package com.example.brainpilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EventAddFragment extends Fragment {

    Button buttonBack;
    Button buttonSave;
    Context thiscontext;
    EditText eventName;
    TextView dateLabel;

    int year;
    int month;
    int day;
    public EventAddFragment(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
        Log.wtf("wtf","année " + year + "mois " + month + "jour " + day);
    }


    public static EventAddFragment newInstance(int year, int month, int dayOfMonth) {
        EventAddFragment fragment = new EventAddFragment(year, month, dayOfMonth);
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewEvent = inflater.inflate(R.layout.fragment_event_add, container, false);
        thiscontext = container.getContext();

        initObjects(inflater, container, viewEvent);
        dateLabel.setText(day+"/"+month+"/"+year);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEventCalendarPage(viewEvent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(thiscontext);
                String name = eventName.getText().toString();
                db.addEvent(day, month, year, name);
            }
        });

        return viewEvent;
    }

    private void initObjects(LayoutInflater inflater, ViewGroup container, View view){
        buttonBack = (Button) view.findViewById(R.id.backButton);
        buttonSave = (Button) view.findViewById(R.id.saveButton);
        dateLabel = (TextView) view.findViewById(R.id.eventDate);
        eventName = (EditText) view.findViewById(R.id.eventName);
    }

    public void moveToEventCalendarPage(View viewEvent){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,new CalendarFragment());
        fragmentTransaction.commit();
    }
}