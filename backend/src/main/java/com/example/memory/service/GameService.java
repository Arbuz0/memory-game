package com.example.memory.service;

import com.example.memory.exception.InvalidGameException;
import com.example.memory.exception.NotFoundException;
import com.example.memory.model.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {
    private final Map<String, Game> games = Collections.synchronizedMap(new HashMap<>());

    public Game createGame(Player player) {
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId, player);
        games.put(gameId, game);
        return game;
    }

    public Game connectToGame(Player player, String gameId) throws InvalidGameException {
        Game game = games.get(gameId);
        if (game == null) {
            throw new InvalidGameException("Invalid game ID");
        }
        if (game.isFull()) {
            throw new InvalidGameException("Game is full");
        }
        game.addPlayer(player);
        return game;
    }

    public Game getGame(String gameId) throws NotFoundException {
        Game game = games.get(gameId);
        if (game == null) {
            throw new NotFoundException("Game not found");
        }
        return game;
    }

    public GameState handleGamePlay(PlayerAction action) throws NotFoundException, InvalidGameException {
        Game game = games.get(action.getGameId());
        if (game == null) {
            throw new NotFoundException("Game not found");
        }
        game.flipCard(action.getPlayerName(), action.getIndex());
        return game.getGameState();
    }
}
