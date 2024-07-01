<template>
    <div>
        <h1 :style="{ fontSize: '5.5em' }">
            {{ translatedRegion }}
        </h1>
        <div class="board-tool">
            <div class="left-buttons">
                <div v-show="isLoggedIn && store.selectedBoard.writer !== storeUser.user.nickname">
                    <button
                        @click="pushHeart(store.selectedBoard.boardId)"
                        :class="{ liked: isHeart }"
                        class="heart-button"
                        :style="{ border: 'none' }"
                    >
                        <i :class="isHeart ? 'fas fa-heart' : 'far fa-heart'"></i>
                        Like
                    </button>
                </div>
            </div>
            <div class="right-buttons">
                <button v-show="isMine" @click="moveUpdateForm">Modify</button>
                <button v-show="isMine" @click="deleteBoard">Delete</button>
            </div>
        </div>
        <img :src="store.selectedBoard.media" />
        <p>{{ store.selectedBoard.title }}</p>
        <p>❤ {{ heartCount }}</p>
        <p>{{ store.selectedBoard.header }}</p>
        <p>{{ formattedDateRange(store.selectedBoard.startDate, store.selectedBoard.endDate) }}</p>
        <p>{{ store.selectedBoard.writer }}</p>
        <hr />
        <h1>Course Information</h1>
        <KakaoMap :lat="centerLat" :lng="centerLng" :level="7" @onLoadKakaoMap="onLoadKakaoMap">
            <div v-for="(path, index) in latLngList" :key="index">
                <KakaoMapPolyline :latLngList="path" />
            </div>
        </KakaoMap>
        <div class="map-information">
            <p>{{ store.selectedBoard.hikingTime }}분</p>
            <p>{{ store.selectedBoard.hikingDist / 1000 }}킬로미터</p>
        </div>
        <div class="main-content" v-html="store.selectedBoard.content"></div>
        <hr />
        <h1>Comment</h1>
        <div
            class="review-container"
            v-for="review in storeReview.reviewList"
            :key="review.reviewId"
        >
            <p>{{ review.title }}</p>
            <p>{{ review.writer }}</p>
            <p>{{ review.content }}</p>
        </div>
        <button v-show="isLoggedIn" @click="createReview" id="create-button">Create</button>
        <div v-show="isLoggedIn" class="review-create-container">
            <p>제목:</p>
            <input type="text" id="review-title-creater" v-model="newReview.title" />
            <p>본문:</p>
            <input type="textarea" id="review-content-creater" v-model="newReview.content" />
        </div>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { useMountainStore } from '@/stores/mountain';
import { useReviewStore } from '@/stores/review';
import { useUserStore } from '@/stores/user';
import { ref, onMounted, computed, watch } from 'vue';
import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router';
import { KakaoMap, KakaoMapPolyline } from 'vue3-kakao-maps';

const store = useBoardStore();
const storeMnt = useMountainStore();
const storeReview = useReviewStore();
const storeUser = useUserStore();
const route = useRoute();
const router = useRouter();

const isLoggedIn = ref(false);
const isMine = ref(false);
const map = ref(null);

const heartCount = ref(0);
const isHeart = ref(false);

const newReview = ref({
    title: '',
    writer: '',
    content: '',
});

onMounted(async () => {
    await store.selectBoardById(route.params.boardId);
    await storeMnt.loadMountain(mntnCode.value);
    await storeReview.getReviewList(route.params.boardId);
    await storeUser.getUserById();
    await store.checkHeart(route.params.boardId);

    setUser();
    heartCount.value = store.selectedBoard.heart;
    isHeart.value = store.isHeart;
    updateLatLngList();
    isLoggedIn.value = sessionStorage.getItem('access_token') !== null ? true : false;
    isMine.value = store.selectedBoard.writer === storeUser.user.nickname ? true : false;
    updateBackgroundColor();
});

onBeforeRouteLeave(() => {
    const body = document.querySelector('body');
    body.style.backgroundColor = 'white';
});

const setUser = () => {
    newReview.value.writer = storeUser.user.nickname;
};

const pushHeart = (boardId) => {
    store.pushHeart(boardId);
    isHeart.value = !isHeart.value; // Toggle heart status immediately for UI responsiveness
};

watch(
    () => store.isHeart,
    (newVal) => {
        isHeart.value = newVal;
    }
);

watch(
    () => store.selectedBoard.heart,
    (newVal) => {
        heartCount.value = newVal;
    }
);

const onLoadKakaoMap = (mapRef) => {
    map.value = mapRef;
    map.value.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);
};

const mntnCode = computed(() => {
    switch (store.selectedBoard.tag) {
        case '북한산':
            return '113050202';
        case '관악산':
            return '116200201';
        case '마니산':
            return '287100601';
        case '명성산':
            return '427800701';
        case '설악산':
            return '428302602';
        case '소백산':
            return '438001301';
        case '가지산':
            return '317100101';
        case '지리산':
            return '488605302';
        case '덕유산':
            return '457300702';
        case '백운산':
            return '462300801';
        case '한라산':
            return '123123123';
    }
});

const latLngList = ref([]);
const centerLat = ref(0);
const centerLng = ref(0);

const updateLatLngList = () => {
    store.selectedBoard.trail.forEach((path) => {
        storeMnt.mountainInformation.forEach((mountain) => {
            if (path[1] === mountain.fid) {
                latLngList.value.push(mountain.paths);
            }
        });
    });
    if (latLngList.value.length > 0) {
        const middleIndex = Math.floor(latLngList.value.length / 2);
        const middlePath = latLngList.value[middleIndex];
        if (middlePath.length > 0) {
            const pathMiddleIndex = Math.floor(middlePath.length / 2);
            centerLat.value = middlePath[pathMiddleIndex].lat;
            centerLng.value = middlePath[pathMiddleIndex].lng;
        }
    }
};

