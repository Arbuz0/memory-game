<template>
  <div class="game-history">
    <h1>Game History</h1>
    <ul>
      <li v-for="(game, index) in gameHistory" :key="index">
        {{ game }}
      </li>
    </ul>
    <router-link to="/">
      <button>Back to Main Menu</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'GameHistory',
  data() {
    return {
      gameHistory: JSON.parse(localStorage.getItem('gameHistory')) || []
    };
  }
};
</script>

<style scoped>
.game-history {
  text-align: center;
  margin-top: 50px;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 10px 0;
}
button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
}
</style>
