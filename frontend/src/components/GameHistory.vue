<template>
  <div class="container">
    <h2>Game History</h2>
    <div class="search-bar">
      <input v-model="searchQuery" type="text" placeholder="Enter player name" />
      <button @click="fetchGameHistory">Search</button>
    </div>
    <ul v-if="gameHistory.length > 0">
      <li v-for="game in gameHistory" :key="game.id">
        {{ game.player1 }} vs {{ game.player2 }} - {{ game.player1Score }}:{{ game.player2Score }} ({{ new Date(game.timestamp).toLocaleString() }})
      </li>
    </ul>
    <p v-else>No game history found.</p>
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

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 10px;
  margin-bottom: 5px;
  background: #f9f9f9;
  border: 1px solid #ddd;
}
</style>
