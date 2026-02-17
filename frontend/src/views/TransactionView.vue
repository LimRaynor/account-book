<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useAccountStore } from '@/stores/account'
import { useTransactionStore } from '@/stores/transaction'

const authStore = useAuthStore()
const accountStore = useAccountStore()
const transactionStore = useTransactionStore()

// 선택된 계좌 ID
const selectedAccountId = ref(null)

// 거래 추가용 입력값
const type = ref('')          // 'INCOME' 또는 'EXPENSE'
const category = ref('')      // 식비, 교통비 등
const amount = ref(0)
const description = ref('')
const date = ref('')          // '2026-02-17' 형식

// 페이지 열릴 때 계좌 목록 조회
onMounted(() => {
  accountStore.fetchAccounts(authStore.user.userId)
})

// 계좌 선택 시 거래 목록 조회
async function onAccountChange() {
  if (selectedAccountId.value) {
    await transactionStore.fetchTransactions(selectedAccountId.value)
  }
}

// 거래 추가
async function handleAddTransaction() {
  await transactionStore.addTransaction({
    accountId: selectedAccountId.value,
    type: type.value,
    category: category.value,
    amount: amount.value,
    description: description.value,
    date: date.value
  })
  await transactionStore.fetchTransactions(selectedAccountId.value)
  // 입력값 초기화
  type.value = ''
  category.value = ''
  amount.value = 0
  description.value = ''
  date.value = ''
}

// 거래 삭제
async function handleDeleteTransaction(id) {
  await transactionStore.deleteTransaction(id)
  await transactionStore.fetchTransactions(selectedAccountId.value)
}
</script>

<template>
  <h1>거래 내역</h1>

  <!-- 계좌 선택 드롭다운 -->
  <select v-model="selectedAccountId" @change="onAccountChange">
    <option disabled :value="null">계좌를 선택하세요</option>
    <option v-for="account in accountStore.accounts"
            :key="account.accountId"
            :value="account.accountId">
      {{ account.name }}
    </option>
  </select>

  <!-- 거래 추가 폼 (계좌 선택 후에만 보임) -->
  <form v-if="selectedAccountId" @submit.prevent="handleAddTransaction">
    <select v-model="type" required>
      <option disabled value="">유형 선택</option>
      <option value="INCOME">수입</option>
      <option value="EXPENSE">지출</option>
    </select>
    <input v-model="category" placeholder="카테고리" required />
    <input v-model.number="amount" type="number" placeholder="금액" required />
    <input v-model="description" placeholder="설명" />
    <input v-model="date" type="date" required />
    <button type="submit">추가</button>
  </form>

  <!-- 거래 목록 -->
  <ul>
    <li v-for="t in transactionStore.transactions" :key="t.tranId">
      [{{ t.type }}] {{ t.category }} — {{ t.amount }}원 ({{ t.date }})
      <button @click="handleDeleteTransaction(t.tranId)">삭제</button>
    </li>
  </ul>
</template>
