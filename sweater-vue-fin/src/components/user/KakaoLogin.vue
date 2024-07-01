<template>
    <div>
        <h1>KAKAO</h1>
        <div class="login_form">
            <form @submit.prevent>
                <h1>Create Account</h1>
                <div>
                    <i class="bx bx-user login__icon"></i>
                    <input
                        v-model="user.nickname"
                        type="text"
                        placeholder="Nickname"
                        class="login__input"
                    />
                </div>
                <div>
                    <button @click="checkNickname(user.nickname)" class="login__button">
                        중복확인
                    </button>
                </div>
                <div id="nickname-feedback" :class="['feedback', { valid: nicknameValid }]">
                    {{ nicknameFeedback }}
                </div>
                <div class="gender__box">
                    <div>
                        <i class="bx bx-male-sign"></i>
                        <input
                            v-model="user.gender"
                            type="radio"
                            class="login__input"
                            value="남성"
                        />
                    </div>
                    <div>
                        <i class="bx bx-female-sign"></i>
                        <input
                            v-model="user.gender"
                            type="radio"
                            class="login__input"
                            value="여성"
                        />
                    </div>
                </div>
                <button @click="kakaoSignUp()" class="login__button">Sign Up</button>
                <div>
                    <span class="login__account login__account--account"
                        >이미 계정이 있으신가요?</span
                    >
                    <span
                        class="login__signin login__signin--signup"
                        id="sign-up"
                        @click="navigateToLogin"
                        >Log In</span
                    >
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const route = useRoute();
const router = useRouter();

const user = ref({
    userId: store.userId,
    password: 'q!w@e#r$',
    nickname: '',
    gender: '남성',
});

onMounted(() => {
    const kakaoCode = route.query.code;
    store.getToken(kakaoCode);
});

const nicknameFeedback = ref('');
const nicknameValid = ref(false);

const checkNickname = async (nickname) => {
    try {
        const status = await store.checkNickname(nickname);
        if (status === 204) {
            nicknameFeedback.value = '사용할 수 있는 닉네임입니다.';
            nicknameValid.value = true;
        } else {
            nicknameFeedback.value = '사용할 수 없는 닉네임입니다.';
            nicknameValid.value = false;
        }
    } catch (error) {
        nicknameFeedback.value = '사용할 수 없는 닉네임입니다.';
        nicknameValid.value = false;
    }
};

const kakaoSignUp = function () {
    store.kakaoSignUp(user.value);
};

const navigateToLogin = () => {
    router.push('/login');
};
</script>

<style scoped>
/* Reset default margin and padding */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.gender__box {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    margin-bottom: 20px;
}

/* Login form container */
.login_form {
    width: 100%;
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* Heading */
h1 {
    font-size: 24px;
    text-align: center;
    margin-bottom: 30px;
}

/* Input container */
.login_form div {
    position: relative;
    margin-bottom: 20px;
}

/* Icon styles */
.login__icon {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    left: 10px;
    color: #777;
}

/* Input styles */
.login__input {
    width: 100%;
    padding: 10px 20px;
    padding-left: 40px;
    border: 1px solid #ddd;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s ease;
}

/* Input focus styles */
.login__input:focus {
    border-color: #3498db;
}

/* Button styles */
.login__button {
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
    font-weight: bold;
    transition: background-color 0.3s ease;
}

/* Button hover styles */
.login__button:hover {
    background-color: #e4c800;
}

/* Account text styles */
.login__account {
    display: block;
    text-align: center;
    margin-top: 20px;
}

/* Sign up link styles */
.login__signin {
    display: block;
    text-align: center;
    color: #3498db;
    cursor: pointer;
    transition: color 0.3s ease;
}

/* Sign up link hover styles */
.login__signin:hover {
    color: #2980b9;
}

.feedback {
    font-size: 12px;
    margin-top: -15px;
    margin-bottom: 10px;
    text-align: center;
}

.feedback.valid {
    color: green;
}

.feedback:not(.valid) {
    color: red;
}
</style>
