<template>
    <div>
        <h1 :style="{ fontSize: '5.5em' }">{{ translatedRegion }}</h1>
        <div class="thunder-tool">
            <div class="left-buttons">
                <div
                    v-show="isLoggedIn && store.selectedThunder.writer !== storeUser.user.nickname"
                >
                    <button
                        @click="pushHeart(store.selectedThunder.thunderId)"
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
                <button v-show="isMine" @click="deleteThunder">Delete</button>
            </div>
        </div>
        <img :src="`${store.selectedThunder.media}`" />
        <p>{{ store.selectedThunder.title }}</p>
        <p>❤ {{ heartCount }}</p>
        <p>
            {{ formattedDateRange(store.selectedThunder.startDate, store.selectedThunder.endDate) }}
        </p>
        <p>{{ store.selectedThunder.writer }}</p>
        <hr />
        <h1>Course Information</h1>
        <KakaoMap :lat="centerLat" :lng="centerLng" :level="7" @onLoadKakaoMap="onLoadKakaoMap">
            <div v-for="(path, index) in latLngList" :key="index">
                <KakaoMapPolyline :latLngList="path" />
            </div>
        </KakaoMap>
        <div class="map-information">
            <p>{{ store.selectedThunder.hikingTime }}분</p>
            <p>{{ store.selectedThunder.hikingDist / 1000 }}킬로미터</p>
        </div>
        <div class="main-content" v-html="store.selectedThunder.content"></div>
        <h1>Comment</h1>
        <div
            class="comment-container"
            v-for="comment in storeComment.commentList"
            :key="comment.commentId"
        >
            <p>{{ comment.title }}</p>
            <p>{{ comment.writer }}</p>
            <p>{{ comment.content }}</p>
            <button
                v-show="storeUser.user.nickname == store.selectedThunder.writer"
                @click="openInviteModal(comment.writer)"
            >
                invite
            </button>
        </div>
        <button v-show="isLoggedIn" @click="createComment" id="create-button">Create</button>
        <div v-show="isLoggedIn" class="comment-create-container">
            <p>제목:</p>
            <input type="text" id="comment-title-creater" v-model="newComment.title" />
            <p>본문:</p>
            <input type="textarea" id="comment-content-creater" v-model="newComment.content" />
        </div>

        <!-- Modal -->
        <div v-show="showMessageModal" class="modal-overlay">
            <div class="modal-content">
                <h2>Enter Message Content</h2>
                <textarea v-model="message.content" rows="5"></textarea>
                <div class="modal-buttons">
                    <button @click="sendMessage">Send</button>
                    <button @click="closeInviteModal">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useThunderStore } from '@/stores/thunder';
import { useMountainStore } from '@/stores/mountain';
import { useCommentStore } from '@/stores/comment';
import { useUserStore } from '@/stores/user';
import { ref, onMounted, computed, watch } from 'vue';
import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router';
import { KakaoMap, KakaoMapPolyline } from 'vue3-kakao-maps';

const store = useThunderStore();
const storeMnt = useMountainStore();
const storeComment = useCommentStore();
const route = useRoute();
const router = useRouter();
const storeUser = useUserStore();

const isLoggedIn = ref(false);
const isMine = ref(false);
const map = ref(null);

const heartCount = ref(0);
const isHeart = ref(false);

const newComment = ref({
    title: '',
    writer: '',
    content: '',
});

const showMessageModal = ref(false);

onMounted(async () => {
    await store.selectThunderById(route.params.thunderId);
    await storeMnt.loadMountain(mntnCode.value);
    await storeComment.getCommentList(route.params.thunderId);
    await storeUser.getUserById();
    await store.checkHeart(route.params.thunderId);

    setUser();
    heartCount.value = store.selectedThunder.heart;
    isHeart.value = store.isHeart;
    updateLatLngList();
    isLoggedIn.value = sessionStorage.getItem('access_token') !== null ? true : false;
    isMine.value = store.selectedThunder.writer === storeUser.user.nickname ? true : false;
    updateBackgroundColor();
});

