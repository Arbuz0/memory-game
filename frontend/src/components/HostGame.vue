<template>
  <div class="container">
    <h1>Host Game</h1>
    <form @submit.prevent="hostGame" class="host-form">
      <input v-model="playerName" type="text" placeholder="Enter your name" required>
      <button type="submit" class="menu-button">Host Game</button>
    </form>
    <div v-if="gameId" class="game-info">
      <div class="game-id-container">
        <p>Game ID: {{ gameId }}</p>
        <button @click="copyToClipboard" class="copy-button">Copy</button>
      </div>
      <button @click="startGame" class="menu-button">Start Game</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuth0 } from '@auth0/auth0-vue';

export default {
  setup() {
    const playerName = ref('');
    const gameId = ref(null);
    const router = useRouter();
    const { isAuthenticated, getAccessTokenSilently, loginWithRedirect } = useAuth0();
    const accessToken = ref(null);

    const hostGame = async () => {
      try {
        const token = await getAccessTokenSilently(); // Ensure token is retrieved before the request
        const response = await fetch('http://localhost:8080/game/start', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, // Use the token here
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

    const copyToClipboard = () => {
      navigator.clipboard.writeText(gameId.value).catch(err => {
        console.error('Could not copy text: ', err);
      });
    };

    onMounted(async () => {
      if (!isAuthenticated.value) {
        await loginWithRedirect();
      } else {
        accessToken.value = await getAccessTokenSilently();
      }
    });

    return {
      playerName,
      gameId,
      hostGame,
      startGame,
      copyToClipboard,
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

.host-form {
  display: flex;
  gap: 20px;
  align-items: center;
  margin-bottom: 20px;
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

.game-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.game-id-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.copy-button {
  padding: 5px 10px;
  font-size: 14px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.copy-button:hover {
  background-color: #45a049;
}
</style>
