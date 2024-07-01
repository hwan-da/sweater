<template>
    <div class="container">
        <div class="text-content">
            <h2>산에서 동물을 만나는 순간</h2>
            <p>
                등산을 하며 산에서 동물을 만나는 것은 매우 특별한 경험입니다. 북한산에서는 종종
                강아지를 데리고 산책하는 사람들을 볼 수 있으며, 겨울철에는 꽁꽁 얼어붙은 길 위로
                고양이가 조심스럽게 걸어다니는 모습을 목격할 수도 있습니다. 한라산에서는 자유롭게
                뛰어다니는 말을 만날 수 있고, 설악산에서는 알파카를 볼 수 있습니다.
            </p>
            <p>
                지리산에서는 반달곰을 발견할 수도 있으며, 다람쥐에게 간식을 주는 재미도 쏠쏠합니다.
                반려견과 함께 주말을 보내며 등산을 즐기는 사람들도 많습니다. 이러한 동물들과의
                만남은 등산을 더욱 특별하고 잊지 못할 추억으로 만들어줍니다.
            </p>
        </div>
        <div class="carousel-container">
            <carousel :per-page="1">
                <slide v-for="(anchor, index) in anchors" :key="index">
                    <a :href="anchor.src">
                        <img :src="anchor.image" :alt="anchor.title" />
                    </a>
                </slide>
                <template #addons>
                    <navigation />
                </template>
            </carousel>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';

const anchors = ref([
    {
        src: 'http://localhost:5173/board/6',
        image: 'https://img.freepik.com/free-photo/closeup-shot-of-a-cute-golden-retriever-puppy-curiously-looking-at-the-camera_181624-15694.jpg?size=626&ext=jpg&ga=GA1.1.632798143.1713052800&semt=ais',
        title: '강아지가 많은 산, 북한산으로 오세요~~',
    },
    {
        src: 'http://localhost:5173/board/5',
        image: 'https://i.pinimg.com/736x/d5/6d/18/d56d1893c7e9930886e4c3fbfe30eff6.jpg',
        title: '꽁꽁 얼어붙은 북한산 위로 고양이가 걸어다닙니다.',
    },
    {
        src: 'http://localhost:5173/board/16',
        image: 'https://i.namu.wiki/i/0NH09CBurKQ6EZZyfR0M9XCg5lyj3GvQJrRA_6eutMC1CZn6IzKqFnU7KPtVvtTNKULQzodTP_KfW6QAZdoVIg.webp',
        title: '말이 뛰어다니는 한라산으로 떠나요',
    },
    {
        src: 'http://localhost:5173/board/4',
        image: 'https://i.namu.wiki/i/1weNF1VH8xqaE7WezpL23zRNecsX9SRb95Ts-k_wsNi1j6tcug4JQdf2XaEs99fWBcPehI_G9TnnS7X_QF8PcA.webp',
        title: '알파카를 볼 수 있는 설악산으로 등산가보세요',
    },
    {
        src: 'http://localhost:5173/thunder/10',
        image: 'https://img.khan.co.kr/news/2017/07/24/l_2017072401003190500251642.jpg',
        title: '지리산 반달곰 잡으러 갈사람',
    },
    {
        src: 'http://localhost:5173/thunder/12',
        image: 'https://i.namu.wiki/i/eMKUOGR4pfm500pqPtvwCOiFinyO6O5y3S6iHD0YEKI0tHLXqTvyAJ0y-qLrZMfC97AIgqkNuVYXw6gdxhkuvQ.webp',
        title: '다람쥐에게 간식을 줄 수 있는 산을 아시나요?',
    },
    {
        src: 'http://localhost:5173/thunder/9',
        image: 'https://img.khan.co.kr/news/2024/03/23/news-p.v1.20240323.c159a4cab6f64473adf462d873e01e43_P1.jpg',
        title: '반려견과 함께 주말에 등산갈사람!',
    },
]);
</script>

<style scoped>
.container {
    background-color: #f9f3f3;
    display: flex;
    flex-direction: row;
    align-items: center;
    position: relative;
    margin: 0 auto;
    padding: 0em 19em 0em 19em;
}

.text-content {
    width: 50%;
    padding: 20px;
}

.carousel-container {
    width: 50%;
    position: relative;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.carousel-container img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
}

.vue-carousel {
    width: 100%;
}

.vue-carousel .carousel__slides {
    display: flex;
    justify-content: center;
}

.vue-carousel .carousel__navigation {
    display: flex;
    justify-content: center;
    margin-top: 10px;
}
</style>
