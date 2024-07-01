<template>
    <div class="container" :style="{ backgroundColor: containerBgColor }">
        <button @click="toggleMenu">
            <h1>Pick a location</h1>
            <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="24"
                height="24"
                :class="{ rotate: showMenu }"
            >
                <path fill="currentColor" d="M12 16L4.1049 7 3 8l9 10 9-10-1-1z"></path>
            </svg>
        </button>
        <div :class="['menu', showMenu ? 'show' : '']">
            <button id="filter-all-place" class="menuItem" @click="clickAllPlaceButton('#73A761')">All Place</button>
            <button id="filter-seoul" class="menuItem" @click="clickMainNavButton('#c6db9d', '서울')">Seoul</button>
            <button id="filter-gyeonggi" class="menuItem" @click="clickMainNavButton('#f6f2fe', '경기')">
                Gyeonggi
            </button>
            <button id="filter-chungcheong" class="menuItem" @click="clickMainNavButton('#aac3da', '충청')">
                Chungcheong
            </button>
            <button id="filter-jeolla" class="menuItem" @click="clickMainNavButton('#ca8342', '전라')">Jeolla</button>
            <button id="filter-gangwon" class="menuItem" @click="clickMainNavButton('#f9ffe3', '강원')">Gangwon</button>
            <button id="filter-gyeongsang" class="menuItem" @click="clickMainNavButton('#a09cf9', '경상')">
                Gyeongsang
            </button>
            <button id="filter-jeju" class="menuItem" @click="clickMainNavButton('#ffed8c', '제주')">Jeju</button>
        </div>

        <div class="tag-filter-nav">
            <button class="tag-nav-item" v-for="tag in uniqueTags" :key="tag" @click="store.selectThunderByTag(tag)">
                {{ tag }}
            </button>
        </div>
        <div class="thunder-tool">
            <RouterLink :to="{ name: 'ThunderCreate' }">
                <button id="create-button" v-show="isLoggedIn">Create</button>
            </RouterLink>
        </div>

        <div class="post-card-container">
            <div class="post-card-element" v-for="thunder in store.thunderList" :key="thunder.thunderId">
                <RouterLink :to="`/thunder/${thunder.thunderId}`">
                    <img :src="thunder.media" loading="lazy" />
                </RouterLink>
                <button class="thunder-tag" @click="store.selectThunderByTag(thunder.tag)">
                    {{ thunder.tag }}
                </button>
                <p class="thunder-title">{{ thunder.title }}</p>
                <p class="thunder-content">{{ thunder.header }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useThunderStore } from '@/stores/thunder';
import { useUserStore } from '@/stores/user';
import { ref, onMounted, computed } from 'vue';
import { RouterLink } from 'vue-router';

const isLoggedIn = ref(false);
const storeUser = useUserStore();

const store = useThunderStore();

onMounted(() => {
    store.getThunderList();
    isLoggedIn.value = sessionStorage.getItem('access_token') !== null ? true : false;
});

const showMenu = ref(false);
const containerBgColor = ref('#73A761');

const toggleMenu = () => {
    showMenu.value = !showMenu.value;
};

const clickAllPlaceButton = (color) => {
    containerBgColor.value = color;
    store.getThunderList();
};

const clickMainNavButton = (color, region) => {
    containerBgColor.value = color;
    store.selectThunderByRegion(region);
};

const uniqueTags = computed(() => {
    const tags = store.tagsByRegion.map((thunder) => thunder.tag);
    return Array.from(new Set(tags));
});
</script>

<style scoped>
.container {
    text-align: center;
    height: 100%;
    transition: background-color 0.5s ease;
}
button {
    font-family: GmarketSansMedium;
    background: none;
    border: none;
    cursor: pointer;
    padding: 0;
    padding-top: 3.5em;
}
button h1 {
    display: inline;
    margin: 0;
    font-size: 3rem;
    position: relative;
    z-index: 1;
}
button svg {
    margin-left: 5px;
    position: relative;
    z-index: 1;
    transition: transform 0.3s ease;
}
.menu {
    position: absolute;
    top: -200px;
    left: 0;
    right: 0;
    background: white;
    color: black;
    padding-top: 4em;
    padding-bottom: 3em;
    padding-left: 2em;
    padding-right: 2em;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 0.5em;
    transition: top 0.5s ease;
}
.menu.show {
    top: 0px;
}
.menuItem {
    flex: 1;
    padding: 10px;
    font-size: 2rem;
    font-weight: bold;
}
.rotate {
    transform: rotate(180deg);
}
#filter-all-place:hover {
    color: #73a761;
}
#filter-seoul:hover {
    color: #c6db9d;
}
#filter-gyeonggi:hover {
    color: #f6f2fe;
}
#filter-chungcheong:hover {
    color: #aac3da;
}
#filter-jeolla:hover {
    color: #ca8342;
}
#filter-gangwon:hover {
    color: #f9ffe3;
}
#filter-gyeongsang:hover {
    color: #a09cf9;
}
#filter-jeju:hover {
    color: #ffed8c;
}

.post-card-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 4em;
    margin: 2em 15em 0 15em;
    padding-bottom: 5em;
}

.post-card-element {
    align-self: start;
}

.post-card-element img {
    margin-top: 0%;
    max-width: 100%;
    max-height: 100%;
    width: auto;
    height: auto;
    object-fit: contain;
}

.post-card-element p {
    text-align: left;
    margin: 0;
}

.thunder-tag {
    background-color: black;
    color: white;
    font-size: x-small;
    display: block;
    text-align: left;
    padding: 0.5em 1.5em 0.5em 1.5em;
    width: fit-content;
    margin-bottom: 1em;
}

.thunder-tag:hover {
    opacity: 0.5;
}

.thunder-title {
    font-weight: bold;
}

.thunder-content {
    font-size: smaller;
}

.tag-filter-nav {
    display: flex;
    justify-content: center;
    gap: 2em;
    margin-top: 3em;
}

.tag-nav-item {
    color: black;
    border: solid;
    border-color: black;
    border-width: thin;
    font-size: x-small;
    padding: 0.5em 1.5em 0.5em 1.5em;
    transition: background-color 0.5s ease, color 0.5s ease;
}

.thunder-tool {
    display: flex;
    justify-content: flex-end;
    margin-top: 1em;
    margin-right: 15em;
}

#create-button {
    border: solid;
    border-color: black;
    border-width: thin;
    color: black;
    padding: 0.5em 1em;
    font-size: small;
}

#create-button a {
    text-decoration: none;
    color: black;
}

.tag-nav-item:hover,
#create-button:hover {
    background-color: black;
    color: white;
    transition: background-color 0.5s ease, color 0.5s ease;
}
</style>
