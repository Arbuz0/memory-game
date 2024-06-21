package com.example.memory.controller;

import com.example.memory.exception.InvalidGameException;
import com.example.memory.exception.NotFoundException;
import com.example.memory.model.*;
import com.example.memory.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) {
        log.info("start game request: {}", player);
        Game game = gameService.createGame(player);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidGameException {
        log.info("connect request: {}", request);
        Game game = gameService.connectToGame(request.getPlayer(), request.getGameId());
        return ResponseEntity.ok(game);
    }

    @PostMapping("/gameplay")
    public ResponseEntity<GameState> gamePlay(@RequestBody PlayerAction action) throws NotFoundException, InvalidGameException {
        log.info("gameplay: {}", action);
        GameState gameState = gameService.handleGamePlay(action);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + action.getGameId(), gameState);
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable String gameId) {
        try {
            Game game = gameService.getGame(gameId);
            return ResponseEntity.ok(game);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
