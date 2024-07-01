import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { useKakao } from 'vue3-kakao-maps';

import App from './App.vue';
import router from './router';
useKakao(import.meta.env.VITE_KAKAO_MAP_API_KEY, ['clusterer', 'services', 'drawing']);

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount('#app');
