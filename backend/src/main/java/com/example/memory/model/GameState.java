package com.example.memory.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class GameState {
    private final List<Integer> board;
    private final boolean[] flipped;
    private final List<Integer> matchedCards;
    private final String currentPlayer;
    private final List<PlayerState> players;

    public GameState(List<Card> board, boolean[] flipped, List<Integer> matchedCards, String currentPlayer, List<PlayerState> players) {
        this.board = board.stream().map(Card::getValue).collect(Collectors.toList());
        this.flipped = flipped.clone();
        this.matchedCards = new ArrayList<>(matchedCards);
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public List<Integer> getBoard() {
        return Collections.unmodifiableList(board);
    }

    public boolean[] getFlipped() {
        return flipped.clone();
    }

    public List<Integer> getMatchedCards() {
        return Collections.unmodifiableList(matchedCards);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public List<PlayerState> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
