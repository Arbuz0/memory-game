<template>
  <div class="main-menu">
    <h1>Memory Game</h1>
    <router-link to="/game">
      <button>Start Game</button>
    </router-link>
    <router-link to="/history">
      <button>View History</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'MainMenu'
};
</script>

<style scoped>
.main-menu {
  text-align: center;
  margin-top: 50px;
}
button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 16px;
}
</style>
