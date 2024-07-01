<template>
    <div class="search-container">
        <input type="text" v-model="keyword" @keyup.enter="search" class="search-input" />
        <button @click="search" class="search-button">Search</button>
    </div>
    <h1>선택하신 동영상이 나타납니다</h1>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import { useVideoStore } from '@/stores/video.js';

const emit = defineEmits(['search']);

const store = useVideoStore();

const keyword = ref('');

const search = () => {
    store.youtubeSearch(keyword.value);
    emit('search');
};
</script>

<style scoped>
.search-container {
    display: flex;
    align-items: center;
    justify-content: center;
    max-width: 400px;
    margin: 0 auto;
}

.search-input {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    flex: 1;
    margin-right: 0.5rem;
}

.search-button {
    padding: 0.5rem 1rem;
    font-size: 1rem;
    background-color: #dc3545;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.search-button:hover {
    background-color: #c82333;
}
</style>
