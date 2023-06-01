package com.example.brainpilot;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class EventAddFragment extends Fragment {

    Button buttonBack;
    Button buttonSave;
    Button buttonSelectTime;
    Context thiscontext;
    EditText eventName;
    TextView dateLabel;
    TextView timeLabel;

    int year;
    int month;
    int day;
    String hour;
    String min;
    public EventAddFragment(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
        this.hour = "";
        this.min = "";
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
        dateLabel.setText("Date: "+day+"/"+month+"/"+year);

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
                db.addEvent(day, month, year, hour,min,name);
            }
        });

        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("wtf","pressed");
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minu = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(thiscontext, androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfTheDay, int minu) {
                        Log.wtf("wtf","open hour picker");
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
                        c.set(Calendar.MINUTE,minu);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat formatHour = new SimpleDateFormat("k");
                        SimpleDateFormat formatMin = new SimpleDateFormat("mm");
                        hour = formatHour.format(c.getTime());
                        min = formatMin.format(c.getTime());
                        timeLabel.setText("Hour: "+ hour+ "H" +min);

                    }
                },hours,minu,false);
                timePickerDialog.show();
            }
        });

        return viewEvent;
    }

    private void initObjects(LayoutInflater inflater, ViewGroup container, View view){
        buttonBack = (Button) view.findViewById(R.id.backButton);
        buttonSave = (Button) view.findViewById(R.id.saveButton);
        buttonSelectTime = (Button) view.findViewById(R.id.selectTime);
        dateLabel = (TextView) view.findViewById(R.id.eventDate);
        timeLabel = (TextView) view.findViewById(R.id.eventTime);
        eventName = (EditText) view.findViewById(R.id.eventName);
    }

    public void moveToEventCalendarPage(View viewEvent){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,new CalendarFragment());
        fragmentTransaction.commit();
    }
}