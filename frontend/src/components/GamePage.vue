<template>
  <div class="container">
    <h2>Game Page</h2>
    <div class="board">
      <MemoryCard 
        v-for="card in cards" 
        :key="card.id" 
        :id="card.id"
        :value="card.value" 
        :flipped="card.flipped" 
        @flip="handleCardFlip" 
      />
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import MemoryCard from './MemoryCard.vue';
import websocketService from '../services/websocket';
import { useRoute } from 'vue-router';

export default {
  components: {
    MemoryCard
  },
  setup() {
    const cards = ref([]);
    const route = useRoute();
    const gameId = route.params.id;

    const handleCardFlip = (index) => {
      console.log('Card flipped:', index);

      // Optimistically update the UI
      cards.value = cards.value.map((card) => {
        if (card.id === index) {
          return { ...card, flipped: !card.flipped };
        }
        return card;
      });

      const playerAction = { gameId, index };

      fetch(`http://localhost:8080/game/gameplay`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(playerAction),
      })
        .then(response => response.json())
        .then(data => {
          console.log('Game state updated:', data);
          const gameState = data;
          cards.value = gameState.board.map((value, index) => ({
            id: index,
            value,
            flipped: gameState.flipped[index]
          }));
        })
        .catch(error => {
          console.error('Error updating game state:', error);
        });
    };

    const onMessageReceived = (data) => {
      console.log('Message received from server:', data);
      const gameState = data.gameState || data;  // Adjust to ensure compatibility with initial fetch structure
      cards.value = gameState.board.map((value, index) => ({
        id: index,
        value,
        flipped: gameState.flipped[index]
      }));
    };

    const connectWebSocket = () => {
      console.log('Connecting to WebSocket...');
      websocketService.connectToSocket(gameId, onMessageReceived, () => {
        console.log('Connected to WebSocket server');
      }, (error) => {
        console.error('WebSocket connection error:', error);
      });
    };

    onMounted(() => {
      console.log('Component mounted, establishing WebSocket connection...');
      connectWebSocket();
      // Fetch initial game state from the backend
      fetch(`http://localhost:8080/game/${gameId}`)
        .then(response => response.json())
        .then(data => {
          console.log('Initial game state fetched:', data);
          const gameState = data.gameState || data;  // Adjust to ensure compatibility with initial fetch structure
          cards.value = gameState.board.map((value, index) => ({
            id: index,
            value,
            flipped: gameState.flipped[index]
          }));
        });
    });

    onBeforeUnmount(() => {
      console.log('Component unmounting, disconnecting WebSocket...');
      websocketService.disconnect();
    });

    return {
      cards,
      handleCardFlip,
    };
  }
};
</script>

<style scoped>
h2 {
  margin-top: 20px;
}
</style>
