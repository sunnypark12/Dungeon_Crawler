package com.example.cs2340s1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.cs2340s1.navigation.FragmentHelper;
import com.example.cs2340s1.navigation.NewView;

public class ScreenInstruction extends NewView {

    private FragmentHelper fh;

    public ScreenInstruction() {
        super(ScreenInstruction.class, R.layout.fragment_screen_instructions, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fh = FragmentHelper.getInstance();
    }
}
