<template>
    <div class="container">
        <h1>Create Board</h1>
        <div>
            <p>지역을 선택하세요</p>
            <select v-model="board.region">
                <option disabled value="">지역 선택</option>
                <option v-for="(mountains, region) in mountainData" :value="region" :key="region">
                    {{ region }}
                </option>
            </select>
        </div>

        <div>
            <p>산을 선택하세요</p>
            <select v-model="board.tag" :disabled="!board.region">
                <option disabled value="">산 선택</option>
                <option
                    v-for="mountain in mountainData[board.region]"
                    :value="mountain"
                    :key="mountain"
                >
                    {{ mountain }}
                </option>
            </select>
        </div>
        <div>
            <p>제목을 입력하세요</p>
            <label for="input-title">
                <input type="text" name="input-title" v-model="board.title" />
            </label>
        </div>
        <div>
            <p>머리글을 입력하세요</p>
            <label for="input-header">
                <input type="textarea" name="input-header" v-model="board.header" />
            </label>
        </div>
        <div>
            <p>출발 날짜를 입력하세요</p>
            <label for="input-start-date">
                <input
                    type="date"
                    name="input-start-date"
                    min="2024-04-01"
                    max="2024-12-31"
                    v-model="board.startDate"
                />
            </label>
        </div>
        <div>
            <p>도착 날짜를 입력하세요</p>
            <label for="input-end-date">
                <input
                    type="date"
                    name="input-end-date"
                    :min="board.startDate"
                    max="2024-12-31"
                    v-model="board.endDate"
                />
            </label>
        </div>
        <div>
            <p>파일을 첨부하세요</p>
            <label for="input-file">
                <input type="file" @change="onFileChange" />
            </label>
        </div>
        <div v-if="editor">
            <bubble-menu class="bubble-menu" :tippy-options="{ duration: 100 }" :editor="editor">
                <button
                    @click="editor.chain().focus().toggleBold().run()"
                    :class="{ 'is-active': editor.isActive('bold') }"
                >
                    Bold
                </button>
            </bubble-menu>
            <div>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
                >
                    h1
                </button>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"
                >
                    h2
                </button>
                <button
                    @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
                    :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"
                >
                    h3
                </button>
                <button
                    @click="editor.chain().focus().setParagraph().run()"
                    :class="{ 'is-active': editor.isActive('paragraph') }"
                >
                    p
                </button>
                <button
                    @click="editor.chain().focus().toggleBold().run()"
                    :disabled="!editor.can().chain().focus().toggleBold().run()"
                    :class="{ 'is-active': editor.isActive('bold') }"
                >
                    b
                </button>
                <button
                    @click="editor.chain().focus().toggleBulletList().run()"
                    :class="{ 'is-active': editor.isActive('bulletList') }"
                >
                    ul
                </button>
                <button
                    @click="editor.chain().focus().toggleOrderedList().run()"
                    :class="{ 'is-active': editor.isActive('orderedList') }"
                >
                    ol
                </button>
                <button @click="editor.chain().focus().setHorizontalRule().run()">hr</button>
                <button @click="editor.chain().focus().setHardBreak().run()">br</button>
            </div>
            <div>
                <button
                    @click="editor.chain().focus().undo().run()"
                    :disabled="!editor.can().chain().focus().undo().run()"
                >
                    undo
                </button>
                <button
                    @click="editor.chain().focus().redo().run()"
                    :disabled="!editor.can().chain().focus().redo().run()"
                >
                    redo
                </button>
            </div>
        </div>

        <editor-content :editor="editor" v-model="board.content" class="editor-content" />

        <div v-if="board.tag !== ''">
            <h2>현재 선택한 등산로의 산행시간: {{ hikingTime }}분</h2>
            <h2>현재 선택한 등산로의 산행거리: {{ hikingDist / 1000 }}킬로미터</h2>
            <KakaoMap :lat="36.34" :lng="127.77" :level="14" @onLoadKakaoMap="onLoadKakaoMap">
                <template v-if="map">
                    <div v-for="(mountainInfo, fid) in storeMnt.mountainInformation" :key="fid">
                        <KakaoMapPolyline
                            :latLngList="mountainInfo.paths"
                            :strokeColor="pathColor(mountainInfo.pmntnDffl)"
                            :strokeWeight="6"
                            :strokeOpacity="0.35"
                            @onLoadKakaoMapPolyline="
                                handleOnLoadKakaoMapPolyline($event, mountainInfo)
                            "
                        />
                    </div>
                </template>
            </KakaoMap>
        </div>
        <button @click="createBoard">글 작성</button>
    </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useMountainStore } from '@/stores/mountain';
