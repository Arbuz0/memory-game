<template>
  <div class="container">
    <h2>Join Game</h2>
    <form @submit.prevent="joinGame">
      <input v-model="gameId" type="text" placeholder="Enter game ID" required>
      <input v-model="playerName" type="text" placeholder="Enter your name" required>
      <button type="submit">Join Game</button>
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
        router.push(`/game/${game.gameId}`);
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
