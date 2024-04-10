package com.example.cs2340s1.Nouns;

import com.example.cs2340s1.Nouns.UserPlayer;
import com.example.cs2340s1.interfaces.Controllable;
import com.example.cs2340s1.interfaces.Updateable;

public class Entity implements Controllable {
    private enum Direction {
        LEFT, RIGHT, UP, DOWN;
    }

    private int x, y, health;
    private int speed;
    public int idle_id;
    public int run_id;
    private int width, height;


    protected class MovementSubscriber implements Updateable {

        private Controllable target;
        private Direction type;
        public MovementSubscriber(Direction type, Controllable target) {
            this.target = target;
            this.type = type;
        }

        @Override
        public void update() {
            switch (type) {
                case DOWN:
                    target.down();
                    break;
                case RIGHT:
                    target.right();
                    break;
                case UP:
                    target.up();
                    break;
                case LEFT:
                    target.left();
                    break;
            }
        }
    }

    private MovementSubscriber MoveLeftSubscriber, MoveRightSubscriber, MoveUpSubscriber,
            MoveDownSubscriber;

    public Entity(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;

        MoveLeftSubscriber = new MovementSubscriber(Direction.LEFT, this);
        MoveRightSubscriber = new MovementSubscriber(Direction.RIGHT, this);
        MoveUpSubscriber = new MovementSubscriber(Direction.UP, this);
        MoveDownSubscriber = new MovementSubscriber(Direction.DOWN, this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public MovementSubscriber getMoveLeftSubscriber() {
        return MoveLeftSubscriber;
    }

    public MovementSubscriber getMoveRightSubscriber() {
        return MoveRightSubscriber;
    }

    public MovementSubscriber getMoveUpSubscriber() {
        return MoveUpSubscriber;
    }

    public MovementSubscriber getMoveDownSubscriber() {
        return MoveDownSubscriber;
    }

    @Override
    public void left() {
        this.x -= speed;
    }

    @Override
    public void right() {
        this.x += speed;
    }

    @Override
    public void up() {
        this.y -= speed;
    }

    @Override
    public void down() {
        this.y += speed;
    }

    public int getIdle_id() {
        return idle_id;
    }

    public void setIdle_id(int idle_id) {
        this.idle_id = idle_id;
    }

    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width - 312;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height - 150;
    }

    public void setMoveLeftSubscriber(MovementSubscriber moveLeftSubscriber) {
        MoveLeftSubscriber = moveLeftSubscriber;
    }

    public void setMoveRightSubscriber(MovementSubscriber moveRightSubscriber) {
        MoveRightSubscriber = moveRightSubscriber;
    }

    public void setMoveUpSubscriber(MovementSubscriber moveUpSubscriber) {
        MoveUpSubscriber = moveUpSubscriber;
    }

    public void setMoveDownSubscriber(MovementSubscriber moveDownSubscriber) {
        MoveDownSubscriber = moveDownSubscriber;
    }
}


