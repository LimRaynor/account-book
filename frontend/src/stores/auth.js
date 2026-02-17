// src/stores/auth.js
import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
    // 로그인한 유저 정보 (null이면 비로그인)
    const user = ref(null)

    // 로그인 성공 시 호출
    function setUser(userData) {
        user.value = userData
    }

    // 로그아웃
    function clearUser() {
        user.value = null
    }

    return { user, setUser, clearUser }
})