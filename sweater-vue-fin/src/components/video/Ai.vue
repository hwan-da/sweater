<template>
    <div class="chatbot_container">
        <button class="toggle_button" @click="toggleChatbot">{{ isOpen ? 'v' : '^' }}</button>
        <div :class="['chatbot', { open: isOpen }]">
            <form @submit.prevent>
                <h1>Sweater ChatBot</h1>
                <div>
                    <input
                        v-model="requestMessage.message"
                        type="textarea"
                        placeholder="가고 싶은 산에 대해 질문해보세요"
                        class="login__input"
                    />
                </div>
                <button class="login__button" @click="sendRequest">보내기</button>
                <div v-if="responseMessage" class="response_message">
                    <div v-for="line in formattedResponse" :key="line">{{ line }}</div>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import axios from 'axios';

const requestMessage = ref({
    message: '',
});

const responseMessage = ref('');
const isOpen = ref(false);

const toggleChatbot = () => {
    isOpen.value = !isOpen.value;
};

const sendRequest = async () => {
    try {
        const formData = new FormData();
        formData.append('message', JSON.stringify(requestMessage.value));
        const response = await axios.post('http://localhost:8080/chat/send', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
        responseMessage.value = response.data.choices[0].message.content;
    } catch (error) {
        console.log(error);
    }
};

const formattedResponse = computed(() => {
    return responseMessage.value
        .split('. ')
        .map((line) => line.trim())
        .filter((line) => line.length > 0);
});
</script>

<style scoped>
.chatbot_container {
    position: fixed;
    bottom: 0;
    right: 0;
    display: flex;
    flex-direction: row;
    width: 100%;
    justify-content: space-between;
    align-items: flex-end;
    margin: 10px;
}

.toggle_button {
    padding: 10px 20px;
    background-color: #5cb85c;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s;
    position: fixed;
    bottom: 20px;
    left: 20px;
}

.toggle_button:hover {
    background-color: #4cae4c;
}

.chatbot {
    position: fixed;
    bottom: 0;
    right: 0;
    width: 400px;
    max-width: 90%;
    height: 600px; /* increased height */
    max-height: 90%; /* increased height */
    background: white;
    border-radius: 8px 8px 0 0;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transform: translateY(100%);
    transition: transform 0.3s ease-out;
}

.chatbot.open {
    transform: translateY(0);
}

form {
    display: flex;
    flex-direction: column;
    padding: 20px;
    height: 100%;
    box-sizing: border-box;
}

h1 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
}

.login__icon {
    font-size: 24px;
    color: #888;
}

.login__input {
    width: calc(100% - 40px);
    padding: 10px 20px;
    margin: 10px 0;
    border: 1px solid #ddd;
    border-radius: 4px;
    outline: none;
    font-size: 16px;
    transition: border-color 0.3s;
}

.login__input:focus {
    border-color: #a0d8ef;
}

.login__button {
    width: 100%;
    padding: 10px;
    margin-top: auto;
    background-color: #5cb85c;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.login__button:hover {
    background-color: #4cae4c;
}

.response_message {
    margin-top: 20px;
    padding: 10px;
    background-color: #f1f1f1;
    border-radius: 4px;
    text-align: left;
}
</style>
