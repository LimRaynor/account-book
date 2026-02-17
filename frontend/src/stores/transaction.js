import { ref } from 'vue'
import { defineStore } from 'pinia'
import api from '@/api/axios'

export const useTransactionStore = defineStore('transaction', () => {
    const transactions = ref([])

    // 거래 목록 조회
    async function fetchTransactions(accountId) {
        const res = await api.get('/api/transactions', { params: { accountId } })
        transactions.value = res.data
    }

    // 거래 추가
    async function addTransaction(transaction) {
        await api.post('/api/transactions', transaction)
    }

    // 거래 삭제
    async function deleteTransaction(id) {
        await api.delete(`/api/transactions/${id}`)
    }

    return { transactions, fetchTransactions, addTransaction, deleteTransaction }
})
