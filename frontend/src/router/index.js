import {createRouter, createWebHistory} from 'vue-router'
import Main from '../views/Main.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Main
        },
        {
            path: '/about',
            name: 'about',
            component: () => import('../views/AboutView.vue'),
        },
        {
            path: '/signup',
            name: 'signup',
            component: () => import('../views/Signup.vue'),
        }
    ]
})

export default router
