package com.example.memory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_history")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player1", nullable = false)
    private String player1;

    @Column(name = "player2", nullable = false)
    private String player2;

    @Column(name = "player1_score", nullable = false)
    private int player1Score;

    @Column(name = "player2_score", nullable = false)
    private int player2Score;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    public GameHistory() {
    }

    public GameHistory(String player1, String player2, int player1Score, int player2Score) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
