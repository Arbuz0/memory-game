package com.example.memory.model;

public class PlayerState {
    private final String name;
    private final int score;

    public PlayerState(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
