package com.example.brainpilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.brainpilot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        remplaceFragment(new KanbanFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.kanban:
                    remplaceFragment(new KanbanFragment());
                    Log.d("myTag", "move to kanban");
                    break;
                case R.id.calendar:
                    remplaceFragment(new CalendarFragment());
                    Log.d("myTag", "move to calendar");
                    break;
                case R.id.study:
                    remplaceFragment(new StudyFragment());
                    Log.d("myTag", "move to study");
                    break;
                case R.id.setting:
                    remplaceFragment(new SettingFragment());
                    Log.d("myTag", "move to setting");
                    break;
            }
            return true;
        });
    }

    private void remplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2,fragment);
        fragmentTransaction.commit();
    }
}