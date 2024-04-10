package com.example.cs2340s1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;

import com.example.cs2340s1.navigation.FragmentHelper;
import com.example.cs2340s1.navigation.NewView;

public class ScreenMainMenu extends NewView {
    private Button BtnPlay, BtnInstructions, BtnQuit;

    public ScreenMainMenu() {
        super(ScreenMainMenu.class, R.layout.activity_start, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        // create the instance handler
        fh = FragmentHelper.getInstance();

        BtnPlay = getActivity().findViewById(R.id.BtnPlay);
        BtnInstructions = getActivity().findViewById(R.id.BtnInstructions);
        BtnQuit = getActivity().findViewById(R.id.BtnQuit);

        fh.AddTransitionObject(BtnPlay, ScreenModifyPlayer.class, null);
        fh.AddTransitionObject(BtnInstructions, ScreenInstruction.class, null);
        BtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}