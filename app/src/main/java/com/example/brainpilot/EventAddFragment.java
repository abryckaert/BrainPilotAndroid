package com.example.brainpilot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventAddFragment extends Fragment {

    Button buttonBack;

    public EventAddFragment() {
    }


    public static EventAddFragment newInstance(String param1, String param2) {
        EventAddFragment fragment = new EventAddFragment();
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
        initObjects(inflater, container, viewEvent);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEventCalendarPage(viewEvent);
            }
        });
        return viewEvent;
    }

    private void initObjects(LayoutInflater inflater, ViewGroup container, View view){
        buttonBack = (Button) view.findViewById(R.id.addEvent);
    }

    public void moveToEventCalendarPage(View viewEvent){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,new CalendarFragment());
        fragmentTransaction.commit();
    }
}