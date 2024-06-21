package com.example.memory.model;

import java.util.*;

public final class Game {
    private final String gameId;
    private final List<Player> players = new ArrayList<>();
    private final Map<String, Integer> playerScores = new HashMap<>();
    private final List<Card> board;
    private final boolean[] flipped;
    private final List<Integer> matchedCards = new ArrayList<>();
    private boolean playerTurn;

    public Game(String gameId, Player player) {
        this.gameId = gameId;
        this.players.add(player);
        this.playerScores.put(player.getName(), 0);
        this.board = initializeBoard();
        this.flipped = new boolean[board.size()];
        this.playerTurn = true; // The player who creates the game starts first
    }

    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
            this.playerScores.put(player.getName(), 0);
        }
    }

    public boolean isFull() {
        return players.size() >= 2;
    }

    private List<Card> initializeBoard() {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            values.add(i);
            values.add(i);
        }
        Collections.shuffle(values);
        List<Card> board = new ArrayList<>();
        for (int value : values) {
            board.add(new Card(value));
        }
        return board;
    }

    public void flipCard(int index) {
        if (flipped[index] || matchedCards.contains(board.get(index).getValue())) {
            return;
        }

        flipped[index] = true;

        // Logic for matching cards
        List<Integer> flippedIndices = new ArrayList<>();
        for (int i = 0; i < flipped.length; i++) {
            if (flipped[i] && !matchedCards.contains(board.get(i).getValue())) {
                flippedIndices.add(i);
            }
        }

        if (flippedIndices.size() == 2) {
            int firstIndex = flippedIndices.get(0);
            int secondIndex = flippedIndices.get(1);
            if (board.get(firstIndex).getValue() == board.get(secondIndex).getValue()) {
                matchedCards.add(board.get(firstIndex).getValue());
                // Increment the score of the current player
                Player currentPlayer = players.get(playerTurn ? 0 : 1);
                playerScores.put(currentPlayer.getName(), playerScores.get(currentPlayer.getName()) + 1);
            } else {
                flipped[firstIndex] = false;
                flipped[secondIndex] = false;
            }
            this.playerTurn = !this.playerTurn;
        }
    }

    public GameState getGameState() {
        List<PlayerState> playerStates = new ArrayList<>();
        for (Player player : players) {
            playerStates.add(new PlayerState(player.getName(), playerScores.get(player.getName())));
        }
        return new GameState(Collections.unmodifiableList(board), flipped.clone(), Collections.unmodifiableList(matchedCards), playerTurn, playerStates);
    }

    public String getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public boolean[] getFlipped() {
        return flipped.clone();
    }

    public List<Integer> getMatchedCards() {
        return Collections.unmodifiableList(matchedCards);
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}

class PlayerState {
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
