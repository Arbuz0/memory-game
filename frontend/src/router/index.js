import { createRouter, createWebHistory } from 'vue-router';
import MainMenu from '@/components/MainMenu.vue';
import GamePage from '@/components/GamePage.vue';
import GameHistory from '@/components/GameHistory.vue';

const routes = [
  {
    path: '/',
    name: 'MainMenu',
    component: MainMenu
  },
  {
    path: '/game',
    name: 'GamePage',
    component: GamePage
  },
  {
    path: '/history',
    name: 'GameHistory',
    component: GameHistory
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
