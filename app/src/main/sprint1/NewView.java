package com.example.cs2340s1.navigation;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cs2340s1.ComponentBlank;
import com.example.cs2340s1.ComponentNavbar;
import com.example.cs2340s1.R;

public abstract class NewView extends Fragment {
    protected static FragmentHelper fh;
    private Class<? extends Fragment> source;
    private boolean showNavbar;
    private final int resource;

    public NewView(Class<? extends Fragment> source, int resource, boolean showNavbar) {
        this.source = source;
        this.showNavbar = showNavbar;
        this.resource = resource;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fh = FragmentHelper.getInstance();
        return inflater.inflate(resource, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fh = FragmentHelper.getInstance();

        if (showNavbar) {
            getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.FragContNav, ComponentNavbar.class, null)
                .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FragContNav, ComponentBlank.class, null)
                    .commit();
        }
    }

    @Override
    public abstract void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);
}
