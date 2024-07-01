<template>
    <div class="profile-container">
        <div class="header-container">
            <h1>Profile</h1>
        </div>
        <img class="profile-img" :src="store.user.picture" @click="openImageModal" />
        <div class="profile-text">
            <span :style="{ fontSize: 'x-large' }">{{ store.user.nickname }}님 안녕하세요!</span>
            <br />
            <span>{{ store.user.userId }}</span>
            <span>{{ store.user.gender }}</span>
        </div>
        <div class="header-container">
            <h1 :style="{ marginTop: '-0.5em' }">Introduction</h1>
            <button @click="openIntroModal" class="edit-btn" :style="{ marginTop: '-2em', marginLeft: '-2em' }">
                Edit
            </button>
        </div>
        <p v-for="(string, index) in intro" :key="index">{{ string }}</p>

        <!-- 사진 바꾸는 모달 -->
        <div v-if="isImageModalOpen" class="modal">
            <div class="modal-content">
                <span class="close" @click="closeImageModal">&times;</span>
                <h2>Change Profile Picture</h2>
                <input type="file" @change="handleImageChange" />
                <button @click="submitImageChange">Change</button>
                <button @click="closeImageModal">Cancel</button>
            </div>
        </div>

        <!-- 소개글 바꾸는 모달 -->
        <div v-if="isIntroModalOpen" class="modal">
            <div class="modal-content">
                <span class="close" @click="closeIntroModal">&times;</span>
                <h2>Edit Introduction</h2>
                <textarea v-model="newProfile.introduce" rows="5" placeholder="Enter your introduction"></textarea>
                <button @click="submitIntroChange">Save Changes</button>
                <button @click="closeIntroModal">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';

const store = useUserStore();

const intro = ref([]);
const isImageModalOpen = ref(false);
const isProfileModalOpen = ref(false);
const isIntroModalOpen = ref(false);
const selectedFile = ref(null);
const nickname = ref(store.user.nickname);
const gender = ref(store.user.gender);
const introduction = ref('');

const newProfile = ref({
    userId: '',
    password: '',
    nickname: '',
    gender: '',
    introduce: '',
});

onMounted(async () => {
    await store.getUserById();
    updateProfile();
    intro.value = store.user.introduce.split('. ');
    introduction.value = store.user.introduce;
});

const updateProfile = () => {
    newProfile.value.userId = store.user.userId;
    newProfile.value.password = store.user.password;
    newProfile.value.nickname = store.user.nickname;
    newProfile.value.gender = store.user.gender;
};

const openImageModal = () => {
    isImageModalOpen.value = true;
};

const closeImageModal = () => {
    isImageModalOpen.value = false;
    selectedFile.value = null;
};

const handleImageChange = (event) => {
    selectedFile.value = event.target.files[0];
};

const submitImageChange = async () => {
    if (selectedFile.value) {
        const formData = new FormData();
        formData.append('picture', selectedFile.value);
        formData.append('userId', store.loginUserId);

        await store.updateUserProfilePicture(formData);

        await store.getUserById();
        closeImageModal();
    }
};

const openIntroModal = () => {
    isIntroModalOpen.value = true;
};

const closeIntroModal = () => {
    isIntroModalOpen.value = false;
};

const submitIntroChange = async () => {
    console.log(newProfile.value);
    await store.updateUserIntroduction(newProfile.value);

    await store.getUserById();
    intro.value = store.user.introduce.split('. ');
    closeIntroModal();
};
</script>

<style scoped>
.profile-container {
    display: flex;
    flex-direction: column;
    width: 55em;
    margin: auto;
}

.header-container {
    display: flex;
    align-items: center;
}

.header-container h1 {
    font-size: 3.5em;
    margin-right: 1em;
}

.profile-img {
    border-radius: 50%;
    width: 250px;
    height: 250px;
    object-fit: cover;
    object-position: center;
    align-self: flex-end;
    margin-top: -5em;
    margin-bottom: 2em;
    cursor: pointer;
}

.profile-text {
    align-self: flex-end;
}

.profile-text span {
    margin: 0 2em 0 2em;
    font-size: large;
}

.edit-btn {
    margin-top: 0.5em;
    padding: 0.3em 0.6em;
    font-size: medium;
    cursor: pointer;
}

.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
    background-color: #fefefe;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
    max-width: 500px;
    text-align: center;
}

.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}

button {
    margin: 10px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
}

input[type='text'],
select,
textarea {
    width: 80%;
    padding: 10px;
    margin: 10px 0;
    box-sizing: border-box;
}
</style>