const formattedDateRange = (startDateString, endDateString) => {
    const startDate = new Date(startDateString);
    const endDate = new Date(endDateString);
    const startYear = startDate.getFullYear();
    const startMonth = startDate.getMonth() + 1;
    const startDay = startDate.getDate();
    const endYear = endDate.getFullYear();
    const endMonth = endDate.getMonth() + 1;
    const endDay = endDate.getDate();

    if (startYear === endYear && startMonth === endMonth && startDay === endDay) {
        return `${startYear}년 ${startMonth}월 ${startDay}일`;
    } else if (startYear === endYear && startMonth === endMonth) {
        return `${startYear}년 ${startMonth}월 ${startDay}일 ~ ${endDay}일`;
    } else if (startYear === endYear) {
        return `${startYear}년 ${startMonth}월 ${startDay}일 ~ ${endMonth}월 ${endDay}일`;
    } else {
        return `${startYear}년 ${startMonth}월 ${startDay}일 ~ ${endYear}년 ${endMonth}월 ${endDay}일`;
    }
};

const moveUpdateForm = () => {
    router.push({ name: 'BoardUpdate' });
};

const deleteBoard = () => {
    store.deleteBoard(route.params.boardId);
    router.push({ name: 'BoardList' });
};

const createReview = () => {
    storeReview.createReview(route.params.boardId, newReview.value);
    // location.reload();
};

const updateBackgroundColor = () => {
    const region = store.selectedBoard.region;
    const body = document.body;
    switch (region) {
        case '서울':
            body.style.backgroundColor = '#c6db9d';
            break;
        case '경기':
            body.style.backgroundColor = '#f6f2fe';
            break;
        case '충청':
            body.style.backgroundColor = '#aac3da';
            break;
        case '전라':
            body.style.backgroundColor = '#ca8342';
            break;
        case '강원':
            body.style.backgroundColor = '#f9ffe3';
            break;
        case '경상':
            body.style.backgroundColor = '#a09cf9';
            break;
        case '제주':
            body.style.backgroundColor = '#ffed8c';
            break;
    }
};

const translatedRegion = computed(() => {
    switch (store.selectedBoard.region) {
        case '서울':
            return 'Seoul';
        case '경기':
            return 'Gyeonggi';
        case '충청':
            return 'Chungcheong';
        case '강원':
            return 'Gangwon';
        case '전라':
            return 'Jeolla';
        case '경상':
            return 'Gyeongsang';
        case '제주':
            return 'Jeju';
        default:
            return store.selectedBoard.region;
    }
});
</script>

<style scoped>
/* General Styles */
body {
    margin: 0;
    box-sizing: border-box;
}

/* Container for the entire board */
div {
    padding: 1rem;
    max-width: 1200px;
    margin: auto;
}

/* Header */
h1 {
    font-size: 2rem;
    text-align: center;
    margin: 1rem 0;
}

/* Board Tools (Modify, Delete, Heart buttons) */
.board-tool {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin: 1rem 0;
}

.board-tool .left-buttons,
.board-tool .right-buttons {
    display: flex;
    gap: 1rem;
}

.board-tool .right-buttons {
    margin-left: auto;
}

.board-tool button,
#create-button {
    border: solid;
    border-color: black;
    border-width: thin;
    color: black;
    padding: 0.5em 1em;
    font-size: small;
    background-color: transparent;
}

.board-tool button a,
#create-button a {
    text-decoration: none;
    color: black;
}

.right-buttons button:hover,
#create-button:hover {
    background-color: black;
    color: white;
    transition: background-color 0.5s ease, color 0.5s ease;
}

/* Heart Button Styles */
.heart-button {
    background: none;
    border: none;
    padding: 0;
}

.heart-button i {
    font-size: 1.5rem;
    transition: color 0.5s ease;
}

.heart-button.liked i {
    color: red;
    animation: heart-pulse 0.5s;
}

@keyframes heart-pulse {
    0% {
        transform: scale(1);
    }
    50% {
        transform: scale(1.2);
    }
    100% {
        transform: scale(1);
    }
}

/* Image */
img {
    width: 100%;
    height: auto;
    display: block;
    margin: 1rem 0;
}

/* Board Information */
p {
    font-size: 1rem;
    margin: 0.5rem 0;
}

hr {
    margin: 2rem 0;
}

/* Course Information */
.map-information {
    display: flex;
    justify-content: space-around;
    margin: 1rem 0;
}

.map-information p {
    font-size: 1rem;
}

/* Main Content */
.main-content {
    margin: 2rem 0;
}

/* Comment Section */
.review-container {
    border: 1px solid #ccc;
    padding: 1rem;
    margin: 1rem 0;
    border-radius: 8px;
}

.review-container p {
    margin: 0.5rem 0;
}

.review-create-container {
    margin: 2rem 0;
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.review-create-container input[type='text'],
.review-create-container input[type='textarea'] {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

/* Responsive Styles */
@media (max-width: 768px) {
    h1 {
        font-size: 1.5rem;
    }

    .board-tool {
        flex-direction: column;
    }

    .board-tool button {
        font-size: 0.9rem;
    }

    .map-information {
        flex-direction: column;
        align-items: center;
    }

    .map-information p {
        font-size: 0.9rem;
    }

    .review-create-container {
        flex-direction: column;
        gap: 0.5rem;
    }

    .review-create-container input[type='text'],
    .review-create-container input[type='textarea'] {
        font-size: 0.9rem;
    }
}
</style>
