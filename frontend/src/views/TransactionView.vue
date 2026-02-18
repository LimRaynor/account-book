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
  <div class="card">
    <h1>거래 내역</h1>

    <div class="form-group">
      <label>계좌 선택</label>
      <select v-model="selectedAccountId" @change="onAccountChange">
        <option disabled :value="null">계좌를 선택하세요</option>
        <option v-for="account in accountStore.accounts"
                :key="account.accountId"
                :value="account.accountId">
          {{ account.name }}
        </option>
      </select>
    </div>
  </div>

  <div v-if="selectedAccountId" class="card">
    <h2>거래 추가</h2>
    <form class="tran-form" @submit.prevent="handleAddTransaction">
      <div class="form-row">
        <div class="form-group">
          <label>유형</label>
          <select v-model="type" required>
            <option disabled value="">유형 선택</option>
            <option value="INCOME">수입</option>
            <option value="EXPENSE">지출</option>
          </select>
        </div>
        <div class="form-group">
          <label>카테고리</label>
          <input v-model="category" placeholder="식비, 교통비 등" required />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>금액</label>
          <input v-model.number="amount" type="number" placeholder="금액" required />
        </div>
        <div class="form-group">
          <label>날짜</label>
          <input v-model="date" type="date" required />
        </div>
      </div>
      <div class="form-group">
        <label>설명</label>
        <input v-model="description" placeholder="메모 (선택)" />
      </div>
      <button type="submit">추가</button>
    </form>
  </div>

  <div v-if="selectedAccountId" class="card list-card">
    <h2>거래 목록</h2>
    <ul class="item-list">
      <li v-for="t in transactionStore.transactions" :key="t.tranId">
        <div class="item-info">
          <span class="type-badge" :class="t.type === 'INCOME' ? 'income' : 'expense'">
            {{ t.type === 'INCOME' ? '수입' : '지출' }}
          </span>
          <span class="item-name">{{ t.category }}</span>
          <span class="item-amount">{{ t.amount.toLocaleString() }}원</span>
          <span class="item-date">{{ t.date }}</span>
        </div>
        <button class="btn-delete" @click="handleDeleteTransaction(t.tranId)">삭제</button>
      </li>
    </ul>
    <p v-if="transactionStore.transactions.length === 0" class="empty">거래 내역이 없습니다.</p>
  </div>
</template>

<style scoped>
.card + .card {
  margin-top: 20px;
}

h2 {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 16px;
  color: var(--color-dark);
}

.form-row {
  display: flex;
  gap: 12px;
}

.form-row .form-group {
  flex: 1;
}

.tran-form button {
  width: 100%;
  margin-top: 8px;
  padding: 12px;
}

.item-list {
  list-style: none;
  padding: 0;
}

.item-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid var(--color-border);
}

.item-list li:last-child {
  border-bottom: none;
}

.item-info {
  display: flex;
  gap: 12px;
  align-items: center;
}

.type-badge {
  padding: 2px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}

.type-badge.income {
  background: #E8F5E9;
  color: #2E7D32;
}

.type-badge.expense {
  background: #FFEBEE;
  color: #C62828;
}

.item-name {
  font-weight: 600;
}

.item-amount {
  font-weight: 600;
}

.item-date {
  color: var(--color-text-muted);
  font-size: 13px;
}

.empty {
  text-align: center;
  color: var(--color-text-muted);
  padding: 24px 0;
}
</style>
