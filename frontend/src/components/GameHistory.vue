<template>
  <div class="container">
    <h2>Game History</h2>
    <ul>
      <li v-for="game in gameHistory" :key="game.id">
        {{ game.details }}
      </li>
    </ul>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';

export default {
  setup() {
    const gameHistory = ref([]); // Fetch game history from the backend

    onMounted(() => {
      // Fetch game history from the backend
    });

    return {
      gameHistory,
    };
  }
};
</script>
