<template>
    <li @click="clickVideo">
        <!-- 썸네일 해상도 high로 가져옴 -->
        <img :src="video.snippet.thumbnails.high.url" />
        <span>{{ video.snippet.title }}</span>
    </li>
</template>

<script setup>
import { useVideoStore } from '@/stores/video.js';
import { defineProps, defineEmits } from 'vue';

const store = useVideoStore();

const props = defineProps({
    video: {
        type: Object,
        required: true,
    },
});

const emit = defineEmits(['clickVideo']);

// view에서 스크롤 핸들링위해 emit
const clickVideo = () => {
    store.clickVideo(props.video);
    emit('clickVideo');
};
</script>

<style scoped>
li {
    display: flex;
    flex-direction: column;
    border: 1px solid #ccc;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}
li:hover {
    background-color: #f0f0f0;
}

li img {
    width: 100%;
    height: auto;
    border-bottom: 1px solid #ccc;
}

li span {
    padding: 0.5rem;
    font-size: 1rem;
    font-weight: bold;
}
</style>
