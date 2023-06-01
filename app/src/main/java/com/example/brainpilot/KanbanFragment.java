package com.example.brainpilot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KanbanFragment extends Fragment {
    LinearLayout col1, col2, col3;
    FloatingActionButton addButton;
    EditText inputNote;
    int cardId = 0;

    public KanbanFragment() {
    }

    public static KanbanFragment newInstance() {
        return new KanbanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kanban, container, false);

        col1 = view.findViewById(R.id.col1);
        col2 = view.findViewById(R.id.col2);
        col3 = view.findViewById(R.id.col3);
        inputNote = view.findViewById(R.id.input_note);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String note = inputNote.getText().toString();
            if (!note.isEmpty()) {
                addCardToColumn(note, col1);
                inputNote.setText("");
            }
        });

        return view;
    }

    private void addCardToColumn(String note, LinearLayout column) {
        Button card = new Button(getContext());
        card.setText(note);
        card.setId(cardId++);
        card.setOnClickListener(v -> {
            if (v.getParent() == col1) {
                moveCard(card, col1, col2);
            } else if (v.getParent() == col2) {
                moveCard(card, col2, col3);
            } else if (v.getParent() == col3) {
                moveCard(card, col3, col1);
            }
        });
        column.addView(card);
    }

    private void moveCard(Button card, LinearLayout from, LinearLayout to) {
        if (from.indexOfChild(card) != -1) {
            from.removeView(card);
            to.addView(card);
        } else {
            Toast.makeText(getActivity(), "La carte n'existe pas dans la colonne d'origine.", Toast.LENGTH_SHORT).show();
        }
    }
}
