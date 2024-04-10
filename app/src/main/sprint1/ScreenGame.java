package com.example.cs2340s1;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.example.cs2340s1.Nouns.Entity;
import com.example.cs2340s1.Nouns.Map;
import com.example.cs2340s1.ViewModels.VMScreenGame;
import com.example.cs2340s1.helpers.MapLoader;
import com.example.cs2340s1.interfaces.Updateable;
import com.example.cs2340s1.navigation.FragmentHelper;
import com.example.cs2340s1.navigation.NewView;
import com.example.cs2340s1.util.gameDifficulty;
import com.example.cs2340s1.util.Publisher;

import java.util.Timer;
import java.util.TimerTask;

public class ScreenGame extends NewView {

    private TextView TextViewPlayerName, TextViewDifficulty, TextViewHealth, TextViewLiveScore;
    private ImageView PlayerDrawable, EnemyDrawable1, EnemyDrawable2;
    private ImageView[] EnemyDrawables;
    private Button EndGameButton;
    private static VMScreenGame vm;
    private static int idle_id;
    private static int run_id;
    private static String currentAnimation;
    private static int lastRunning = 0;
    private WindowMetrics display;
    private Timer timer;
    private Handler handler;
    private Map currentMap;
    private static int frame_id = 0;

    private int WIDTH;
    private int HEIGHT;

    private Publisher PlayerHitRightWall, PlayerKeyDown,
            PlayerKeyUp, PlayerKeyLeft, PlayerKeyRight;
    public Publisher playerHealthUpdatePublisher = new Publisher(); // instantiated new publisher

    public static VMScreenGame getVm() {
        return vm;
    }

    public class WallHitObserver implements Updateable {
        @Override
        public void update() {
            currentMap.erase(getView());
            currentMap = currentMap.getNextMap();
            if (currentMap == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isWin", true);
                fh.loadFragment(ScreenEnd.class, bundle);
            } else {
                currentMap.draw(getView());
                /* this is required because of concurrent modifications to the PlayerKeyRight stuff */
                initNewEnemies();
            }


        }
    }
    public ScreenGame() {
        super(ScreenGame.class, R.layout.fragment_screen_level, false);
        vm = new VMScreenGame();
    }

    private void setDisplayMetrics(FragmentActivity activity) {
        display = activity.getWindow().getWindowManager().getCurrentWindowMetrics();
        WIDTH = display.getBounds().width();
        HEIGHT = display.getBounds().height();
    }

    private void initMapLoader(View view) {
        MapLoader mapLoader = new MapLoader(getResources(), getActivity());
        currentMap = mapLoader.generateGameMap();
        currentMap.draw(view);
    }

    private void refreshEnemyDrawables() {
        for (int i = 0; i < vm.getEnemies().length; i++) {
            Entity e = vm.getEnemies()[i];
            EnemyDrawables[i].setImageResource(e.run_id);
            EnemyDrawables[i].setX(e.getX());
            EnemyDrawables[i].setY(e.getY());
            e.setWidth(EnemyDrawables[i].getWidth());
            e.setHeight(EnemyDrawables[i].getHeight());
        }
    }

    private void initOnScreenText(Bundle b) {
        String playerName = b.getString("PlayerName");

        int difficulty = b.getInt("Difficulty");
        int chosenCharacter = b.getInt("PlayerCharacter");

        gameDifficulty gd = gameDifficulty.EASY;
        switch (difficulty) {
            case 1:
                gd = gameDifficulty.MEDIUM;
                break;
            case 2:
                gd = gameDifficulty.HARD;
                break;
        }
        vm.setGameState(gd, playerName);
        vm.getPlayer().idle_id = R.drawable.sprite1_idle;
        vm.getPlayer().run_id = R.drawable.sprite1_run;

        switch (chosenCharacter) {
            case 1:
                vm.getPlayer().idle_id = R.drawable.sprite2_idle;
                vm.getPlayer().run_id = R.drawable.sprite2_run;
                break;
            case 2:
                vm.getPlayer().idle_id = R.drawable.sprite3_idle;
                vm.getPlayer().run_id = R.drawable.sprite3_run;
                break;
        }


        // set the screen to vars
        TextViewPlayerName.setText(playerName);
        TextViewDifficulty.setText(gd.name());
        TextViewHealth.setText(Integer.toString(vm.getPlayerHealth()));
        PlayerDrawable.setImageResource(vm.getPlayer().idle_id);
        currentAnimation = "idle";
    }

