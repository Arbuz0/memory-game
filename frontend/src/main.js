// main.js or main.ts
import { createApp } from 'vue';
import App from './App.vue';
import { createAuth0 } from '@auth0/auth0-vue';
import router from './router';

const app = createApp(App);

app.use(
  createAuth0({
    domain: 'dev-blmdt4682iwaub4z.us.auth0.com',
    clientId: 'k6lV6iiMul6Zw8Ct9zfJ2Y3dNm3igTUI',
    authorizationParams: {
      redirect_uri: window.location.origin
    }
  })
);

app.use(router);

app.mount('#app');
