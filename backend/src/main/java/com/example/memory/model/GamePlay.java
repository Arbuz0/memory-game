package com.example.memory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class GamePlay {
    private final String gameId;
    private final int index1;
    private final int index2;

    @JsonCreator
    public GamePlay(@JsonProperty("gameId") String gameId, @JsonProperty("index1") int index1, @JsonProperty("index2") int index2) {
        this.gameId = gameId;
        this.index1 = index1;
        this.index2 = index2;
    }

    public String getGameId() {
        return gameId;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
}
