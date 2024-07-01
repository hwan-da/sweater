import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import VideoView from '@/views/VideoView.vue';
import BoardView from '@/views/BoardView.vue';
import ThunderView from '@/views/ThunderView.vue';
import MyPageView from '@/views/MyPageView.vue';
import LoginView from '@/views/LoginView.vue';
import SignUpView from '@/views/SignUpView.vue';
import KakaoLogin from '@/components/user/KakaoLogin.vue';

import BoardList from '@/components/board/BoardList.vue';
import BoardDetail from '@/components/board/BoardDetail.vue';
import BoardCreate from '@/components/board/BoardCreate.vue';
import BoardUpdate from '@/components/board/BoardUpdate.vue';

import ThunderList from '@/components/thunder/ThunderList.vue';
import ThunderCreate from '@/components/thunder/ThunderCreate.vue';
import ThunderDetail from '@/components/thunder/ThunderDetail.vue';
import ThunderUpdate from '@/components/thunder/ThunderUpdate.vue';

import MyProfile from '@/components/mypage/MyProfile.vue';
import MyPost from '@/components/mypage/MyPost.vue';
import MyThunder from '@/components/mypage/MyThunder.vue';
import MyLikePost from '@/components/mypage/MyLikePost.vue';
import MyLikeThunder from '@/components/mypage/MyLikeThunder.vue';
import MyMessage from '@/components/mypage/MyMessage.vue';

import About from '@/components/footer/About.vue';
import Intro from '@/components/footer/Intro.vue';
import Tech from '@/components/footer/Tech.vue';

const isAuthenticated = () => {
    return sessionStorage.getItem('access_token') !== null ? true : false;
};

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    scrollBehavior() {
        return { top: 0 };
    },
    routes: [
        {
            path: '/',
            name: 'Home',
            component: HomeView,
        },
        {
            path: '/video',
            name: 'Video',
            component: VideoView,
        },
        {
            path: '/board',
            name: 'Board',
            component: BoardView,
            children: [
                {
                    path: '',
                    name: 'BoardList',
                    component: BoardList,
                },
                {
                    path: '/board/:boardId',
                    name: 'BoardDetail',
                    component: BoardDetail,
                },
                {
                    path: '/board/create',
                    name: 'BoardCreate',
                    component: BoardCreate,
                    beforeEnter: (to, from, next) => {
                        if (isAuthenticated()) next();
                        else {
                            window.alert('올바른 경로가 아닙니다. 로그인을 진행해주세요.');
                            router.push({ name: 'Login' });
                        }
                    },
                },
                {
                    path: '/board/update',
                    name: 'BoardUpdate',
                    component: BoardUpdate,
                    beforeEnter: (to, from, next) => {
                        if (isAuthenticated()) next();
                        else {
                            window.alert('올바른 경로가 아닙니다. 로그인을 진행해주세요.');
                            router.push({ name: 'Login' });
                        }
                    },
                },
            ],
        },
        {
            path: '/thunder',
            name: 'Thunder',
            component: ThunderView,
            children: [
                {
                    path: '',
                    name: 'ThunderList',
                    component: ThunderList,
                },
                {
                    path: '/thunder/:thunderId',
                    name: 'ThunderDetail',
                    component: ThunderDetail,
                },
                {
                    path: '/thunder/create',
                    name: 'ThunderCreate',
                    component: ThunderCreate,
                    beforeEnter: (to, from, next) => {
                        if (isAuthenticated()) next();
                        else {
                            window.alert('올바른 경로가 아닙니다. 로그인을 진행해주세요.');
                            router.push({ name: 'Login' });
                        }
                    },
                },
                {
                    path: '/thunder/update',
                    name: 'ThunderUpdate',
                    component: ThunderUpdate,
                    beforeEnter: (to, from, next) => {
                        if (isAuthenticated()) next();
                        else {
                            window.alert('올바른 경로가 아닙니다. 로그인을 진행해주세요.');
                            router.push({ name: 'Login' });
                        }
                    },
                },
            ],
        },
        {
            path: '/mypage',
            name: 'MyPage',
            component: MyPageView,
            beforeEnter: (to, from, next) => {
                if (isAuthenticated()) next();
                else {
                    window.alert('올바른 경로가 아닙니다. 로그인을 진행해주세요.');
                    router.push({ name: 'Login' });
                }
            },
            children: [
                {
                    path: '',
                    name: 'MyProfile',
                    component: MyProfile,
                },
                {
                    path: '/mypage/mypost',
                    name: 'MyPost',
                    component: MyPost,
                },
                {
                    path: '/mypage/mythunder',
                    name: 'MyThunder',
                    component: MyThunder,
                },
                {
                    path: '/mypage/mylikepost',
                    name: 'MyLikePost',
                    component: MyLikePost,
                },
                {
                    path: '/mypage/mylikethunder',
                    name: 'MyLikeThunder',
                    component: MyLikeThunder,
                },
                {
                    path: '/mypage/mymessage',
                    name: 'MyMessage',
                    component: MyMessage,
                },
            ],
        },
        {
            path: '/login',
            name: 'Login',
            component: LoginView,
        },
        {
            path: '/signup',
            name: 'SignUp',
            component: SignUpView,
        },
        {
            path: '/kakao',
            name: 'Kakao',
            component: KakaoLogin,
        },
        {
            path: '/about',
            name: 'About',
            component: About,
        },
        {
            path: '/intro',
            name: 'Intro',
            component: Intro,
        },
        {
            path: '/tech',
            name: 'Tech',
            component: Tech,
        },
    ],
});

export default router;
