package com.example.cs2340s1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.cs2340s1.navigation.FragmentHelper;

public class MainActivity extends AppCompatActivity {

    // FragmentHelper is the class that we are using to help us populate the screen as
    // needed.
    private FragmentHelper fh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // this will setup the fragment helper and also load in the initial
        // fragment with the class passed in to the "load fragment" method!
        fh = FragmentHelper.getInstance(R.id.fragment_activity_main, this); // call getInstance if you need a FragmentHelper!
        fh.loadFragment(ScreenMainMenu.class, null); // loadFragment will populate the screen with the fragment!


    }
}