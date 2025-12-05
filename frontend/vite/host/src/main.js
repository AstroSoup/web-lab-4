import { createApp } from 'vue'
import App from './App.vue'
import { pinia } from './bootloader.js'

import { createRouter, createWebHistory } from 'vue-router'

import StartPage from './pages/StartPage.vue'
import { useAuthStore } from './stores/AuthStore.js'
import MainPage from "./pages/MainPage.vue";

const routes = [
    { path: '/', name: 'start', component: StartPage , meta: {requiresAuth: false} },
    //{path: '/', name: 'not found', component: NotFoundPage, meta: {requiresAuth: false} },
    { path: '/app', name: 'main', component: MainPage, meta: { requiresAuth: true } },

]


const router = createRouter({
    history: createWebHistory(),
    routes
})

const app = createApp(App)

app.use(pinia)
app.use(router)

router.beforeEach(async (to, from) => {
    if (to.meta.requiresAuth) {
        const isAuth = await useAuthStore().check_auth()
        if (!isAuth) {
            return {name: "start"};
        }
    }
    return true;
})


app.mount('#app')