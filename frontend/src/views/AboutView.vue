<template>
  <div class="card about-card">
    <h1>About</h1>
    <p>Vue 3 + Spring Boot로 만든 가계부 애플리케이션입니다.</p>
    <p class="sub">계좌 관리와 수입/지출 거래 내역을 기록할 수 있습니다.</p>
  </div>
</template>

<style scoped>
.about-card {
  text-align: center;
  padding: 60px 32px;
}

.about-card p {
  font-size: 16px;
  margin-bottom: 8px;
}

.about-card .sub {
  color: var(--color-text-muted);
  font-size: 14px;
}
</style>
