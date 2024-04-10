package com.example.cs2340s1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cs2340s1.navigation.FragmentHelper;

public class ComponentNavbar extends Fragment {

    private FragmentHelper fh;
    private Button BtnMainMenuNav;

    // This function is required, don't change unless you know what you are doing.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navbar_primary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fh = FragmentHelper.getInstance();

        BtnMainMenuNav = getActivity().findViewById(R.id.BtnNavBack);
        fh.AddTransitionObject(BtnMainMenuNav, ScreenMainMenu.class, null);
    }
}
