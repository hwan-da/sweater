import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_AUTH_API = `http://localhost:8080/auth`;
const REST_USER_API = `http://localhost:8080/user`;

export const useUserStore = defineStore('user', () => {
    const loginUserId = ref(null);

    const kakaoLogin = async () => {
        try {
            const response = await axios.get(`${REST_AUTH_API}/login`);
            window.location.href = response.data;
            console.log(response);
        } catch (error) {}
    };

    const login = async (loginUser) => {
        try {
            const response = await axios.post(`${REST_AUTH_API}/sign-in`, loginUser);
            sessionStorage.setItem('access_token', response.data['access_token']);
            loginUserId.value = response.data.userId;
            router.push({ name: 'Home' });
        } catch (error) {}
    };

    const userId = ref('');

    const getToken = async (kakaoCode) => {
        try {
            const response = await axios.get(`${REST_AUTH_API}/kakao/${kakaoCode}`);
            userId.value = response.data.id;
            if (response.data.isExisted) {
                loginUserId.value = response.data.userId;
                sessionStorage.setItem('access_token', response.data['access_token']);
                router.push({ name: 'Home' });
            }
        } catch (error) {}
    };

    const user = ref({});

    const getUserById = async () => {
        const token = sessionStorage.getItem('access_token');
        if (token) {
            const id = JSON.parse(atob(token.split('.')[1])).id;
            try {
                const response = await axios.get(`${REST_USER_API}/id/${id}`);
                user.value = response.data;
            } catch (error) {}
        }
    };

    const checkId = async (userId) => {
        try {
            const response = await axios.post(`${REST_AUTH_API}/id-check/${userId}`);
            return response.status;
        } catch (error) {}
    };

    const checkNickname = async (nickname) => {
        try {
            const response = await axios.get(`${REST_USER_API}/nickname/${nickname}`);
            return response.status;
        } catch (error) {}
    };

    const sendCertification = async (emailCertification) => {
        try {
            const response = await axios.post(
                `${REST_AUTH_API}/email-certification`,
                emailCertification
            );
            return response.status;
        } catch (error) {}
    };

    const checkCertificationNumber = async (certification) => {
        try {
            const response = await axios.post(
                `${REST_AUTH_API}/check-certification`,
                certification
            );
            return response.status;
        } catch (error) {}
    };

    const kakaoSignUp = async (user) => {
        try {
            if (user.userId == '') {
                user.userId = userId.value;
            }
            const response = await axios.post(`${REST_AUTH_API}/kakao`, user);
            router.push({ name: 'Login' });
        } catch (error) {}
    };

    const signUp = async (user) => {
        try {
            const response = await axios.post(`${REST_AUTH_API}/sign-up`, user);
            router.push({ name: 'Login' });
        } catch (error) {}
    };

    const updateUserProfilePicture = async (formData) => {
        try {
            const response = await axios.post(`${REST_USER_API}/profile`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
        } catch (error) {}
    };

    const updateUserIntroduction = async (user) => {
        try {
            const response = await axios.post(`${REST_USER_API}/`, user, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
        } catch (error) {}
    };

    return {
        kakaoLogin,
        getToken,
        checkNickname,
        signUp,
        kakaoSignUp,
        user,
        userId,
        getUserById,
        checkId,
        checkCertificationNumber,
        sendCertification,
        login,
        loginUserId,
        updateUserProfilePicture,
        updateUserIntroduction,
    };
});
