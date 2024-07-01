import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_COMMENT_API = `http://localhost:8080/comment`;

export const useCommentStore = defineStore('comment', () => {
    const commentList = ref([]);

    const getCommentList = async (thunderId) => {
        try {
            const response = await axios.get(`${REST_COMMENT_API}/${thunderId}`);
            commentList.value = response.data;
        } catch (error) {}
    };

    const createComment = async (thunderId, comment) => {
        try {
            await axios.post(`${REST_COMMENT_API}/${thunderId}`, comment, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getCommentList(thunderId);
        } catch (error) {console.log(error)}
    };

    const modifyComment = async (thunderId, commentId) => {
        try {
            await axios.put(`${REST_COMMENT_API}/${thunderId}/${commentId}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getCommentList(thunderId);
        } catch (error) {}
    };

    const deleteComment = async (commentId) => {
        try {
            await axios.delete(`${REST_COMMENT_API}/${commentId}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getCommentList(thunderId);
        } catch (error) {}
    };

    const invite = async (message) => {
        try {
            console.log(message);
            await axios.post(`${REST_COMMENT_API}/invite`, message, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
        } catch (error) {}
    };

    return { commentList, getCommentList, createComment, modifyComment, deleteComment, invite };
});
