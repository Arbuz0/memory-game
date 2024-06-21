<template>
  <div class="container">
    <h1>Memory Game</h1>
    <div class="button-container">
      <router-link to="/host">
        <button class="menu-button">Host Game</button>
      </router-link>
      <router-link to="/join">
        <button class="menu-button">Join Game</button>
      </router-link>
      <router-link to="/history">
        <button class="menu-button">Game History</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainMenu',
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
  margin-bottom: 60px; /* Add more separation between title and buttons */
  white-space: nowrap; /* Ensure the title is on one line */
}

.button-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.menu-button {
  width: 200px; /* Make buttons equal size */
  padding: 15px 0; /* Adjust padding to make button bigger */
  font-size: 18px; /* Make button text bigger */
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
