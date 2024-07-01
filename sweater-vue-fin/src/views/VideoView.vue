<template>
    <div class="container">
        <VideoSearch @search="handleSearch" />
        <VideoDetail ref="videoView" />
        <VideoList @handleClickVideo="scrollToDetail" ref="videoList" />
        <Ai />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import VideoSearch from '@/components/video/VideoSearch.vue';
import VideoDetail from '@/components/video/VideoDetail.vue';
import VideoList from '@/components/video/VideoList.vue';
import Ai from '@/components/video/Ai.vue';

const videoList = ref(null);
const videoView = ref(null);

// 검색 결과 목록 나오면 거기로 스크롤 다운
const handleSearch = () => {
    if (videoList.value) {
        videoList.value.$el.scrollIntoView({ behavior: 'smooth' });
    }
};

// 동영상 하나 선택하면 화면으로 스크롤 업
const scrollToDetail = () => {
    if (videoView.value) {
        videoView.value.$el.scrollIntoView({ behavior: 'smooth' });
    }
};
</script>

<style scoped>
.container {
    margin: 0 20em 0 20em;
}
</style>
