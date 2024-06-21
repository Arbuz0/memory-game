<template>
  <div class="container">
    <h2>Game History</h2>
    <div class="search-bar">
      <input v-model="searchQuery" type="text" placeholder="Enter player name" />
      <button @click="fetchGameHistory">Search</button>
    </div>
    <div class="history-box">
      <div v-if="gameHistory.length > 0" class="game-history-list">
        <div v-for="game in gameHistory" :key="game.id" class="game-history-item">
          <div class="players-scores">
            <p class="player-name">{{ game.player1 }}</p>
            <p class="player-score">{{ game.player1Score }}</p>
            <p class="vs">:</p>
            <p class="player-score">{{ game.player2Score }}</p>
            <p class="player-name">{{ game.player2 }}</p>
          </div>
          <div class="timestamp">
            <p>{{ new Date(game.timestamp).toLocaleString() }}</p>
          </div>
        </div>
      </div>
      <p v-else>No game history found.</p>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const gameHistory = ref([]);
    const searchQuery = ref('');

    const fetchGameHistory = () => {
      fetch(`http://localhost:8080/history/${searchQuery.value}`)
        .then(response => response.json())
        .then(data => {
          gameHistory.value = data;
        })
        .catch(error => {
          console.error('Error fetching game history:', error);
        });
    };

    return {
      gameHistory,
      searchQuery,
      fetchGameHistory,
    };
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
}

.search-bar {
  display: flex;
  margin-bottom: 20px;
}

.search-bar input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px 0 0 5px;
}

.search-bar button {
  padding: 10px 20px;
  font-size: 16px;
  border: 1px solid #4CAF50;
  background-color: #4CAF50;
  color: white;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
}

.search-bar button:hover {
  background-color: #45a049;
}

.history-box {
  max-height: 400px; /* Set the max height of the box */
  overflow-y: auto; /* Make the box scrollable */
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background-color: #f9f9f9;
}

.game-history-list {
  display: flex;
  flex-direction: column;
}

.game-history-item {
  background-color: #e0f7da; /* Light green background */
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
}

.players-scores {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.player-name, .player-score, .vs {
  margin: 0 5px;
  font-size: 24px;
  font-weight: bold;
}

.timestamp {
  text-align: center;
  font-size: 12px;
  color: #888;
  margin-top: 5px;
}
</style>
