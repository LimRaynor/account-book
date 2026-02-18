<script setup>
import { ref } from 'vue'
import api from '@/api/axios'

// 1. 폼 데이터를 ref로 선언
const name = ref('')
const email = ref('')
const password = ref('')

// 2. 가입 함수

async function handleSignup() {
  // 비동기 함수로 응답올때까지 기다림
  try {
    await api.post('/api/auth/signup', {
      // 백엔드로 요청하면 응답올때까지 기다림
      name: name.value,
      email: email.value,
      password: password.value
    })
    alert('회원가입 성공!')
    // 문제없이 응답받았을경우
  } catch (e) {
    alert('회원가입 실패')
    // 하나라도 에러가나서 응답에문제가 생겼을 경우
  }
}
</script>

<template>
  <div class="auth-wrapper">
    <div class="card auth-card">
      <h1>회원가입</h1>
      <form @submit.prevent="handleSignup">
        <div class="form-group">
          <label>이름</label>
          <input v-model="name" type="text" required />
        </div>
        <div class="form-group">
          <label>이메일</label>
          <input v-model="email" type="email" required />
        </div>
        <div class="form-group">
          <label>비밀번호</label>
          <input v-model="password" type="password" required />
        </div>
        <button type="submit">가입하기</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.auth-wrapper {
  display: flex;
  justify-content: center;
  padding-top: 40px;
}

.auth-card {
  width: 100%;
  max-width: 400px;
}

.auth-card button {
  width: 100%;
  margin-top: 8px;
  padding: 12px;
}
</style>
