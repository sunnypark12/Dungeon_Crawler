package com.example.cs2340s1;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cs2340s1.navigation.FragmentHelper;
import com.example.cs2340s1.navigation.NewView;

import java.util.function.Function;
import android.widget.Toast;


public class ScreenModifyPlayer extends NewView {

    public ScreenModifyPlayer() {
        super(ScreenModifyPlayer.class, R.layout.fragment_screen_modify_player, false);
        persistor = new ScreenModifierPersistor();
    }
    private ImageView ImgViewChar1, ImgViewChar2, ImgViewChar3, ImgViewSelected;
    private class ScreenModifierPersistor {
        public int SelectedCharacter;

        public int getSelectedCharacter() {
            return SelectedCharacter;
        }
    }

    private static ScreenModifierPersistor persistor;
    private Button BtnNext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ImgViewChar1 = view.findViewById(R.id.ImageViewChar1);
        ImgViewChar2 = view.findViewById(R.id.ImageViewChar2);
        ImgViewChar3 = view.findViewById(R.id.ImageViewChar3);
        ImgViewSelected = view.findViewById(R.id.ImageViewSelected);

        int[] ImgResources = new int[]{
                R.drawable.sprite1_idle,
                R.drawable.sprite2_idle,
                R.drawable.sprite3_idle
        };

        persistor.SelectedCharacter = -1;
        int i = 0;
        for (ImageView img : new ImageView[]{ImgViewChar1, ImgViewChar2, ImgViewChar3}) {
            int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                private final int counter = finalI;
                @Override
                public void onClick(View view) {
                    persistor.SelectedCharacter = counter;
                    img.setImageResource(ImgResources[counter]);
                    ImgViewSelected.setImageResource(ImgResources[counter]);
                    ((AnimationDrawable) ImgViewSelected.getDrawable()).start();
                }
            });

            ((AnimationDrawable) img.getDrawable()).start();
            i++;
        }

        BtnNext = view.findViewById(R.id.BtnPlayerSelect1);
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (persistor.SelectedCharacter == -1) {
                    Toast.makeText(getContext(), "Please select a character!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle b = new Bundle();
                b.putInt("SelectedChar", persistor.getSelectedCharacter());
                FragmentHelper.getInstance().loadFragment(ScreenPlayerSelect.class, b);
            }
        });
    }
}