    private void initPublishers() {
        PlayerKeyDown = new Publisher();
        PlayerKeyUp = new Publisher();
        PlayerKeyLeft = new Publisher();
        PlayerKeyRight = new Publisher();
        PlayerHitRightWall = new Publisher();
        playerHealthUpdatePublisher = new Publisher(); // initialized new Publisher to handle updates

    }

    private void initNewEnemies() {
        // we first flush
        vm.flushEnemies();
        for (int i = 0; i < vm.getEnemies().length; i++) {
            Entity entity = vm.getEnemies()[i];

            PlayerKeyRight.addSubscriber(new VMScreenGame.HandlerPlayerHitWall(entity, false));
            PlayerKeyLeft.addSubscriber(new VMScreenGame.HandlerPlayerHitWall(entity, false));
            PlayerKeyUp.addSubscriber(new VMScreenGame.HandlerPlayerHitWall(entity, false));
            PlayerKeyDown.addSubscriber(new VMScreenGame.HandlerPlayerHitWall(entity, false));

            PlayerKeyRight.addSubscriber(entity.getMoveRightSubscriber());
            PlayerKeyDown.addSubscriber(entity.getMoveDownSubscriber());
            PlayerKeyUp.addSubscriber(entity.getMoveUpSubscriber());
            PlayerKeyLeft.addSubscriber(entity.getMoveLeftSubscriber());

            entity.setX((int) (Math.random() * WIDTH));
            entity.setY(Math.min((int) (Math.random() * VMScreenGame.FLOOR), VMScreenGame.ROOF));

        }

        refreshEnemyDrawables();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /* grab the ui elements and initialize them */
        TextViewPlayerName = view.findViewById(R.id.TvPlayerName);
        TextViewDifficulty = view.findViewById(R.id.TvDifficulty);
        TextViewHealth = view.findViewById(R.id.TvHealth);
        PlayerDrawable = view.findViewById(R.id.ImgViewPlayer);
        TextViewLiveScore = view.findViewById(R.id.tvScoreLive);
        EnemyDrawable1 = view.findViewById(R.id.ImgViewEnemy);
        EnemyDrawable2 = view.findViewById(R.id.ImgViewEnemy2);
        EnemyDrawables = new ImageView[]{EnemyDrawable1, EnemyDrawable2};

        /* initialization */
        setDisplayMetrics(getActivity());

        Bundle b = getArguments();
        initOnScreenText(b);
        initMapLoader(view);
        initPublishers();
        //playerHealthUpdatePublisher = new Publisher(); // initialized new Publisher to handle updates

        /* VM Init */
        getVm().setPlayerPositionX((int) WIDTH/3);
        getVm().setPlayerPositionY((int) HEIGHT/3);
        VMScreenGame.setWIDTH(WIDTH);
        getVm().subscribeToWallHit(new WallHitObserver());

        playerHealthUpdatePublisher.addSubscriber(new Updateable() {
            @Override
            public void update() {
                // TODO: Implement this when we have a text associated with health
                  //TextView.setText("Health: " + vm.getPlayerHealth());
                System.out.println(vm.getPlayerHealth());
                if (vm.getPlayerHealth() <= 0) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isWin", false);
                    FragmentHelper.getInstance().loadFragment(ScreenEnd.class, bundle);
                }
            }
        });

        /* register the handlers */
        PlayerKeyDown.addSubscriber(getVm().getPlayer().getMoveDownSubscriber());
        PlayerKeyUp.addSubscriber(getVm().getPlayer().getMoveUpSubscriber());
        PlayerKeyLeft.addSubscriber(getVm().getPlayer().getMoveLeftSubscriber());
        PlayerKeyRight.addSubscriber(getVm().getPlayer().getMoveRightSubscriber());
        //playerHealthUpdatePublisher.addSubscriber(getVm().getCollisionObserver());

        PlayerKeyRight.addSubscriber(vm.handlerPlayerHitWall);
        PlayerKeyLeft.addSubscriber(vm.handlerPlayerHitWall);
        PlayerKeyUp.addSubscriber(vm.handlerPlayerHitWall);
        PlayerKeyDown.addSubscriber(vm.handlerPlayerHitWall);
        //playerHealthUpdatePublisher.addSubscriber(vm.getGameState().collisionObserver);

        PlayerKeyRight.addSubscriber(vm.getCollisionObserver());
        PlayerKeyLeft.addSubscriber(vm.getCollisionObserver());
        PlayerKeyUp.addSubscriber(vm.getCollisionObserver());
        PlayerKeyDown.addSubscriber(vm.getCollisionObserver());
        //playerHealthUpdatePublisher.addSubscriber(vm.getCollisionObserver());

