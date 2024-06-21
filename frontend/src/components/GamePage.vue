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
import { useAuth0 } from '@auth0/auth0-vue';

export default {
  components: {
    MemoryCard
  },
  setup() {
    const cards = ref([]);
    const route = useRoute();
    const gameId = route.params.id;
    const { getAccessTokenSilently } = useAuth0();

    const handleCardFlip = async (index) => {
      console.log('Card flipped:', index);

      // Optimistically update the UI
      cards.value = cards.value.map((card) => {
        if (card.id === index) {
          return { ...card, flipped: !card.flipped };
        }
        return card;
      });

      const playerAction = { gameId, index };

      try {
        const token = await getAccessTokenSilently();
        const response = await fetch(`http://localhost:8080/game/gameplay`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
          body: JSON.stringify(playerAction),
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log('Game state updated:', data);
        const gameState = data;
        cards.value = gameState.board.map((value, index) => ({
          id: index,
          value,
          flipped: gameState.flipped[index]
        }));
      } catch (error) {
        console.error('Error updating game state:', error);
      }
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

    onMounted(async () => {
      console.log('Component mounted, establishing WebSocket connection...');
      connectWebSocket();
      try {
        const token = await getAccessTokenSilently();
        const response = await fetch(`http://localhost:8080/game/${gameId}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log('Initial game state fetched:', data);
        const gameState = data.gameState || data;  // Adjust to ensure compatibility with initial fetch structure
        cards.value = gameState.board.map((value, index) => ({
          id: index,
          value,
          flipped: gameState.flipped[index]
        }));
      } catch (error) {
        console.error('Error fetching initial game state:', error);
      }
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
