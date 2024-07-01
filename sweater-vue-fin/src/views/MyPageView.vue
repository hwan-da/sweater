<template>
    <div class="mypage-container">
        <div class="container">
            <div class="mypage-nav">
                <div class="menu-title">Menu</div>
                <RouterLink :to="{ name: 'MyProfile' }">My Profile</RouterLink>
                <RouterLink :to="{ name: 'MyPost' }">My Post</RouterLink>
                <RouterLink :to="{ name: 'MyThunder' }">My Thunder</RouterLink>
                <RouterLink :to="{ name: 'MyLikePost' }">My Like Post</RouterLink>
                <RouterLink :to="{ name: 'MyLikeThunder' }">My Like Thunder</RouterLink>
                <RouterLink :to="{ name: 'MyMessage' }">My Message</RouterLink>
            </div>
        </div>
        <RouterView />
    </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount } from 'vue';
import { RouterView } from 'vue-router';

onMounted(() => {
    document.body.style.backgroundColor = '#eee5cd';
    document.querySelector('footer').style.backgroundColor = '#eee5cd';
});

onBeforeUnmount(() => {
    document.body.style.backgroundColor = '';
    document.querySelector('footer').style.backgroundColor = '';
});
</script>

<style scoped>
.mypage-container {
    display: flex;
    flex-direction: row;
    padding: 3em 12em 3em 12em;
    height: 100%;
}

.container {
    display: block;
    padding: 0 5em 0 5em;
}

.menu-title {
    text-align: center;
    font-size: x-large;
}

.mypage-nav {
    display: flex;
    flex-direction: column;
    gap: 4em;
}

.mypage-nav a {
    display: block;
    text-decoration: none;
    color: black;
}
</style>
