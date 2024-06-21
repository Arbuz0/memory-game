package com.example.memory.service;

import com.example.memory.model.GameHistory;
import com.example.memory.repository.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoryService {

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    public void saveGameResult(String player1, String player2, int player1Score, int player2Score) {
        GameHistory gameHistory = new GameHistory(player1, player2, player1Score, player2Score);
        gameHistoryRepository.save(gameHistory);
    }

    public List<GameHistory> getGameHistory(String player) {
        return gameHistoryRepository.findByPlayer1OrPlayer2(player, player);
    }
}
