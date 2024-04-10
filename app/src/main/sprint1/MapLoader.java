package com.example.cs2340s1.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.example.cs2340s1.Nouns.Map;
import com.example.cs2340s1.R;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MapLoader {

    private Resources resources;
    private FragmentActivity activity;

    private final Rect  defaultWall = new Rect(0, 11, 48, 42);
    private final Rect  defaultCol = new Rect(48, 11, 64, 55);
    private final Rect redCol = new Rect(32, 235, 32+16, 235+44);
    private final Rect blueCol = new Rect(80, 235, 80+16, 235+44);
    private final Rect greenCol = new Rect(128, 235, 128+16, 235+44);
    private final Rect floor = new Rect(71, 8, 103, 40);
    private final Rect bloodFloor = new Rect(128, 192, 160, 224);



    public MapLoader(Resources resources, FragmentActivity activity) {
        this.resources = resources;
        this.activity = activity;
    }

    public Map generateGameMap() {
        Map level1 = new Map(generateMap1());
        Map level2 = new Map(generateMap2());
        Map level3 = new Map(generateMap3());

        level1.setNextMap(level2);
        level2.setNextMap(level3);
        level3.setPrevMap(level2);
        level2.setPrevMap(level1);

        return level1;
    }

    private ImageView createTile(Rect rect) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.tiles, opts);

        // Add objects from tile
        ImageView imageView = new ImageView(activity);
        imageView.setId(View.generateViewId());

        Bitmap bitmapPortion = Bitmap.createBitmap(bitmap,
                rect.left,
                rect.top,
                rect.width(),
                rect.height());

        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmapPortion);
        bitmapDrawable.getPaint().setFilterBitmap(false);
        imageView.setImageDrawable(bitmapDrawable);
        return imageView;
    }

    private ImageView createRepeatedTile(Rect rect, int horizontalReps, int verticalReps) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.tiles, opts);

        Bitmap bitmapPortion = Bitmap.createBitmap(bitmap,
            rect.left,
            rect.top,
            rect.width(),
            rect.height()
        );

        // Create a new bitmap with width equals to width of bitmapPortion times 3
        Bitmap tiledBitmap = Bitmap.createBitmap(
                bitmapPortion.getWidth() * horizontalReps,
                bitmapPortion.getHeight() * verticalReps,
                bitmapPortion.getConfig()
        );

        Canvas canvas = new Canvas(tiledBitmap);

        // loop to draw the original bitmap at different x positions 3 times on the new bitmap.
        for(int i = 0; i < horizontalReps; i++) {
            for (int j = 0; j < verticalReps; j++) {
                Rect dest = new Rect(
                        rect.width()*i,
                        rect.height()*j,
                        rect.width()*(i+1),
                        rect.height()*(j+1)
                );
                canvas.drawBitmap(bitmap, rect, dest, null);
            }
        }

        ImageView imageView = new ImageView(activity);
        imageView.setId(View.generateViewId());

        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, tiledBitmap);
        bitmapDrawable.getPaint().setFilterBitmap(false);
        imageView.setImageDrawable(bitmapDrawable);

        return imageView;
    }

    private ArrayList<ImageView> generateMap1() {
        ArrayList<ImageView> components = new ArrayList<ImageView>();

        ImageView wall1 = createTile(defaultWall);
        wall1.setScaleX(0.5f);
        wall1.setScaleY(0.5f);
        wall1.setX(-361-350);
        wall1.setY(-150-75);
        wall1.setZ(-1);
        components.add(wall1);

        ImageView wall2 = createTile(defaultCol);
        wall2.setScaleX(0.25f);
        wall2.setScaleY(0.25f);
        wall2.setX(0-350);
        wall2.setY(43-150-75);
        wall2.setZ(-1);
        components.add(wall2);

        ImageView wall3 = createTile(defaultWall);
        wall3.setScaleX(0.5f);
        wall3.setScaleY(0.5f);
        wall3.setX(-361+722-350);
        wall3.setY(-150-75);
        wall3.setZ(-1);
        components.add(wall3);

        ImageView wall4 = createTile(defaultCol);
        wall4.setScaleX(0.25f);
        wall4.setScaleY(0.25f);
        wall4.setX(722-350);
        wall4.setY(43 - 150-75);
        wall4.setZ(-1);
        components.add(wall4);

        ImageView wall5 = createTile(defaultWall);
        wall5.setScaleX(0.5f);
        wall5.setScaleY(0.5f);
        wall5.setX(-361+2*722-350);
        wall5.setY(-150-75);
        wall5.setZ(-1);
        components.add(wall5);

        ImageView floor1 = createRepeatedTile(floor, 6, 5);
        floor1.setScaleX(1.5f);
        floor1.setScaleY(1.5f);
        floor1.setX(0);
        floor1.setY(163);
        floor1.setZ(-1.2f);
        floor1.setRotationX(10);
        components.add(floor1);

        return components;
    }

    private ArrayList<ImageView> generateMap2()  {
        ArrayList<ImageView> components = new ArrayList<ImageView>();

        ImageView wall1 = createTile(defaultWall);
        wall1.setScaleX(0.5f);
        wall1.setScaleY(0.5f);
        wall1.setX(-361-350);
        wall1.setY(-150-75);
        wall1.setZ(-1);
        components.add(wall1);

        ImageView wall2 = createTile(redCol);
        wall2.setScaleX(0.25f);
        wall2.setScaleY(0.25f);
        wall2.setX(0-350);
        wall2.setY(43-150-75);
        wall2.setZ(-1);
        components.add(wall2);

        ImageView wall3 = createTile(defaultWall);
        wall3.setScaleX(0.5f);
        wall3.setScaleY(0.5f);
        wall3.setX(-361+722-350);
        wall3.setY(-150-75);
        wall3.setZ(-1);
        components.add(wall3);

        ImageView wall4 = createTile(blueCol);
        wall4.setScaleX(0.25f);
        wall4.setScaleY(0.25f);
        wall4.setX(722-350);
        wall4.setY(43 - 150-75);
        wall4.setZ(-1);
        components.add(wall4);

        ImageView wall5 = createTile(defaultWall);
        wall5.setScaleX(0.5f);
        wall5.setScaleY(0.5f);
        wall5.setX(-361+2*722-350);
        wall5.setY(-150-75);
        wall5.setZ(-1);
        components.add(wall5);

        ImageView floor1 = createRepeatedTile(bloodFloor, 6, 5);
        floor1.setScaleX(1.5f);
        floor1.setScaleY(1.5f);
        floor1.setX(0);
        floor1.setY(163);
        floor1.setZ(-1.2f);
        floor1.setRotationX(10);
        components.add(floor1);

        return components;
    }

    private ArrayList<ImageView> generateMap3()  {
        ArrayList<ImageView> components = new ArrayList<ImageView>();

        ImageView wall1 = createTile(defaultWall);
        wall1.setScaleX(0.5f);
        wall1.setScaleY(0.5f);
        wall1.setX(-361-350);
        wall1.setY(-150-75);
        wall1.setZ(-1);
        components.add(wall1);

        ImageView wall2 = createTile(greenCol);
        wall2.setScaleX(0.25f);
        wall2.setScaleY(0.25f);
        wall2.setX(0-350);
        wall2.setY(43-150-75);
        wall2.setZ(-1);
        components.add(wall2);

        ImageView wall3 = createTile(defaultWall);
        wall3.setScaleX(0.5f);
        wall3.setScaleY(0.5f);
        wall3.setX(-361+722-350);
        wall3.setY(-150-75);
        wall3.setZ(-1);
        components.add(wall3);

        ImageView wall4 = createTile(greenCol);
        wall4.setScaleX(0.25f);
        wall4.setScaleY(0.25f);
        wall4.setX(722-350);
        wall4.setY(43 - 150-75);
        wall4.setZ(-1);
        components.add(wall4);

        ImageView wall5 = createTile(defaultWall);
        wall5.setScaleX(0.5f);
        wall5.setScaleY(0.5f);
        wall5.setX(-361+2*722-350);
        wall5.setY(-150-75);
        wall5.setZ(-1);
        components.add(wall5);

        ImageView floor1 = createRepeatedTile(floor, 6, 5);
        floor1.setScaleX(1.5f);
        floor1.setScaleY(1.5f);
        floor1.setX(0);
        floor1.setY(163);
        floor1.setZ(-1.2f);
        floor1.setRotationX(10);
        components.add(floor1);

        return components;
    }

}
