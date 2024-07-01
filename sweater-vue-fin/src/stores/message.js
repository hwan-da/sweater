import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_MESSAGE_API = `http://localhost:8080/message`;

export const useMessageStore = defineStore('message', () => {
    const messageList = ref([]);

    const getMessageList = async (userId) => {
        try {
            const response = await axios.get(`${REST_MESSAGE_API}/${userId}`);
            messageList.value = response.data;
        } catch (error) {}
    };

    return { messageList, getMessageList };
});
