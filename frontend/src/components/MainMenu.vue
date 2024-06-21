<template>
  <div class="container">
    <h2>Main Menu</h2>
    <router-link to="/host">Host Game</router-link>
    <router-link to="/join">Join Game</router-link>
    <router-link to="/history">Game History</router-link>
  </div>
</template>

<script>
export default {
  name: 'MainMenu',
};
</script>