import { KakaoMap, KakaoMapPolyline } from 'vue3-kakao-maps';
import { useEditor, EditorContent, BubbleMenu } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import { useBoardStore } from '@/stores/board';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const storeUser = useUserStore();

const editor = useEditor({
    content: '내용을 입력하세요',
    extensions: [StarterKit],
});

const board = ref({
    region: '',
    tag: '',
    writer: '',
    title: '',
    header: '',
    startDate: '',
    endDate: '',
    content: '',
    media: '',
    hikingTime: 0,
    hikingDist: 0,
});

const mountainData = {
    서울: ['북한산', '관악산'],
    경기: ['마니산', '명성산'],
    강원: ['설악산'],
    충청: ['소백산'],
    경상: ['가지산', '지리산'],
    전라: ['덕유산', '백운산'],
    제주: ['한라산'],
};

const store = useBoardStore();
const storeMnt = useMountainStore();
const router = useRouter();

const map = ref(null);
const hikingTime = ref(0);
const hikingDist = ref(0);
const hikingOrder = ref([]);
const onLoadKakaoMap = (mapRef) => {
    map.value = mapRef;
    map.value.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);
};

const mntnCode = computed(() => {
    switch (board.value.tag) {
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

watch(
    () => board.value.tag,
    (newValue) => {
        if (newValue !== '') {
            storeMnt.loadMountain(mntnCode.value);
        }
    }
);

const pathColor = (difficulty) => {
    if (difficulty === '쉬움') return '#00FF5E';
    else if (difficulty === '중간') return '#FFAA00';
    else return '#F10000';
};

const handleOnLoadKakaoMapPolyline = (polylineRef, mountainInfo) => {
    kakao.maps.event.addListener(polylineRef, 'mouseover', function () {
        polylineRef.setOptions({ strokeOpacity: 1 });
    });
    kakao.maps.event.addListener(polylineRef, 'mouseout', function () {
        polylineRef.setOptions({ strokeOpacity: 0.35 });
    });
    kakao.maps.event.addListener(polylineRef, 'click', function () {
        hikingTime.value += mountainInfo.pmntnUppl;
        hikingDist.value += mountainInfo.pmntnLt;
        polylineRef.setOptions({ strokeOpacity: 1, strokeColor: '#000011' });
        hikingOrder.value.push([mountainInfo.mntnCode, mountainInfo.fid]);
        
    });
};

const media = ref('');

const onFileChange = (event) => {
    media.value = event.target.files[0];
};

const createBoard = async () => {
    await storeUser.getUserById();
    board.value.writer = storeUser.user.nickname;
    board.value.content = editor.value.getHTML();
    console.log(hikingOrder.value)
    board.value.trail = JSON.stringify(hikingOrder.value);
    board.value.hikingTime = hikingTime.value;
    board.value.hikingDist = hikingDist.value;

    const formData = new FormData();

    formData.append('media', media.value);
    formData.append('board', JSON.stringify(board.value));

    await store.createBoard(formData);
    router.push({ name: 'BoardList' });
};
</script>

<style scoped>
.bubble-menu {
    display: flex;
    background-color: #0d0d0d;
    padding: 0.5rem;
    border-radius: 0.5rem;
}

.bubble-menu button {
    border: none;
    background: none;
    color: #fff;
    font-size: 0.85rem;
    font-weight: 500;
    padding: 0 0.5rem;
    opacity: 0.6;
    transition: opacity 0.3s ease;
}

.bubble-menu button:hover,
.bubble-menu button.is-active {
    opacity: 1;
}

.container {
    margin: 0 auto;
    padding: 0 1rem;
    max-width: 800px;
}

select,
input[type='text'],
input[type='textarea'],
input[type='file'] {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 1rem;
    margin-top: 0.5rem;
}

select {
    width: auto;
    min-width: 200px;
}

label {
    display: block;
    margin-bottom: 0.5rem;
}

h1 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
}

p {
    font-size: 1rem;
    margin-bottom: 0.5rem;
}

button {
    display: inline-flex;
    align-content: space-between;
    padding: 0.45rem 0.85rem;
    font-size: 1rem;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 0.2em 0.2em 0.2em 0.2em;
}

button:hover {
    background-color: #0056b3;
}

button:disabled {
    background-color: #6c757d;
    cursor: not-allowed;
}

h2 {
    font-size: 1.2rem;
    margin-top: 1rem;
}

.editor-content {
    min-height: 300px;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 1rem;
    box-sizing: border-box;
}
</style>
