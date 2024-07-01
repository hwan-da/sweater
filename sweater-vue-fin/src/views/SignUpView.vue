<template>
    <div class="login_form">
        <form @submit.prevent>
            <h1>Create Account</h1>
            <div>
                <i class="bx bx-user login__icon"></i>
                <input
                    v-model="user.userId"
                    type="text"
                    placeholder="ID"
                    class="login__input"
                    aria-describedby="id-feedback"
                />
            </div>
            <div>
                <button @click="checkId(user.userId)" class="login__button" type="button">중복확인</button>
            </div>
            <div id="id-feedback" :class="['feedback', { valid: idValid }]">{{ idFeedback }}</div>
            <div class="login__box">
                <i class="bx bx-lock login__icon"></i>
                <input
                    v-model="user.password"
                    type="password"
                    placeholder="Password"
                    class="login__input"
                    aria-describedby="password-feedback"
                />
            </div>
            <div class="login__box">
                <i class="bx bx-lock login__icon"></i>
                <input
                    v-model="confirmPassword"
                    type="password"
                    placeholder="Password Check"
                    class="login__input"
                    @input="checkPasswordsMatch"
                    aria-describedby="password-feedback"
                />
            </div>
            <div id="password-feedback" :class="['feedback', { valid: passwordsMatch }]">
                {{ passwordFeedback }}
            </div>
            <div>
                <i class="bx bx-user login__icon"></i>
                <input
                    v-model="user.nickname"
                    type="text"
                    placeholder="Nickname"
                    class="login__input"
                    aria-describedby="nickname-feedback"
                />
            </div>
            <div>
                <button @click="checkNickname(user.nickname)" class="login__button" type="button">중복확인</button>
            </div>
            <div id="nickname-feedback" :class="['feedback', { valid: nicknameValid }]">
                {{ nicknameFeedback }}
            </div>
            <div class="gender__box">
                <div>
                    <i class="bx bx-male-sign"></i>
                    <input v-model="user.gender" type="radio" class="login__input" value="남성" />
                </div>
                <div>
                    <i class="bx bx-female-sign"></i>
                    <input v-model="user.gender" type="radio" class="login__input" value="여성" />
                </div>
            </div>
            <div>
                <i class="bx bx-user login__icon"></i>
                <input
                    v-model="certification.email"
                    type="email"
                    placeholder="Email"
                    class="login__input"
                    aria-describedby="email-feedback"
                />
            </div>
            <div>
                <button @click="sendCertification(certification.email)" class="login__button" type="button">
                    인증번호 발송
                </button>
            </div>
            <div id="email-feedback" :class="['feedback', { valid: emailSent }]">
                {{ emailFeedback }}
            </div>
            <div>
                <i class="bx bx-user login__icon"></i>
                <input
                    v-model="certification.certificationNumber"
                    type="text"
                    placeholder="Certification Number"
                    class="login__input"
                    aria-describedby="certification-feedback"
                />
            </div>
            <div>
                <button @click="checkCertificationNumber()" class="login__button" type="button">인증번호 확인</button>
            </div>
            <div id="certification-feedback" :class="['feedback', { valid: certificationValid }]">
                {{ certificationFeedback }}
            </div>
            <button @click="signUp(user.userId)" class="login__button">Sign Up</button>
            <div>
                <span class="login__account login__account--account">이미 계정이 있으신가요?</span>
                <span class="login__signin login__signin--signup" id="sign-up" @click="navigateToLogin">Log In</span>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const store = useUserStore();
const router = useRouter();

const user = ref({
    userId: '',
    password: '',
    nickname: '',
    gender: '',
    certificationNumber: '',
    email: '',
});

const emailCertification = ref({
    userId: '',
    email: '',
});

const certification = ref({
    userId: '',
    email: '',
    certificationNumber: '',
});

const confirmPassword = ref('');
const idFeedback = ref('');
const idValid = ref(false);
const nicknameFeedback = ref('');
const nicknameValid = ref(false);
const passwordFeedback = ref('비밀번호를 입력하세요');
const passwordsMatch = ref(false);
const emailFeedback = ref('');
const emailSent = ref(false);
const certificationFeedback = ref('');
const certificationValid = ref(false);

const checkId = async (userId) => {
    try {
        const status = await store.checkId(userId);
        if (status === 200) {
            idFeedback.value = '사용할 수 있는 아이디입니다.';
            idValid.value = true;
        } else {
            idFeedback.value = '사용할 수 없는 아이디입니다.';
            idValid.value = false;
        }
    } catch (error) {
        idFeedback.value = '사용할 수 없는 아이디입니다.';
        idValid.value = false;
    }
};

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

const checkPasswordsMatch = () => {
    if (user.value.password === confirmPassword.value) {
        passwordFeedback.value = '비밀번호가 일치합니다.';
        passwordsMatch.value = true;
    } else {
        passwordFeedback.value = '비밀번호를 확인해 주세요.';
        passwordsMatch.value = false;
    }
};

const sendCertification = async (email) => {
    try {
        emailCertification.value.userId = user.value.userId
        emailCertification.value.email = email;
        const status = await store.sendCertification(emailCertification.value);
        if (status === 200) {
            emailFeedback.value = '인증번호가 발송되었습니다.';
            emailSent.value = true;
        } else {
            emailFeedback.value = '인증번호 발송에 실패했습니다.';
            emailSent.value = false;
        }
    } catch (error) {
        emailFeedback.value = '인증번호 발송에 실패했습니다.';
        emailSent.value = false;
    }
};

const checkCertificationNumber = async () => {
    try {
        certification.value.userId = user.value.userId;
        const status = await store.checkCertificationNumber(certification.value);
        if (status === 200) {
            certificationFeedback.value = '인증되었습니다.';
            certificationValid.value = true;
        } else {
            certificationFeedback.value = '인증이 거부되었습니다.';
            certificationValid.value = false;
        }
    } catch (error) {
        certificationFeedback.value = '인증이 거부되었습니다.';
        certificationValid.value = false;
    }
};

const signUp = () => {
    user.value.email = certification.value.email;
    user.value.certificationNumber = certification.value.certificationNumber;
    store.signUp(user.value);
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
    background-color: #3498db;
    color: #fff;
    text-align: center;
    text-decoration: none;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

/* Button hover styles */
.login__button:hover {
    background-color: #2980b9;
}

/* Feedback text styles */
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
</style>
