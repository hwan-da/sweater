import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useUserStore } from './user';

const REST_THUNDER_API = `http://localhost:8080/thunder`;

export const useThunderStore = defineStore('thunder', () => {
    const storeUser = useUserStore();

    const isHeart = ref(false);

    const thunderList = ref([]);

    const thunderHeartList = ref([]);

    const selectedThunder = ref({});

    const tagsByRegion = ref([]);

    const getThunderList = async () => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/`);
            thunderList.value = response.data;
            tagsByRegion.value = response.data;
        } catch (error) {}
    };

    const selectThunderById = async (id) => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/${id}`);
            selectedThunder.value = response.data;

            const trailData = JSON.parse(response.data.trail);
            selectedThunder.value.trail = trailData;
        } catch (error) {}
    };

    const selectThunderByNickname = async (nickname) => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/nickname/${nickname}`);
            thunderList.value = response.data;
        } catch (error) {}
    };

    const selectThunderByRegion = async (region) => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/region/${region}`);
            thunderList.value = response.data;
            tagsByRegion.value = response.data;
        } catch (error) {}
    };

    const selectThunderByTag = async (tag) => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/tag/${tag}`);
            thunderList.value = response.data;
        } catch (error) {}
    };

    const selectThunderByHeart = async (userId) => {
        try {
            const response = await axios.get(`${REST_THUNDER_API}/heart/${userId}`);
            thunderHeartList.value = response.data;
        } catch (error) {
            console.log(error);
        }
    };

    const createThunder = async (formData) => {
        try {
            const response = await axios.post(`${REST_THUNDER_API}/create`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
        } catch (error) {}
    };

    const modifyThunder = async () => {
        try {
            const thunderData = {
                thunderId: selectedThunder.value.thunderId,
                title: selectedThunder.value.title,
                header: selectedThunder.value.header,
                startDate: selectedThunder.value.startDate,
                endDate: selectedThunder.value.endDate,
                content: selectedThunder.value.content,
            };

            await axios.put(`${REST_THUNDER_API}/update`, thunderData, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getThunderList();
        } catch (error) {}
    };

    const deleteThunder = async (id) => {
        try {
            await axios.delete(`${REST_THUNDER_API}/${id}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getThunderList();
        } catch (error) {}
    };

    const pushHeart = async (thunderId) => {
        try {
            await axios.post(`${REST_THUNDER_API}/heart/${storeUser.loginUserId}/${thunderId}`, null, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await selectThunderById(thunderId);
        } catch (error) {}
    };

    const checkHeart = async (thunderId) => {
        if (sessionStorage.getItem('access_token')) {
            try {
                const response = await axios.post(
                    `${REST_THUNDER_API}/checkheart/${storeUser.user.userId}/${thunderId}`,
                    null,
                    {
                        headers: {
                            access_token: sessionStorage.getItem('access_token'),
                        },
                    }
                );
                isHeart.value = response.data;
                await selectThunderById(thunderId);
            } catch (error) {}
        }
    };

    return {
        thunderList,
        selectedThunder,
        getThunderList,
        tagsByRegion,
        selectThunderById,
        selectThunderByNickname,
        selectThunderByRegion,
        selectThunderByTag,
        createThunder,
        modifyThunder,
        deleteThunder,
        pushHeart,
        selectThunderByHeart,
        thunderHeartList,
        checkHeart,
        isHeart,
    };
});
