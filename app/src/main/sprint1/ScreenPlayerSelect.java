package com.example.cs2340s1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cs2340s1.navigation.FragmentHelper;
import com.example.cs2340s1.navigation.NewView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ScreenPlayerSelect extends NewView {

    private Button BtnContinue; // example of what I need you to do
    private TextInputEditText inputPlayerName;
    private TextView difficultySelect;
    private RadioButton btnRadioEasy, btnRadioMedium, btnRadioHard;

    private int SelectedDifficulty = -1;

    public ScreenPlayerSelect() {
        super(ScreenPlayerSelect.class, R.layout.screen_player_input, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BtnContinue = getActivity().findViewById(R.id.BtnContinue);
        inputPlayerName = getActivity().findViewById(R.id.InputPlayerName);
        btnRadioEasy = getActivity().findViewById(R.id.BtnRadioEasy);
        btnRadioMedium = getActivity().findViewById(R.id.BtnRadioMedium);
        btnRadioHard = getActivity().findViewById(R.id.BtnRadioHard);

        for (RadioButton btn : new RadioButton[]{btnRadioEasy, btnRadioMedium, btnRadioHard}) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnRadioEasy.isChecked()) {
                        SelectedDifficulty = 0;
                    }
                    else if (btnRadioMedium.isChecked()) {
                        SelectedDifficulty = 1;
                    }
                    else if (btnRadioHard.isChecked()) {
                        SelectedDifficulty = 2;
                    }

                    for (RadioButton neighbor : new RadioButton[]{btnRadioEasy, btnRadioMedium, btnRadioHard}) {
                        if (btn != neighbor && neighbor.isChecked()) {
                            neighbor.setChecked(false);
                        }
                    }
                }
            });
        }

        BtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                int SelectedChar = getArguments().getInt("SelectedChar");

                Bundle b = new Bundle();
                if (SelectedDifficulty == -1) {
                    Toast.makeText(getContext(), "Please select a difficulty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (inputPlayerName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please input a name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (inputPlayerName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getContext(), "Player name cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                b.putString("PlayerName", inputPlayerName.getText().toString().trim());
                b.putInt("Difficulty", SelectedDifficulty);
                b.putInt("PlayerCharacter", SelectedChar);
                FragmentHelper.getInstance().loadFragment(ScreenGame.class, b);
            }
        });
    }
}