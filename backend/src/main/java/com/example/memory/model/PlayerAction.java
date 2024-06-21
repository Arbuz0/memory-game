package com.example.memory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PlayerAction {
    private final String gameId;
    private final String playerName;
    private final int index;

    @JsonCreator
    public PlayerAction(@JsonProperty("gameId") String gameId, @JsonProperty("playerName") String playerName, @JsonProperty("index") int index) {
        this.gameId = gameId;
        this.playerName = playerName;
        this.index = index;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getIndex() {
        return index;
    }
}
