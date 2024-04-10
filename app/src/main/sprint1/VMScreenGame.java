package com.example.cs2340s1.ViewModels;

import com.example.cs2340s1.Nouns.Entity;
import com.example.cs2340s1.interfaces.Updateable;
import com.example.cs2340s1.util.gameDifficulty;
import com.example.cs2340s1.util.GameState;
import com.example.cs2340s1.util.Publisher;


public class VMScreenGame {

    private static final int DELTA = 30;
    public static final int FLOOR = 1400;
    public static final int ROOF = 700;
    private static int WIDTH, HEIGHT;
    private static GameState gs;
    public static Publisher PublishRightWallHit;
    public HandlerPlayerHitWall handlerPlayerHitWall;

    public static class HandlerPlayerHitWall implements Updateable {
        Entity e;
        private boolean shouldPing = true;
        public HandlerPlayerHitWall(Entity e) {
            this(e, true);
        }

        public HandlerPlayerHitWall(Entity e, boolean shouldPing) {
            this.e = e;
            this.shouldPing = shouldPing;
        }

        @Override
        public void update() {
            int width = e.getWidth();
            int height = e.getHeight();
            int x = e.getX();
            int y = e.getY();
            int speed = e.getSpeed();

            if (x < speed) {
                e.setX(speed);
            } else if (x + width > WIDTH) {
                if (shouldPing)
                    PublishRightWallHit.ping();
                e.setX(speed);
            }
            if (y < ROOF) {
                e.setY(ROOF + e.getSpeed());
            } else if (y + height > FLOOR) {
                e.setY(FLOOR - height - speed);
            }
        }
    }

    public void setGameState(gameDifficulty gd, String name) {
        gs = new GameState(gd, name);

        PublishRightWallHit = new Publisher();
        handlerPlayerHitWall = new HandlerPlayerHitWall(getPlayer());
    }
    public void subscribeToWallHit(Updateable u) {
        PublishRightWallHit.addSubscriber(u);
    }

    public static void setWIDTH(int width) {
        WIDTH = width;
    }
    public int getPlayerHealth() {
        return gs.getPlayer().getHealth();
    }

    public Entity getPlayer() {
        return gs.getPlayer();
    }
    public Entity[] getEnemies() {
        return new Entity[]{gs.getEnemy1(), gs.getEnemy2()};
    }

    public void flushEnemies() {
        gs.setEnemy1(null);
        gs.setEnemy2(null);

        while (gs.getEnemies().size() > 0 && (gs.getEnemy1() == null || gs.getEnemy2() == null)) {
            if (gs.getEnemy1() == null) {
                gs.setEnemy1(gs.getEnemies().remove(0));
            } else {
                gs.setEnemy2(gs.getEnemies().remove(0));
            }
        }
    }

    // TODO: Update this with new score mechanics
    public int getScore() {
        GameState.LeaderboardInformation latest = GameState.getLatestAttempt();
        return latest.getNumScore();
    }

    public void onEnemyDestroyed(Entity enemy) {
        gs.onEnemyDestroyed(enemy);
    }

    public void calculateTimeBasedScore() {
        gs.calculateTimeBasedScore();
    }

    public void updateAliveScore() {
        gs.updateAliveScore();
    }

    public GameState getGameState() {
        return gs;
    }

    public int getPlayerPositionX(int delta) {
        return gs.getPlayer().getX() + delta;
    }

    public int getPlayerPositionY(int delta) {
        return gs.getPlayer().getY() + delta;
    }

    public void setPlayerPositionX(int delta) {
        gs.getPlayer().setX(getPlayerPositionX(0) + delta);
    }

    public void setPlayerPositionY(int delta) {
        gs.getPlayer().setY(getPlayerPositionY(0) + delta);
    }

    public GameState.CollisionObserver getCollisionObserver() {
        return gs.collisionObserver;
    }
}


