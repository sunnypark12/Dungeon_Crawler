package com.example.cs2340s1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs2340s1.ViewModels.VMScreenEnd;
import com.example.cs2340s1.navigation.NewView;
import com.example.cs2340s1.util.GameState;

public class ScreenEnd extends NewView {

    private TextView ScoreFinalText;
    private TextView endGameMessage;
    private ImageView endGameImage;

    private Button restartButton;
    private Button exitButton;
    private ConstraintLayout endGameBackground;
    private VMScreenEnd vm;

    public ScreenEnd() {
        super(ScreenEnd.class, R.layout.activity_win, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ScoreFinalText = view.findViewById(R.id.score_win_Final);
        // TODO: Wire these up to the frontend once they are created in the graphics
        endGameMessage = view.findViewById(R.id.game_win_text);
        endGameImage = view.findViewById(R.id.end_game_image);
        endGameBackground = view.findViewById(R.id.end_game_background);
        restartButton = view.findViewById(R.id.win_restart_button);
        exitButton = view.findViewById(R.id.exit_button);

        /**
         * With this boolean bundle, in theory we should be able to get the boolean from ScreenGame and handle the loading screen accordingly.
         * We can have just one screen and place the respective UI elements dependent on the boolean result.
         */
        StringBuilder sb = new StringBuilder();
        boolean isWin = getArguments().getBoolean("isWin", true);
        if (isWin) {
            endGameMessage.setText("YOU WON!");
            // TODO: Wire this up to the frontend once Win image object is created in graphics
            endGameImage.setImageResource(R.drawable.end_game_image);
            endGameBackground.setBackgroundResource(R.drawable.end_game_background_win);
        } else {
            endGameMessage.setText("GAME OVER");
            // TODO: Wire this up to the frontend once Win image object is created in graphics
            endGameImage.setImageResource(R.drawable.end_game_image);
            endGameBackground.setBackgroundResource(R.drawable.end_game_background_lose);
        }


        sb.append(VMScreenEnd.getLastScore().getScore()).append("\n\n");
        for (GameState.LeaderboardInformation g : GameState.getAttempts()) {
            if (g == null) {
                continue;
            }
            sb.append(g.getName()).append(" | ").append(g.getDate()).append(" | ").append(g.getScore());
            sb.append("\n");
        }
        ScoreFinalText.setText(sb.toString());
    }
}