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
    <div class="auth-buttons">
      <button 
        @click="login" 
        class="menu-button small-button" 
        :disabled="isAuthenticated"
      >
        Login
      </button>
      <button 
        @click="logout" 
        class="menu-button small-button" 
        :disabled="!isAuthenticated"
      >
        Logout
      </button>
    </div>
  </div>
</template>

<script>
import { useAuth0 } from '@auth0/auth0-vue';

export default {
  setup() {
    const { loginWithRedirect, logout, isAuthenticated } = useAuth0();

    return {
      login: () => loginWithRedirect(),
      logout: () => logout({ returnTo: window.location.origin }),
      isAuthenticated
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
  margin-bottom: 60px;
  white-space: nowrap;
}

.button-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.auth-buttons {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.menu-button {
  width: 200px;
  padding: 15px 0;
  font-size: 18px;
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

.menu-button:disabled {
  background-color: #9E9E9E;
  cursor: not-allowed;
}

.small-button {
  width: 95px;
  padding: 10px 0;
  font-size: 14px;
}
</style>
