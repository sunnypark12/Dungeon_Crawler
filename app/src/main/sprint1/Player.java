package com.example.cs2340s1.Nouns;

public class Player {
    private String name;
    private int hp, strength, shield, speed;
    private double money;
    private int resource;
    private int x, y;

    public Player(String name) {
        this.name = name;
        hp = 10;
        strength = 2;
        shield = 1;
        speed = 1;
        money = 0;
        x = y = 0;
    }

    public String getName() {return name;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getHP() {
        return hp;
    }
    public int getStrength() {
        return strength;
    }
    public int getShield() {
        return shield;
    }
    public int getSpeed() {
        return speed;
    }
    public double getMoney() {
        return money;
    }
    public void setHP(int hp) {
        this.hp = hp;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setShield(int shield) {
        this.shield = shield;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public void setResource(int res) {
        this.resource = res;
    }
    public void setName(String name) { this.name = name; }

    public int setX(int x) {
        this.x = x;
        return x;
    }

    public int setY(int y) {
        this.y = y;
        return y;
    }
}
