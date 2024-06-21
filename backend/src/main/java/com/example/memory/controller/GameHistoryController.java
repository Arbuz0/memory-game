package com.example.memory.controller;

import com.example.memory.model.GameHistory;
import com.example.memory.service.GameHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/history")
@CrossOrigin(origins = "*")
public class GameHistoryController {

    private final GameHistoryService gameHistoryService;

    @GetMapping("/{player}")
    public ResponseEntity<List<GameHistory>> getGameHistory(@PathVariable String player) {
        List<GameHistory> history = gameHistoryService.getGameHistory(player);
        return ResponseEntity.ok(history);
    }
}
