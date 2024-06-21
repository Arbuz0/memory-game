package com.example.memory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PlayerAction {
    private final String gameId;
    private final int index;

    @JsonCreator
    public PlayerAction(@JsonProperty("gameId") String gameId, @JsonProperty("index") int index) {
        this.gameId = gameId;
        this.index = index;
    }

    public String getGameId() {
        return gameId;
    }

    public int getIndex() {
        return index;
    }
}
