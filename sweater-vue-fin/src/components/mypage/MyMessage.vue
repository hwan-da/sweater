<template>
    <div class="container">
        <h1>My Message</h1>
        <div v-for="message in formattedMessages" :key="message.messageId" class="message-container">
            <p class="message-title">보낸 사람: {{ message.sender }}</p>
            <p class="message-tag">받는 사람: {{ message.receiver }}</p>
            <p class="message-header">메시지: {{ message.content }}</p>
            <p class="message-content">보낸 날짜: {{ message.formattedDate }}</p>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useMessageStore } from '@/stores/message';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useMessageStore();
const storeUser = useUserStore();
const router = useRouter();

const formattedMessages = ref([]);

const formatDate = (dateString) => {
    return dateString.substring(0, 10); // YYYY-MM-DD 형식으로 자르기
};

const processMessages = () => {
    formattedMessages.value = store.messageList.map((message) => ({
        ...message,
        formattedDate: formatDate(message.sentDate),
    }));
};

onMounted(async () => {
    await storeUser.getUserById();
    await store.getMessageList(storeUser.user.nickname);
    processMessages();
});
</script>

<style scoped>
.container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    font-family: Arial, sans-serif;
}

h1 {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
}

.message-container {
    border: 1px solid #ddd;
    border-radius: 8px;
    width: 40em;
    padding: 5em;
    margin-bottom: 20px;
    background-color: #f9f9f9;
    transition: transform 0.2s;
    cursor: pointer;
}

.message-container:hover {
    transform: translateY(-5px);
}

.message-title {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 10px;
}

.message-tag {
    font-size: 0.9em;
    color: #888;
    margin-bottom: 10px;
}

.message-header {
    font-size: 1.2em;
    margin-bottom: 10px;
}

.message-content {
    font-size: 1em;
    line-height: 1.5;
    color: #444;
}
</style>
