<template>
    <header>
        <nav>
            <RouterLink :to="{ name: 'Home' }" @click="spinImage">
                <img src="../assets/symbol.png" :class="{ spin: isSpinning }" :style="{ scale: '50%' }" />
                Sweater
            </RouterLink>
            <div class="nav-links">
                <RouterLink :to="{ name: 'Video' }">Video</RouterLink>
                <RouterLink :to="{ name: 'BoardList' }">Board</RouterLink>
                <RouterLink :to="{ name: 'ThunderList' }">Thunder</RouterLink>
                <RouterLink :to="{ name: 'MyProfile' }" v-show="isLoggedIn">My page</RouterLink>
                <button id="logout-button" v-show="isLoggedIn" @click="logOut">Log Out</button>
                <RouterLink :to="{ name: 'Login' }" v-show="!isLoggedIn">Log in</RouterLink>
                <RouterLink :to="{ name: 'SignUp' }" v-show="!isLoggedIn">Sign Up</RouterLink>
            </div>
        </nav>
    </header>
</template>

<script setup>
import router from '@/router';
import { useUserStore } from '@/stores/user';
import { ref, watch, onMounted } from 'vue';
const storeUser = useUserStore();

const isLoggedIn = ref(false);
const isSpinning = ref(false);

onMounted(() => {
    if (sessionStorage.getItem('access_token') != null) {
        const token = sessionStorage.getItem('access_token').split('.');
        const userId = JSON.parse(atob(token[1]))['id'];
        storeUser.loginUserId = userId;
    }
});

watch(
    [() => storeUser.loginUserId, () => sessionStorage.getItem('access_token')],
    ([newLoginUserId, newAccessToken]) => {
        if (newAccessToken) {
            isLoggedIn.value = true;
        } else {
            isLoggedIn.value = false;
        }
    },
    { immediate: true }
);

const logOut = () => {
    storeUser.loginUserId = null;
    sessionStorage.removeItem('access_token');
    isLoggedIn.value = false;
    router.push({ name: 'Home' });
};

const spinImage = () => {
    isSpinning.value = true;
    setTimeout(() => {
        isSpinning.value = false;
    }, 1000); // The duration of the animation in milliseconds
};
</script>

<style scoped>
nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 18em 2em 18em;
}

.nav-links {
    display: flex;
    align-content: space-around;
}

nav a,
div {
    padding: 2em;
    color: black;
    text-decoration: none;
    vertical-align: middle;
    display: flex;
    align-items: center;
    font-weight: bold;
    transition: color 0.3s;
    font-family: GmarketSansMedium;
}

#logout-button {
    margin: 2em;
    color: black;
    text-decoration: none;
    vertical-align: middle;
    display: flex;
    align-items: center;
    font-weight: bold;
    transition: color 0.3s;
    font-family: GmarketSansMedium;
    background-color: transparent;
    border: none;
    cursor: pointer;
    white-space: nowrap;
}

div a {
    font-size: smaller;
    position: relative;
}

nav div a.router-link-active::after,
#logout-button.router-link-active::after {
    content: '';
    display: block;
    position: relative;
    bottom: -1em;
    right: 50%;
    width: 0.2em;
    height: 0.2em;
    background-color: black;
    margin: 5px auto 0;
    transform: translateX(-50%);
    animation: appear 0.3s ease-in-out;
}

nav div a:hover,
#logout-button:hover {
    color: grey;
}

@keyframes appear {
    from {
        opacity: 0;
        transform: translateY(-5px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}

.spin {
    animation: spin 0.6s linear;
}
</style>
