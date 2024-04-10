package com.example.cs2340s1.Nouns;

import android.media.Image;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.cs2340s1.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Map {

    private ArrayList<ImageView> components;
    private Map next;
    private Map prev;

    public Map(ArrayList<ImageView> components) {
        this.components = components;
    }

    public void draw(View view) {
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.fragment_screen_level);
        for (ImageView drawable : this.components) {
            layout.addView(drawable);
        }
    }

    public void erase(View view) {
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.fragment_screen_level);
        for (ImageView drawable : this.components) {
            layout.removeView(drawable);
        }
    }

    public void setNextMap(Map next) {
        this.next = next;
    }

    public void setPrevMap(Map prev) {

        this.prev = prev;
    }

    public Map getNextMap() {
        return this.next;
    }

    public Map getPrevMap() {
        return this.prev;
    }
}
