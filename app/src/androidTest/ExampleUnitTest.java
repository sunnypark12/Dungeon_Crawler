package com.example.cs2340s1;

import org.junit.Test;

import static org.junit.Assert.*;

import android.widget.ImageView;

import com.example.cs2340s1.Nouns.Entity;
import com.example.cs2340s1.Nouns.Map;
import com.example.cs2340s1.Nouns.Player;

import com.example.cs2340s1.ViewModels.VMScreenGame;
import com.example.cs2340s1.factories.EntityFactory;
import com.example.cs2340s1.interfaces.Updateable;
import com.example.cs2340s1.util.gameDifficulty;
import com.example.cs2340s1.util.GameState;
import com.example.cs2340s1.util.Publisher;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void publisher_canSubscribe() {
        final int[] count = {0};
        Publisher publisher = new Publisher();
        Updateable subscriber = new Updateable() {
            @Override
            public void update() {
                count[0]++;
            }
        };

        publisher.addSubscriber(subscriber);
        publisher.ping();

        assertEquals(count[0], 1);
    }
    @Test
    public void publisher_canPingMultiple() {
        final int[] count = {0};
        Publisher publisher = new Publisher();
        Updateable subscriber = new Updateable() {
            @Override
            public void update() {
                count[0]++;
            }
        };

        publisher.addSubscriber(subscriber);
        publisher.addSubscriber(subscriber);
        publisher.ping();

        assertEquals(count[0], 2);
    }

    @Test
    public void publisher_canRemove() {
        final int[] count = {0};
        Publisher publisher = new Publisher();
        Updateable subscriber = new Updateable() {
            @Override
            public void update() {
                count[0]++;
            }
        };

        publisher.addSubscriber(subscriber);
        publisher.addSubscriber(subscriber);
        publisher.removeSubscriber(subscriber);
        publisher.ping();

        assertEquals(count[0], 1);
    }

    @Test
    public void bounds_doesNotClipRight() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");
        VMScreenGame.setWIDTH(100);
        vm.setPlayerPositionX(100 + 10);
        PublishKeyRight.addSubscriber(vm.handlerPlayerHitWall);
        PublishKeyRight.ping();

        assertTrue(vm.getPlayer().getX() < 100);
    }

    @Test
    public void bounds_doesNotClipLeft() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");
        VMScreenGame.setWIDTH(1080);
        vm.setPlayerPositionX(80 * -1);
        PublishKeyRight.addSubscriber(vm.handlerPlayerHitWall);
        PublishKeyRight.ping();

        assertTrue(vm.getPlayerPositionX(0) > 0);
    }

    @Test
    public void bounds_doesNotClipUp() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");
        vm.setPlayerPositionY(80 * -1);
        PublishKeyRight.addSubscriber(vm.handlerPlayerHitWall);
        PublishKeyRight.ping();

        assertTrue(vm.getPlayerPositionY(0) > 0);
    }

    @Test
    public void bounds_doesNotClipDown() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");
        vm.setPlayerPositionY(VMScreenGame.FLOOR + 10);
        PublishKeyRight.addSubscriber(vm.handlerPlayerHitWall);
        PublishKeyRight.ping();

        assertTrue(vm.getPlayer().getY() < VMScreenGame.FLOOR);
    }

    @Test
    public void testSetNextMap() {
        Map map1 = new Map(new ArrayList< ImageView >());
        Map map2 = new Map(new ArrayList< ImageView >());

        map1.setNextMap(map2);
        assertEquals(map2, map1.getNextMap());
    }

    @Test
    public void testSetPrevMap() {
        Map map1 = new Map(new ArrayList< ImageView >());
        Map map2 = new Map(new ArrayList< ImageView >());

        map2.setPrevMap(map1);
        assertEquals(map1, map2.getPrevMap());
    }

    @Test
    public void testNextPrevMapConsistency() {
        Map map1 = new Map(new ArrayList< ImageView >());
        Map map2 = new Map(new ArrayList< ImageView >());

        map1.setNextMap(map2);
        map2.setPrevMap(map1);

        assertEquals(map2, map1.getNextMap());
        assertEquals(map1, map2.getPrevMap());
        assertEquals(map1, map1.getNextMap().getPrevMap());
        assertEquals(map2, map2.getPrevMap().getNextMap());
    }

    @Test
    public void testSelfPointingMap() {
        Map map1 = new Map(new ArrayList< ImageView >());

        map1.setNextMap(map1);
        map1.setPrevMap(map1);

        assertEquals(map1, map1.getNextMap());
        assertEquals(map1, map1.getPrevMap());
    }

    @Test(expected = NullPointerException.class)
    public void testNullNextMap() {
        Map map1 = new Map(new ArrayList< ImageView >());
        map1.setNextMap(null);
        map1.getNextMap().getPrevMap();
    }

    @Test(expected = NullPointerException.class)
    public void testNullPrevMap() {
        Map map1 = new Map(new ArrayList< ImageView >());
        map1.setPrevMap(null);
        map1.getPrevMap().getNextMap();
    }

    @Test
    public void testInitialValues() {
        Player player = new Player("Player1");

        assertEquals("Player1", player.getName());
        assertEquals(10, player.getHP());
        assertEquals(2, player.getStrength());
        assertEquals(1, player.getShield());
        assertEquals(1, player.getSpeed());
        assertEquals(0, player.getMoney(), 0.001);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void testSetHP() {
        Player player = new Player("Player1");
        player.setHP(20);
        assertEquals(20, player.getHP());
    }

    @Test
    public void testSetStrength() {
        Player player = new Player("Player1");
        player.setStrength(3);
        assertEquals(3, player.getStrength());
    }

    @Test
    public void testSetShield() {
        Player player = new Player("Player1");
        player.setShield(2);
        assertEquals(2, player.getShield());
    }

    @Test
    public void testSetSpeed() {
        Player player = new Player("Player1");
        player.setSpeed(2);
        assertEquals(2, player.getSpeed());
    }

    @Test
    public void testSetMoney() {
        Player player = new Player("Player1");
        player.setMoney(100.50);
        assertEquals(100.50, player.getMoney(), 0.001);
    }

    @Test
    public void testSetName() {
        Player player = new Player("Player1");
        player.setName("Player2");
        assertEquals("Player2", player.getName());
    }

    @Test
    public void testSetLocation() {
        Player player = new Player("Player1");
        player.setX(5);
        player.setY(10);
        assertEquals(5, player.getX());
        assertEquals(10, player.getY());
    }

    @Test
    public void entity_create() {
        Entity e = EntityFactory.getEntity(0, 0, 10, 10, 0, 0);
        int expected[] = new int[]{0, 0, 10, 10, 0, 0};
        int results[] = new int[]{e.getX(), e.getY(), e.getHealth(), e.getSpeed(), e.getIdle_id(), e.getRun_id()};

        assertArrayEquals(expected, results);
    }

    @Test
    public void entity_different_objects() {
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        Entity f = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );

        assertNotEquals(e, f);
    }

    @Test
    public void entity_speed_related_movement() {
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        ((Updateable) e.getMoveRightSubscriber()).update();

        assertEquals(e.getX(), e.getSpeed());
    }

    @Test
    public void entity_collision_case_1() {
        GameState gs = new GameState(gameDifficulty.EASY, "");
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        e.setWidth(10);
        e.setHeight(10);

        gs.setEnemy1(e);
        gs.getPlayer().setX(5);
        gs.getPlayer().setY(5);
        gs.getPlayer().setWidth(10);
        gs.getPlayer().setHeight(10);
        gs.getPlayer().setHealth(100);

        gs.collisionObserver.update();
        assertTrue(gs.getPlayer().getHealth() < 100);
    }

    /**
     * This test will be used for testing out of bounds but still in
     * bounds condition (used for ensuring that our ImageView stuff
     * doesn't break).
     */
    @Test
    public void entity_collision_case_2() {
        GameState gs = new GameState(gameDifficulty.EASY, "");
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        e.setWidth(10);
        e.setHeight(10);

        gs.setEnemy1(e);
        gs.getPlayer().setX(5);
        gs.getPlayer().setY(20);
        gs.getPlayer().setWidth(10);
        gs.getPlayer().setHeight(10);
        gs.getPlayer().setHealth(100);

        gs.collisionObserver.update();
        assertTrue(gs.getPlayer().getHealth() < 100);
    }

    @Test
    public void entity_collision_case_3() {
        GameState gs = new GameState(gameDifficulty.EASY, "");
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        e.setWidth(10);
        e.setHeight(10);

        gs.setEnemy1(e);
        gs.getPlayer().setX(10);
        gs.getPlayer().setY(10);
        gs.getPlayer().setWidth(10);
        gs.getPlayer().setHeight(10);
        gs.getPlayer().setHealth(100);

        gs.collisionObserver.update();
        assertTrue(gs.getPlayer().getHealth() < 100);
    }

    @Test
    public void entity_collision_case_4() {
        GameState gs = new GameState(gameDifficulty.EASY, "");
        Entity e = EntityFactory.getEntity(
                0, 0, 10, 10, 0,0
        );
        e.setWidth(10);
        e.setHeight(10);

        gs.setEnemy1(e);
        gs.getPlayer().setX(-101);
        gs.getPlayer().setY(-101);
        gs.getPlayer().setWidth(10);
        gs.getPlayer().setHeight(10);
        gs.getPlayer().setHealth(100);

        gs.collisionObserver.update();
        assertTrue(gs.getPlayer().getHealth() == 100);
    }

    @Test
    public void vm_flushEnemies() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");


        vm.getGameState().setEnemy1(EntityFactory.getEntity(
                0, 0, 10, 10, 0, 0
        ));
        vm.getGameState().setEnemy2(EntityFactory.getEntity(
                0, 0, 10, 10, 0, 0
        ));

        vm.getGameState().clearEnemies();
        vm.flushEnemies();

        assertNull(vm.getGameState().getEnemy1());
        assertNull(vm.getGameState().getEnemy2());
    }

    @Test
    public void vm_initEnemies() {
        Publisher PublishKeyRight = new Publisher();
        VMScreenGame vm = new VMScreenGame();
        vm.setGameState(gameDifficulty.EASY, "NoName");


        vm.getGameState().setEnemy1(EntityFactory.getEntity(
                0, 0, 10, 10, 0, 0
        ));
        vm.getGameState().setEnemy2(EntityFactory.getEntity(
                0, 0, 10, 10, 0, 0
        ));
    }

}
