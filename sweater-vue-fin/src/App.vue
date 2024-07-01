<template>
    <div class="page-container">
        <div class="main-content">
            <TheHeaderNav />
            <RouterView />
        </div>
        <TheFooter />
    </div>
</template>

<script setup>
import TheHeaderNav from '@/common/TheHeaderNav.vue';
import TheFooter from '@/common/TheFooter.vue';
</script>

<style scoped>
@import '@/assets/main.css';

.main-content {
    margin: auto;
    height: auto;
}

.TheFooter {
    position: relative;
    transform: translateY(-100%);
}
</style>
