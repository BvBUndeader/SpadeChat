import { createRouter, createWebHistory } from 'vue-router';
import LoginScreen from '@/views/LoginScreen.vue';
import SpadeChat from '@/views/SpadeChat.vue';

const routes = [
    { path: '/', component: LoginScreen }, 
    { path: '/spadechat/:username', component: SpadeChat, props: true }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