onBeforeRouteLeave(() => {
    const body = document.querySelector('body');
    body.style.backgroundColor = 'white';
});

const message = ref({
    sender: '',
    receiver: '',
    content: '',
});

const setUser = () => {
    newComment.value.writer = storeUser.user.nickname;
    message.value.sender = storeUser.user.nickname;
};

const openInviteModal = (receiver) => {
    message.value.receiver = receiver;
    showMessageModal.value = true;
};

const closeInviteModal = () => {
    showMessageModal.value = false;
};

const sendMessage = () => {
    storeComment.invite(message.value);
    closeInviteModal();
};

const pushHeart = (thunderId) => {
    store.pushHeart(thunderId);
    isHeart.value = !isHeart.value;
};

watch(
    () => store.isHeart,
    (newVal) => {
        isHeart.value = newVal;
    }
);

watch(
    () => store.selectedThunder.heart,
    (newVal) => {
        heartCount.value = newVal;
    }
);

const onLoadKakaoMap = (mapRef) => {
    map.value = mapRef;
    map.value.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);
};

const mntnCode = computed(() => {
    switch (store.selectedThunder.tag) {
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
    store.selectedThunder.trail.forEach((path) => {
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
    router.push({ name: 'ThunderUpdate' });
};

const deleteThunder = () => {
    store.deleteThunder(route.params.thunderId);
    router.push({ name: 'ThunderList' });
};

const createComment = () => {
    storeComment.createComment(route.params.thunderId, newComment.value);
    location.reload();
};

const updateBackgroundColor = () => {
    const region = store.selectedThunder.region;
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
    switch (store.selectedThunder.region) {
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
            return store.selectedThunder.region;
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
.thunder-tool {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin: 1rem 0;
}

.thunder-tool .left-buttons,
.thunder-tool .right-buttons {
    display: flex;
    gap: 1rem;
}

.thunder-tool .right-buttons {
    margin-left: auto;
}

.thunder-tool button,
#create-button {
    border: solid;
    border-color: black;
    border-width: thin;
    color: black;
    padding: 0.5em 1em;
    font-size: small;
    background-color: transparent;
}

.thunder-tool button a,
#create-button a {
    text-decoration: none;
    color: black;
}

.thunder-tool button:hover,
#create-button:hover {
    background-color: black;
    color: white;
    transition: background-color 0.5s ease, color 0.5s ease;
}

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
.comment-container {
    border: 1px solid #ccc;
    padding: 1rem;
    margin: 1rem 0;
    border-radius: 8px;
}

.comment-container p {
    margin: 0.5rem 0;
}

.comment-create-container {
    margin: 2rem 0;
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.comment-create-container input[type='text'],
.comment-create-container input[type='textarea'] {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

/* Modal Styles */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background: white;
    padding: 2rem;
    border-radius: 8px;
    text-align: center;
    width: 80%;
    max-width: 500px;
}

.modal-content h2 {
    margin-bottom: 1rem;
}

.modal-content textarea {
    width: 100%;
    margin-bottom: 1rem;
    padding: 0.5rem;
}

.modal-buttons {
    display: flex;
    justify-content: space-around;
}

.modal-buttons button {
    padding: 0.5rem 1rem;
    cursor: pointer;
}

/* Responsive Styles */
@media (max-width: 768px) {
    h1 {
        font-size: 1.5rem;
    }

    .thunder-tool {
        flex-direction: column;
    }

    .thunder-tool button {
        font-size: 0.9rem;
    }

    .map-information {
        flex-direction: column;
        align-items: center;
    }

    .map-information p {
        font-size: 0.9rem;
    }

    .comment-create-container {
        flex-direction: column;
        gap: 0.5rem;
    }

    .comment-create-container input[type='text'],
    .comment-create-container input[type='textarea'] {
        font-size: 0.9rem;
    }
}
</style>
