import { createRouter, createWebHistory } from 'vue-router';
import MainMenu from '../components/MainMenu.vue';
import HostGame from '../components/HostGame.vue';
import JoinGame from '../components/JoinGame.vue';
import GamePage from '../components/GamePage.vue';
import GameHistory from '../components/GameHistory.vue';

const routes = [
  { path: '/', component: MainMenu },
  { path: '/host', component: HostGame },
  { path: '/join', component: JoinGame },
  { path: '/game/:id', component: GamePage },
  { path: '/history', component: GameHistory },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
