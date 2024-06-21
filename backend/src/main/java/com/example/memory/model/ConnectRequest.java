package com.example.memory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ConnectRequest {
    private final String gameId;
    private final Player player;

    @JsonCreator
    public ConnectRequest(@JsonProperty("gameId") String gameId, @JsonProperty("player") Player player) {
        this.gameId = gameId;
        this.player = player;
    }

    public String getGameId() {
        return gameId;
    }

    public Player getPlayer() {
        return player;
    }
}
