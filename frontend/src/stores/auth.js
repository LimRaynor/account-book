import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null)
    const token = ref(localStorage.getItem('token') || null)

    function login(tokenResponse) {
        // tokenResponse = { accessToken, refreshToken }
        token.value = tokenResponse.accessToken
        localStorage.setItem('token', tokenResponse.accessToken)
    }

    function logout() {
        user.value = null
        token.value = null
        localStorage.removeItem('token')
    }

    function setUser(userData) {
        user.value = userData
    }

    return { user, token, login, logout, setUser }
})
