package com.example.cs2340s1.ViewModels;

import com.example.cs2340s1.util.GameState;

public class VMScreenEnd {
    private GameState gs;
    public static GameState.LeaderboardInformation getLastScore() {
        return GameState.getLatestAttempt();
    }

}