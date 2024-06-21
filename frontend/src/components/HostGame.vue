<template>
  <div class="container">
    <h2>Host Game</h2>
    <form @submit.prevent="hostGame">
      <input v-model="playerName" type="text" placeholder="Enter your name" required>
      <button type="submit">Host Game</button>
    </form>
    <div v-if="gameId">
      <p>Game ID: {{ gameId }}</p>
      <button @click="startGame">Start Game</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const playerName = ref('');
    const gameId = ref(null);
    const router = useRouter();

    const hostGame = async () => {
      try {
        const response = await fetch('http://localhost:8080/game/start', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ name: playerName.value }),
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const game = await response.json();
        gameId.value = game.gameId;
      } catch (error) {
        console.error('Error starting game:', error);
      }
    };

    const startGame = () => {
      router.push(`/game/${gameId.value}`);
    };

    return {
      playerName,
      gameId,
      hostGame,
      startGame,
    };
  }
};
</script>
