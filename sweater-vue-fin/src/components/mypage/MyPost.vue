<template>
    <div class="container">
        <h1>My Post</h1>
        <div
            v-for="board in store.boardList"
            :key="board.boardId"
            class="post-container"
            @click="navigateToBoard(board.boardId)"
        >
            <p class="post-title">{{ board.title }}</p>
            <p class="post-tag">{{ board.tag }}</p>
            <p class="post-header">{{ board.header }}</p>
            <p class="post-content" v-html="board.content"></p>
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useBoardStore } from '@/stores/board';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useBoardStore();
const storeUser = useUserStore();
const router = useRouter();

onMounted(async () => {
    await storeUser.getUserById();
    await store.selectBoardByNickname(storeUser.user.nickname);
});

const navigateToBoard = (boardId) => {
    router.push(`/board/${boardId}`);
};
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

.post-container {
    border: 1px solid #ddd;
    border-radius: 8px;
    width: 40em;
    padding: 5em;
    margin-bottom: 20px;
    background-color: #f9f9f9;
    transition: transform 0.2s;
    cursor: pointer;
}

.post-container:hover {
    transform: translateY(-5px);
}

.post-title {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 10px;
}

.post-tag {
    font-size: 0.9em;
    color: #888;
    margin-bottom: 10px;
}

.post-header {
    font-size: 1.2em;
    margin-bottom: 10px;
}

.post-content {
    font-size: 1em;
    line-height: 1.5;
    color: #444;
}
</style>
