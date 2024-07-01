import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useVideoStore = defineStore('video', () => {
    const videoList = ref([]);
    const selectedVideo = ref(null);

    const youtubeSearch = (keyword) => {
        const URL = 'https://www.googleapis.com/youtube/v3/search';
        const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

        axios({
            url: URL,
            method: 'GET',
            params: {
                key: API_KEY,
                part: 'snippet',
                maxResults: 12,
                q: keyword,
                type: 'video',
            },
        })
            .then((response) => {
                videoList.value = response.data.items;
                console.log(response.data.items);
            })
            .catch((err) => {
                console.error(err);
            });
    };

    const clickVideo = (video) => {
        selectedVideo.value = video;
    };

    return { videoList, selectedVideo, youtubeSearch, clickVideo };
});
