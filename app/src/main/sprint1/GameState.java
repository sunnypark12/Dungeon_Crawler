package com.example.cs2340s1.util;

import com.example.cs2340s1.Nouns.Entity;
import com.example.cs2340s1.Nouns.Player;
import com.example.cs2340s1.R;
import com.example.cs2340s1.factories.EntityFactory;
import com.example.cs2340s1.interfaces.Updateable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameState {
    public CollisionObserver collisionObserver;

    private boolean isHit = false;
    private int score = 0;
    public class CollisionObserver implements Updateable {
        @Override
        public void update() {
            for (Entity e : new Entity[]{enemy1, enemy2}) {
                if (e == null)
                    continue;
                // boundary calculations
                boolean xwithin = isWithin(getPlayer().getX(), e.getX());
                boolean ywithin = isWithin(getPlayer().getY(), e.getY());
                System.out.println(123);
                if (xwithin && ywithin) {
                    int healthBeforeCollision = getPlayer().getHealth();
                    getPlayer().setHealth(getPlayer().getHealth() - 1 * difficulty.ordinal());
                    int healthAfterCollision = getPlayer().getHealth();
                    int healthLost = healthBeforeCollision - healthAfterCollision;
                    isHit = true;
                    System.out.println(getPlayer().getHealth());

                    final int POINTS_LOST_FOR_HEALTH_LOSS = 10;
                    deductScore(healthLost * POINTS_LOST_FOR_HEALTH_LOSS);
                }

            }
        }

        public boolean isWithin(int n, int range) {
            if (Math.abs(range - n) <= 100) {
                return true;
            }
            return false;
        }
    }
    public void clearEnemies() {
        enemies.clear();
    }
    public boolean getIsHit() {
        return isHit;
    }
    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    // TODO: Update leaderboard with new score mechanics
    public class LeaderboardInformation {
        private String name;
        private String score;
        private String date;
        private int nscore;

        public LeaderboardInformation(String name, int score) {
            this.name = name;
            this.nscore = score;
            this.score = Integer.toString(score);
            this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }


        public String getDate() {
            return date;
        }
        public String getName() {
            return name;
        }

        public String getScore() {
            return Integer.toString(nscore);
        }
        public int getIntScore(){return nscore;}

        public boolean isLess(int score) {
            return score < nscore;
        }

        public int getNumScore() {
            this.nscore ++;
            return this.nscore;
        }
    }

    /**
     * Constants for the GameState
     */
    private static final int EASY_HP = 100;
    private static final int MEDIUM_HP = 50;
    private static final int HARD_HP = 20;
    private static final int ENEMY_COUNT = 5;
    private static LeaderboardInformation[] Attempts;
    private static int AttemptCount = 0;
    private static int CurrentAttemptIndex = 0;
    /**
     * GameState Variables
     */
    private gameDifficulty difficulty;

    private int MaxEnemyHealth;

    private Entity Player;
    private ArrayList<Entity> enemies;
    private Entity enemy1;
    private Entity enemy2;
    private int MaxPlayerHealth;


    public void addEnemy(Entity e) {
        enemies.add(e);
    }

    public Entity getEnemy1() {
        return enemy1;
    }

    public Entity getEnemy2() {
        return enemy2;
    }

    public void setEnemy1(Entity enemy1) {
        this.enemy1 = enemy1;
    }

    public void setEnemy2(Entity enemy2) {
        this.enemy2 = enemy2;
    }

    public GameState(gameDifficulty difficulty, String PlayerName) {
        this.difficulty = difficulty;
        enemies = new ArrayList<>();

        int HP = 0;
        switch (difficulty) {
            case EASY:
                HP = EASY_HP;
                break;
            case MEDIUM:
                HP = MEDIUM_HP;
                break;
            case HARD:
                HP = HARD_HP;
                break;
        }

        MaxPlayerHealth = HP;
        MaxEnemyHealth = MaxPlayerHealth / 2;
        if (Attempts == null) {
            Attempts = new LeaderboardInformation[5];
        }

        Player = EntityFactory.getEntity(0, 0, MaxEnemyHealth, 30, 0, 0);
        addAttempt(new LeaderboardInformation(PlayerName, (int)(100 * Math.random()) ));

        collisionObserver = new CollisionObserver();

        /* init the enemies */
        Entity e1 = CreateEnemy(0, 0, 0, 20, R.drawable.villain1_000, R.drawable.villain1_walk);
        Entity e2 = CreateEnemy(0, 0, 0, 30, R.drawable.villain2_000, R.drawable.villain2_walk);
        Entity e3 = CreateEnemy(0, 0, 0, 40, R.drawable.villain3_000, R.drawable.villain3_walk);
        Entity e4 = CreateEnemy(0, 0, 0, 3, R.drawable.villain4_000, R.drawable.villain4_walk);
        Entity e5 = CreateEnemy(0, 0, 0, 10, R.drawable.villain1_000, R.drawable.villain1_walk);
        Entity e6 = CreateEnemy(0, 0, 0, 20, R.drawable.villain2_000, R.drawable.villain2_walk);

        addEnemy(e1);
        addEnemy(e2);
        addEnemy(e3);
        addEnemy(e4);
        addEnemy(e5);
        addEnemy(e6);
    }

    public Entity getPlayer() {
        return Player;
    }


    /**
     * Should return a player that is already set-up!
     * @param PlayerName name of player.
     * @param hp the hit points.
     */
    private Player CreatePlayer(String PlayerName, int hp, int resource) {
        //TODO: Implement this method. What we expect:
        //      - When this method is called, we expect a player to be created with name and health.
        //      - Other variables like speed, position, etc can be set to defaults for now.
        Player p = new Player(PlayerName);
        p.setHP(hp);
        p.setResource(resource);

        addAttempt(new LeaderboardInformation(PlayerName, (int)(100 * Math.random()) ));

        return p;
    }
        public Entity CreateEnemy(int x, int y, int health, int speed, int idle, int running) {
        // TODO: Implement this method. What we expect:
        //      - Will create an enemy and place them randomly.
        return EntityFactory.getEntity(x, y, health, speed, idle, running);
    }

    public static LeaderboardInformation[] getAttempts() {
        return Attempts;
    }

    public void addAttempt(LeaderboardInformation l) {

        LeaderboardInformation stmp = null;
        LeaderboardInformation tmp = null;
        for (int i = 0; i < Attempts.length; i++) {
            if (Attempts[i] == null && tmp == null) {
                Attempts[i] = l;
                CurrentAttemptIndex = i;
                return;
            } else if (tmp == null && !Attempts[i].isLess(l.getNumScore())) {
                tmp = Attempts[i];
                Attempts[i] = l;
                CurrentAttemptIndex = i;
            } else if (tmp != null) {
                stmp = Attempts[i];
                Attempts[i] = tmp;
                tmp = stmp;

                if (i+1 < Attempts.length && Attempts[i + 1] == null) {
                    Attempts[i + 1] = tmp;
                    return;
                }
            }


        }

        incrementCount();
    }

    public static LeaderboardInformation getLatestAttempt() {
        return Attempts[CurrentAttemptIndex];
    }
    private static void incrementCount() {
        AttemptCount = (AttemptCount + 1) % Attempts.length;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

    public void addScore(int points) {
        score += points;
    }

    public void deductScore(int points) {
        score -= points;
    }

    public int getScore() {
        return score;
    }

    public void onEnemyDestroyed(Entity enemy) {
        final int POINTS_FOR_ENEMY = 100; // how many points awarded for destroying enemy
        addScore(POINTS_FOR_ENEMY);
        // TODO: may need additional logic here to remove the enemy?
    }

    /**
     * This are for the time based score implementation
     */
    private long startTime;
    private final int TIME_BONUS_THRESHOLD =  60000; // milliseconds
    private final int TIME_BONUS_SCORE = 500;
    private final int ALIVE_BONUS_SCORE = 10;
    private final int ALIVE_BONUS_INTERVAL = 1000; // milliseconds
    private long lastScoreUpdateTime;
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void calculateTimeBasedScore() {
        /* changed to just this as this code is run every second anyways */
        addScore(TIME_BONUS_SCORE);
    }

    public void updateAliveScore() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastScoreUpdateTime >= ALIVE_BONUS_INTERVAL) {
            addScore(ALIVE_BONUS_SCORE);
            lastScoreUpdateTime = currentTime;
        }
    }

}
