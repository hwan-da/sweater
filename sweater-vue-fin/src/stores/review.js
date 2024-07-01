import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_REVIEW_API = `http://localhost:8080/review`;

export const useReviewStore = defineStore('review', () => {
    const reviewList = ref([]);

    const getReviewList = async (boardId) => {
        try {
            const response = await axios.get(`${REST_REVIEW_API}/${boardId}`);
            reviewList.value = response.data;
        } catch (error) {}
    };

    const createReview = async (boardId, review) => {
        try {
            await axios.post(`${REST_REVIEW_API}/${boardId}`, review, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getReviewList(boardId);
        } catch (error) {}
    };

    const modifyReview = async (boardId, reviewId) => {
        try {
            await axios.put(`${REST_REVIEW_API}/${boardId}/${reviewId}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getReviewList(boardId);
        } catch (error) {}
    };

    const deleteReview = async (reviewId) => {
        try {
            await axios.delete(`${REST_REVIEW_API}/${reviewId}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getReviewList(boardId);
        } catch (error) {}
    };

    return { reviewList, getReviewList, createReview, modifyReview, deleteReview };
});
