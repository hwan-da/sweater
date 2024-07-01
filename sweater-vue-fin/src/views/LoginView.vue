<template>
    <div class="container">
        <div class="login_form">
            <form @submit.prevent>
                <h1>Log In</h1>
                <div>
                    <i class="bx bx-user login__icon"></i>
                    <input
                        v-model="loginUser.userId"
                        type="text"
                        placeholder="ID"
                        class="login__input"
                    />
                </div>
                <div class="login__box">
                    <i class="bx bx-lock login__icon"></i>
                    <input
                        v-model="loginUser.password"
                        type="password"
                        placeholder="Password"
                        class="login__input"
                    />
                </div>
                <button class="login__button" @click="login()">로그인</button>
                <div>
                    <span class="login__account login__account--account"
                        >아직 계정이 없으신가요?</span
                    >
                    <span
                        class="login__signin login__signin--signup"
                        id="sign-up"
                        @click="navigateToSignUp"
                        >Sign Up</span
                    >
                </div>
            </form>
            <button class="kakao_button" @click="kakaoLogin()">카카오로 시작하기</button>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRoute, useRouter } from 'vue-router';

const store = useUserStore();

const router = useRouter();

const loginUser = ref({
    userId: '',
    password: '',
});

const login = function () {
    store.login(loginUser.value);
};

const navigateToSignUp = () => {
    router.push('/signup');
};

const kakaoLogin = function () {
    store.kakaoLogin();
};
</script>

<style scoped>
.container {
    height: 60vh;
    align-content: center;
}
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.kakao {
    width: 50px;
    height: 50px;
}

.login_form {
    width: 100%;
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
    font-size: 24px;
    text-align: center;
    margin-bottom: 30px;
}

.login_form div {
    position: relative;
    margin-bottom: 20px;
}

.login__icon {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    left: 10px;
    color: #777;
}

.login__input {
    width: 100%;
    padding: 10px 20px;
    padding-left: 40px;
    border: 1px solid #ddd;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s ease;
}

.login__input:focus {
    border-color: #3498db;
}

.login__button {
    display: block;
    width: 100%;
    padding: 10px 20px;
    background-color: #3498db;
    color: #fff;
    text-align: center;
    text-decoration: none;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.login__button:hover {
    background-color: #2980b9;
}

.login__account {
    display: block;
    text-align: center;
    margin-top: 20px;
}

.login__signin {
    display: block;
    text-align: center;
    color: #3498db;
    cursor: pointer;
    transition: color 0.3s ease;
}

.login__signin:hover {
    color: #2980b9;
}

/* Kakao button styling */
.kakao_button {
    display: block;
    width: 100%;
    padding: 10px 20px;
    background-color: #fee500;
    color: #000;
    text-align: center;
    text-decoration: none;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    font-weight: bold;
    margin-top: 10px;
}

.kakao_button:hover {
    background-color: #e4c800;
}
</style>
