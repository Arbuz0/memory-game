<template>
  <div class="container">
    <div class="header">
      <div class="side-scores left">
        <div
          class="player"
          v-for="player in leftPlayers"
          :key="player.name"
          :class="{ 'current-turn': currentPlayer === player.name }"
        >
          <h3>{{ player.name }}</h3>
          <p>Score: {{ player.score }}</p>
        </div>
      </div>
      <h2>Game Page</h2>
      <div class="side-scores right">
        <div
          class="player"
          v-for="player in rightPlayers"
          :key="player.name"
          :class="{ 'current-turn': currentPlayer === player.name }"
        >
          <h3>{{ player.name }}</h3>
          <p>Score: {{ player.score }}</p>
        </div>
      </div>
    </div>
    <div class="board">
      <MemoryCard
        v-for="card in cards"
        :key="card.id"
        :id="card.id"
        :image="card.image"
        :flipped="card.flipped"
        @flip="handleCardFlip"
        :is-checking="isChecking"
        :is-current-player="isCurrentPlayer"
      />
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRoute } from 'vue-router';
import MemoryCard from './MemoryCard.vue';
import websocketService from '../services/websocket';

export default {
  components: {
    MemoryCard
  },
  setup() {
    const cards = ref([]);
    const route = useRoute();
    const gameId = route.params.id;
    const playerName = route.query.playerName;
    const isChecking = ref(false);
    const flippedCards = ref([]);
    const players = ref([]);
    const currentPlayer = ref(null);

    const leftPlayers = ref([]);
    const rightPlayers = ref([]);

    const handleCardFlip = async (index) => {
      if (isChecking.value || flippedCards.value.length >= 2 || playerName !== currentPlayer.value) return;

      // Optimistically update the UI
      cards.value = cards.value.map((card) => {
        if (card.id === index) {
          return { ...card, flipped: !card.flipped };
        }
        return card;
      });

      // Track the flipped cards
      flippedCards.value.push(index);

      if (flippedCards.value.length === 2) {
        isChecking.value = true;
        const [firstIndex, secondIndex] = flippedCards.value;
        const firstCard = cards.value.find(card => card.id === firstIndex);
        const secondCard = cards.value.find(card => card.id === secondIndex);
        const delay = ms => new Promise(res => setTimeout(res, ms));

        if (firstCard.image !== secondCard.image) {
          // Cards do not match, flip them back after 1 second
          await delay(1000);
          cards.value = cards.value.map((card) => {
            if (card.id === firstIndex || card.id === secondIndex) {
              return { ...card, flipped: false };
            }
            return card;
          });
        }

        flippedCards.value = [];
        isChecking.value = false;
      }

      const playerAction = { gameId, playerName, index };

      fetch(`http://localhost:8080/game/gameplay`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(playerAction),
      })
      .then(response => response.json())
      .then(data => {
        const gameState = data;
        if (gameState && gameState.board && gameState.flipped) {
          cards.value = gameState.board.map((value, index) => ({
            id: index,
            image: `/cards/image${value}.jpg`,
            flipped: gameState.flipped[index]
          }));
          players.value = gameState.players.map((player) => ({
            name: player.name,
            score: player.score
          }));
          currentPlayer.value = gameState.currentPlayer;
          updatePlayerSides();
        } else {
          console.error('Malformed game state:', gameState);
        }
      })
      .catch(error => {
        console.error('Error updating game state:', error);
      });
    };

    const onMessageReceived = (data) => {
      const gameState = data.gameState || data;  // Adjust to ensure compatibility with initial fetch structure
      if (gameState && gameState.board && gameState.flipped) {
        cards.value = gameState.board.map((value, index) => ({
          id: index,
          image: `/cards/image${value}.jpg`,
          flipped: gameState.flipped[index]
        }));
        players.value = gameState.players.map((player) => ({
          name: player.name,
          score: player.score
        }));
        currentPlayer.value = gameState.currentPlayer;
        updatePlayerSides();
      } else {
        console.error('Malformed game state received from server:', gameState);
      }
    };

    const updatePlayerSides = () => {
      if (players.value.length > 1) {
        leftPlayers.value = [players.value[0]];
        rightPlayers.value = [players.value[1]];
      } else {
        leftPlayers.value = players.value;
        rightPlayers.value = [];
      }
    };

    const connectWebSocket = () => {
      websocketService.connectToSocket(gameId, onMessageReceived, () => {
        console.log('Connected to WebSocket server');
      }, (error) => {
        console.error('WebSocket connection error:', error);
      });
    };

    onMounted(() => {
      connectWebSocket();
      // Fetch initial game state from the backend
      fetch(`http://localhost:8080/game/${gameId}`)
        .then(response => response.json())
        .then(data => {
          const gameState = data.gameState || data;  // Adjust to ensure compatibility with initial fetch structure
          if (gameState && gameState.board && gameState.flipped) {
            cards.value = gameState.board.map((value, index) => ({
              id: index,
              image: `/cards/image${value}.jpg`,
              flipped: gameState.flipped[index]
            }));
            players.value = gameState.players.map((player) => ({
              name: player.name,
              score: player.score
            }));
            currentPlayer.value = gameState.currentPlayer;
            updatePlayerSides();
          } else {
            console.error('Malformed initial game state:', gameState);
          }
        })
        .catch(error => {
          console.error('Error fetching initial game state:', error);
        });
    });

    onBeforeUnmount(() => {
      websocketService.disconnect();
    });

    return {
      cards,
      handleCardFlip,
      isChecking,
      players,
      currentPlayer,
      playerName,
      leftPlayers,
      rightPlayers
    };
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.side-scores {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100px;
}

.player {
  text-align: center;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  width: 80px;
}

.current-turn {
  background-color: #ffd700; /* Gold color for the current player */
}

.board {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  grid-template-rows: repeat(4, 1fr);
  gap: 10px;
  margin-top: 20px;
}
</style>
