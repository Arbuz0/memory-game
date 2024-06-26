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
    private final boolean playerTurn;

    public GameState(List<Card> board, boolean[] flipped, List<Integer> matchedCards, boolean playerTurn) {
        this.board = board.stream().map(Card::getValue).collect(Collectors.toList());
        this.flipped = flipped.clone();
        this.matchedCards = new ArrayList<>(matchedCards);
        this.playerTurn = playerTurn;
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

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
