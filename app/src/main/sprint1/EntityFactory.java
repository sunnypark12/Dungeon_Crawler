package com.example.cs2340s1.factories;

import com.example.cs2340s1.Nouns.Entity;

public class EntityFactory {
    public static Entity getEntity(int x, int y, int health, int speed, int idle, int running) {
        Entity e = new Entity(x, y, health);
        e.setSpeed(speed);
        e.idle_id = idle;
        e.run_id = running;
        return e;
    }
}
