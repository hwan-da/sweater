<template>
    <div class="video-list-container">
        <h1>검색 결과가 아래에 나옵니다</h1>
        <ul>
            <VideoListItem
                v-for="video in store.videoList"
                :key="video.id.videoId"
                :video="video"
                @clickVideo="handleClickVideo"
            />
        </ul>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video.js';
import { defineEmits } from 'vue';
import VideoListItem from '@/components/video/VideoListItem.vue';

const emit = defineEmits(['handleClickVideo']);

// view에서 스크롤 핸들링하기 위해서 한번 더 emit
const handleClickVideo = () => {
    emit('handleClickVideo');
};

const store = useVideoStore();
</script>

<style scoped>
ul {
    list-style: none;
    padding: 0;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 1rem;
}

@media (min-width: 1024px) {
    ul {
        grid-template-columns: repeat(4, 1fr);
    }
}

@media (min-width: 768px) and (max-width: 1023px) {
    ul {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (min-width: 480px) and (max-width: 767px) {
    ul {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 479px) {
    ul {
        grid-template-columns: 1fr;
    }
}
</style>
