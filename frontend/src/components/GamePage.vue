<template>
  <div class="container">
    <h2>Game Page</h2>
    <div class="board">
      <MemoryCard 
        v-for="card in cards" 
        :key="card.id" 
        :value="card.value" 
        :flipped="card.flipped" 
        @flip="handleCardFlip" 
      />
    </div>
    <div class="messages">
      <h3>Received Messages</h3>
      <ul>
        <li v-for="(message, index) in messages" :key="index">{{ message }}</li>
      </ul>
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
    const messages = ref([]);
    const route = useRoute();
    const gameId = route.params.id;

    const handleCardFlip = (index) => {
      console.log('Card flipped:', index);
      websocketService.sendMessage(gameId, { index });
    };

    const updateCards = (gameState) => {
      cards.value = gameState.board.map((value, index) => ({
        id: index,
        value,
        flipped: gameState.flipped[index]
      }));
    };

    const onMessageReceived = (data) => {
      console.log('Message received from server:', data);
      messages.value.push(JSON.stringify(data));
      const gameState = data.gameState;
      updateCards(gameState);
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
          const gameState = data.gameState;
          updateCards(gameState);
        })
        .catch(error => {
          console.error('Error fetching initial game state:', error);
        });
    });

    onBeforeUnmount(() => {
      console.log('Component unmounting, disconnecting WebSocket...');
      websocketService.disconnect();
    });

    return {
      cards,
      messages,
      handleCardFlip,
    };
  }
};
</script>

<style scoped>
.board {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.messages {
  margin-top: 20px;
}

.messages ul {
  list-style-type: none;
  padding: 0;
}

.messages li {
  background-color: #f9f9f9;
  margin-bottom: 5px;
  padding: 10px;
  border: 1px solid #ddd;
}
</style>
