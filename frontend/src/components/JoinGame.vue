<template>
  <div class="container">
    <h1>Join Game</h1>
    <form @submit.prevent="joinGame" class="join-form">
      <input v-model="gameId" type="text" placeholder="Enter game ID" required>
      <input v-model="playerName" type="text" placeholder="Enter your name" required>
      <button type="submit" class="menu-button">Join Game</button>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const gameId = ref('');
    const playerName = ref('');
    const router = useRouter();

    const joinGame = async () => {
      try {
        const response = await fetch('http://localhost:8080/game/connect', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ gameId: gameId.value, player: { name: playerName.value } }),
        });
        const game = await response.json();
        router.push({ path: `/game/${game.gameId}`, query: { playerName: playerName.value } });
      } catch (error) {
        console.error('Error joining game:', error);
      }
    };

    return {
      gameId,
      playerName,
      joinGame,
    };
  }
};
</script>

<style scoped>
.container {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

h1 {
  font-size: 48px; /* Make the title font a lot bigger */
  margin-bottom: 40px; /* Add separation between title and form */
  white-space: nowrap; /* Ensure the title is on one line */
}

.join-form {
  display: flex;
  gap: 20px;
  align-items: center;
}

input[type="text"] {
  padding: 10px;
  font-size: 16px;
  width: 200px; /* Make input width consistent with buttons */
  border: 2px solid #4CAF50;
  border-radius: 5px;
}

.menu-button {
  width: 150px; /* Adjusted button width */
  padding: 10px 0; /* Adjust padding to make button bigger */
  font-size: 16px; /* Adjust button text size */
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.menu-button:hover {
  background-color: #45a049;
}
</style>
