<script setup>

</script>

<template>
  <div class="card welcome-card">
    <h1>메인 페이지</h1>
    <p>가계부 앱에 오신 것을 환영합니다.</p>
    <p class="sub">계좌를 등록하고 거래 내역을 관리해보세요.</p>
  </div>
</template>

<style scoped>
.welcome-card {
  text-align: center;
  padding: 60px 32px;
}

.welcome-card p {
  font-size: 16px;
  color: var(--color-dark);
  margin-bottom: 8px;
}

.welcome-card .sub {
  color: var(--color-text-muted);
  font-size: 14px;
}

.welcome-card h1 {
  display: inline-block;
}
</style>