        initNewEnemies();
        for (Entity entity : vm.getEnemies()) {
            PlayerKeyRight.addSubscriber(new VMScreenGame.HandlerPlayerHitWall(entity, false));
            PlayerKeyRight.addSubscriber(entity.getMoveRightSubscriber());

        }
        refreshEnemyDrawables();

        /* code to get the keyboard input to work */
        getView().setFocusedByDefault(true);
        getView().setFocusable(true);
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                ScreenGame.lastRunning = 0;
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (ScreenGame.currentAnimation.equals("idle")) {
                            PlayerDrawable.setImageResource(getVm().getPlayer().run_id);
                            ScreenGame.currentAnimation = "run";
                        }
                        PlayerKeyDown.ping();
                        if (vm.getGameState().getIsHit()) {
                            playerHealthUpdatePublisher.ping();
                            vm.getGameState().setIsHit(false);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        PlayerDrawable.setScaleX(-1);

                        for (ImageView villainDrawable : EnemyDrawables) {
                            villainDrawable.setScaleX(-1);
                        }

                        EnemyDrawable1.setScaleX(-1);


                        if (ScreenGame.currentAnimation.equals("idle")) {
                            PlayerDrawable.setImageResource(getVm().getPlayer().run_id);
                            ScreenGame.currentAnimation = "run";
                        }
                        PlayerKeyLeft.ping();
                        if (vm.getGameState().getIsHit()) {
                            playerHealthUpdatePublisher.ping();
                            vm.getGameState().setIsHit(false);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        PlayerDrawable.setScaleX(1);

                        for (ImageView villainDrawable : EnemyDrawables) {
                            villainDrawable.setScaleX(1);
                        }

                        if (ScreenGame.currentAnimation.equals("idle")) {
                            PlayerDrawable.setImageResource(getVm().getPlayer().run_id);
                            ScreenGame.currentAnimation = "run";
                        }
                        PlayerKeyRight.ping();
                        if (vm.getGameState().getIsHit()) {
                            playerHealthUpdatePublisher.ping();
                            vm.getGameState().setIsHit(false);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (ScreenGame.currentAnimation.equals("idle")) {
                            PlayerDrawable.setImageResource(getVm().getPlayer().run_id);
                            ScreenGame.currentAnimation = "run";
                        }
                        PlayerKeyUp.ping();
                        if (vm.getGameState().getIsHit()) {
                            playerHealthUpdatePublisher.ping();
                            vm.getGameState().setIsHit(false);
                        }
                        break;
                    default:
                        break;
                }
              
                PlayerDrawable.setX(getVm().getPlayerPositionX(0));
                PlayerDrawable.setY(getVm().getPlayerPositionY(0));

                refreshEnemyDrawables();
                return true;
              
            }
        });

        vm.getGameState().startTimer(); // to initialize the timer for the time based score
        handler = new Handler(Looper.getMainLooper());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        vm.getGameState().calculateTimeBasedScore();
                        TextViewLiveScore.setText("Score: " + vm.getGameState().getScore()); // changed this to get the score from the VM
                        TextViewHealth.setText("Health: " + getVm().getPlayerHealth());
                    }
                });

                ++ScreenGame.frame_id;
                int playerFrame = ScreenGame.frame_id % ((AnimationDrawable) PlayerDrawable.getDrawable()).getNumberOfFrames();
                ((AnimationDrawable) PlayerDrawable.getDrawable()).selectDrawable(playerFrame);

                for (ImageView villainDrawable : EnemyDrawables) {
                    int villainFrame = ScreenGame.frame_id % ((AnimationDrawable) villainDrawable.getDrawable()).getNumberOfFrames();
                    ((AnimationDrawable) villainDrawable.getDrawable()).selectDrawable(villainFrame);
                }

                if (ScreenGame.currentAnimation.equals("run")) {
                    ScreenGame.lastRunning += 1;
                    if (ScreenGame.lastRunning >= 2) {
                        PlayerDrawable.setImageResource(vm.getPlayer().idle_id);
                        ScreenGame.currentAnimation = "idle";
                        ScreenGame.lastRunning = 0;
                    }
                }

            }
        }, 1000, 200); // Update score every 1000 milliseconds (1 second)

    }
    
    private static void UpdateEntityPlayerDrawable(ImageView imageView, Entity entity, int res) {
        imageView.setY(entity.getY());
        imageView.setX(entity.getX());
        imageView.setImageResource(res);
    }
    
//    public void onPlayerHealthChange(int healthDelta) {
        // TODO: Implement when we determine how things will effect the player health
//        vm.updatePlayerHealth(healthDelta);
//        playerHealthUpdatePublisher.ping(); // will notify the publisher that the players health changed
//    }
}