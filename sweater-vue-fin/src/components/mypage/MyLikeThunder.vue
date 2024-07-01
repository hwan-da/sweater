<template>
    <div class="container">
        <h1>My Like Thunder</h1>
        <div
            v-for="thunder in store.thunderHeartList"
            :key="thunder.thunderId"
            class="post-container"
            @click="navigateToThunder(thunder.thunderId)"
        >
            <p class="post-title">{{ thunder.title }}</p>
            <p class="post-tag">{{ thunder.tag }}</p>
            <p class="post-header">{{ thunder.header }}</p>
            <p class="post-content" v-html="thunder.content"></p>
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useThunderStore } from '@/stores/thunder';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useThunderStore();
const storeUser = useUserStore();
const router = useRouter();

onMounted(async () => {
    await storeUser.getUserById();
    await store.selectThunderByHeart(storeUser.loginUserId);
});

const navigateToThunder = (thunderId) => {
    router.push(`/thunder/${thunderId}`);
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
