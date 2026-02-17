// src/stores/account.js
import { ref } from 'vue'
import { defineStore } from 'pinia'
import api from '@/api/axios'

export const useAccountStore = defineStore('account', () => {
    const accounts = ref([])

    // 계좌 목록 조회
    async function fetchAccounts(userId) {
        const res = await api.get('/api/accounts', { params: { userId } })
        accounts.value = res.data
    }

    // 계좌 추가
    async function addAccount(account) {
        await api.post('/api/accounts', account)
    }

    // 계좌 삭제
    async function deleteAccount(id) {
        await api.delete(`/api/accounts/${id}`)
    }

    return { accounts, fetchAccounts, addAccount, deleteAccount }
})