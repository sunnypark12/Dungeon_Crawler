package com.example.cs2340s1.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cs2340s1.R;

import java.util.HashMap;

public class FragmentHelper {
    private static FragmentHelper singleton;
    private AppCompatActivity compat;
    private int ContainerId;
    private int ManagedFragments;
    private static FragmentTransaction Transaction;

    public FragmentHelper() {
        // this is required to set the singleton to be not-null!
    }

    /**
     * This is the constructor for the FragmentHelper and takes in a fragment id. With
     * this fragment ID, we can manipulate how we deal with a specific fragment!
     * @param ContainerId the id assigned to the fragment.
     */
    private FragmentHelper(int ContainerId, AppCompatActivity Compat) {
        this.ManagedFragments = 0;
        this.ContainerId = ContainerId;
        this.compat = Compat;
    }

    public void AddTransitionObject(TextView view, Class<? extends Fragment> destination, Bundle b) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleton.loadFragment(destination, b);
            }
        });
    }

    /**
     * Will load the main activity with a fragment given a Fragment class (or
     * sub-class), and will pass in a bundle if one is required.
     *
     * Minor optimizations were made by replacing if we are already managing
     * a fragment. There is potential for re-using fragments (i.e., not
     * repopulating the end or main menu screens - but this can be introduced
     * later).
     * @param f Class that extends from fragment.
     * @param bundle Data passing object used in Android, look up for more info.
     */
    public void loadFragment(Class<? extends Fragment> f, Bundle bundle) {
        Transaction = singleton.compat.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true);
        if (ManagedFragments++ == 0) {
            Transaction.add(ContainerId, f, bundle);
        } else {
            Transaction.replace(ContainerId, f, bundle);
        }
        Transaction.commit();
    }


    public static synchronized FragmentHelper getInstance(int ContainerId, AppCompatActivity Compat) {
        if (singleton == null) {
            singleton = new FragmentHelper(ContainerId, Compat);
        }

        return singleton;
    }

    public static synchronized FragmentHelper getInstance() {
        if (singleton == null)
            throw new RuntimeException("No fragment helper to provide!");

        return singleton;
    }
}
