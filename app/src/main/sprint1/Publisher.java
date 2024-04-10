package com.example.cs2340s1.util;

import androidx.appcompat.view.menu.SubMenuBuilder;

import com.example.cs2340s1.Nouns.Entity;
import com.example.cs2340s1.interfaces.Updateable;

import java.util.ArrayList;
import java.util.Iterator;

public class Publisher {
    private ArrayList<Updateable> subscribers;
    public Publisher() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(Updateable subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Updateable subscriber) {
        subscribers.remove(subscriber);
    }

    public void ping() {
        int count = 0;

        while (count < subscribers.size()) {
            subscribers.get(count++).update();
        }
    }
}
