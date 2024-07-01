<template>
    <div class="video-container">
        <iframe
            :src="videoURL"
            title="YouTube video player"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerpolicy="strict-origin-when-cross-origin"
            allowfullscreen
        ></iframe>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video.js';
import { computed } from 'vue';

const store = useVideoStore();

const videoURL = computed(() => {
    const video = store.selectedVideo;
    if (video && video.id && video.id.videoId) {
        return `https://www.youtube.com/embed/${video.id.videoId}`;
    }
    return null;
});
</script>

<style scoped>
.video-container {
    position: relative;
    width: 100%;
    scale: 100%;
    padding-bottom: 56.25%;
    height: 0;
    overflow: hidden;
    margin: 2em auto;
    border: solid;
}

iframe {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
