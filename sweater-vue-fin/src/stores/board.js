import { ref, computed, onMounted } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useUserStore } from './user';

const REST_BOARD_API = `http://localhost:8080/board`;

export const useBoardStore = defineStore('board', () => {
    const storeUser = useUserStore();

    const isHeart = ref(false);

    const boardHeartList = ref([]);
    // 전체 보드 리스트
    const boardList = ref([]);
    // 선택된 단일 보드
    const selectedBoard = ref({});
    // 화면에 보여줄 태그 리스트
    const tagsByRegion = ref([]);

    const getBoardList = async () => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/`);
            boardList.value = response.data;
            tagsByRegion.value = response.data;
        } catch (error) {}
    };

    const selectBoardById = async (id) => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/${id}`);
            selectedBoard.value = response.data;

            const trailData = JSON.parse(response.data.trail);
            selectedBoard.value.trail = trailData;
        } catch (error) {}
    };

    const selectBoardByNickname = async (nickname) => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/nickname/${nickname}`);
            boardList.value = response.data;
        } catch (error) {}
    };

    const selectBoardByRegion = async (region) => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/region/${region}`);
            boardList.value = response.data;
            tagsByRegion.value = response.data;
        } catch (error) {}
    };

    const selectBoardByTag = async (tag) => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/tag/${tag}`);
            boardList.value = response.data;
        } catch (error) {}
    };

    const selectBoardByHeart = async (userId) => {
        try {
            const response = await axios.get(`${REST_BOARD_API}/heart/${userId}`);
            boardHeartList.value = response.data;
        } catch (error) {
            console.log(error);
        }
    };

    const createBoard = async (formData) => {
        try {
            console.log(sessionStorage.getItem('access_token'));
            const response = await axios.post(`${REST_BOARD_API}/create`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
        } catch (error) {}
    };

    const modifyBoard = async () => {
        try {
            const boardData = {
                boardId: selectedBoard.value.boardId,
                title: selectedBoard.value.title,
                header: selectedBoard.value.header,
                startDate: selectedBoard.value.startDate,
                endDate: selectedBoard.value.endDate,
                content: selectedBoard.value.content,
            };

            await axios.put(`${REST_BOARD_API}/update`, boardData, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getBoardList();
        } catch (error) {
            console.error('Error in modifyBoard:', error.response ? error.response.data : error.message);
        }
    };

    const deleteBoard = async (id) => {
        try {
            await axios.delete(`${REST_BOARD_API}/${id}`, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await getBoardList();
        } catch (error) {}
    };

    const pushHeart = async (boardId) => {
        try {
            await axios.post(`${REST_BOARD_API}/heart/${storeUser.user.userId}/${boardId}`, null, {
                headers: {
                    access_token: sessionStorage.getItem('access_token'),
                },
            });
            await selectBoardById(boardId);
        } catch (error) {}
    };

    const checkHeart = async (boardId) => {
        if (sessionStorage.getItem('access_token')) {
            try {
                const response = await axios.post(
                    `${REST_BOARD_API}/checkheart/${storeUser.user.userId}/${boardId}`,
                    null,
                    {
                        headers: {
                            access_token: sessionStorage.getItem('access_token'),
                        },
                    }
                );
                isHeart.value = response.data;
                await selectBoardById(boardId);
            } catch (error) {}
        }
    };

    return {
        boardList,
        selectedBoard,
        getBoardList,
        tagsByRegion,
        selectBoardById,
        selectBoardByNickname,
        selectBoardByRegion,
        selectBoardByTag,
        createBoard,
        modifyBoard,
        deleteBoard,
        pushHeart,
        selectBoardByHeart,
        boardHeartList,
        checkHeart,
        isHeart,
    };
});
