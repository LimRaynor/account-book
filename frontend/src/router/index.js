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
        },
        {
            path: '/login',
            name: 'login',
            component: ()=> import('../views/LoginView.vue'),
        },
        {
            path: '/accounts',
            name: 'accounts',
            component: ()=> import('../views/AccountView.vue'),
        },
        {
            path: '/accounts/create',
            name: 'accounts/create',
            component: ()=> import('../views/AccountView.vue'),
        },
        {
            path: '/transactions',
            name: 'transactions',
            component: ()=> import('../views/TransactionView.vue'),
        }

    ]
}); // ; JS에서도 꼭쓰자 약속

export default router
