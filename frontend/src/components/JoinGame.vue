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
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuth0 } from '@auth0/auth0-vue';

export default {
  setup() {
    const gameId = ref('');
    const playerName = ref('');
    const router = useRouter();
    const { isAuthenticated, getAccessTokenSilently, loginWithRedirect } = useAuth0();
    const accessToken = ref(null);

    const joinGame = async () => {
      try {
        const response = await fetch('http://localhost:8080/game/connect', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken.value}`,
          },
          body: JSON.stringify({ gameId: gameId.value, player: { name: playerName.value } }),
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const game = await response.json();
        router.push(`/game/${game.gameId}`);
      } catch (error) {
        console.error('Error joining game:', error);
      }
    };

    onMounted(async () => {
      if (!isAuthenticated.value) {
        await loginWithRedirect();
      } else {
        accessToken.value = await getAccessTokenSilently();
      }
    });

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
  font-size: 48px;
  margin-bottom: 40px;
  white-space: nowrap;
}

.join-form {
  display: flex;
  gap: 20px;
  align-items: center;
}

input[type="text"] {
  padding: 10px;
  font-size: 16px;
  width: 200px;
  border: 2px solid #4CAF50;
  border-radius: 5px;
}

.menu-button {
  width: 150px;
  padding: 10px 0;
  font-size: 16px;
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
